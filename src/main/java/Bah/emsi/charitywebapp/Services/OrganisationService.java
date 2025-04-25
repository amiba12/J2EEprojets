package Bah.emsi.charitywebapp.Services;
import Bah.emsi.charitywebapp.entities.ActionDeCharite;
import Bah.emsi.charitywebapp.repositories.ActionDeChariteRepo;
import Bah.emsi.charitywebapp.entities.Organisation;
import Bah.emsi.charitywebapp.repositories.OrganisationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrganisationService {

    @Autowired
    private OrganisationRepo organisationRepo;

    // Sauvegarder une organisation
    public Organisation saveOrganisation(Organisation organisation) {
        return organisationRepo.save(organisation);
    }

    // Récupérer une organisation par son ID
    public Organisation getOrganisationById(Long id) {
        return organisationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Organisation non trouvée"));
    }
     // pour sans logo
     //@PutMapping("/{id}")
        //public ResponseEntity<Organisation> modifierOrganisation(@PathVariable Long id, @RequestBody Organisation organisation) {
         //return ResponseEntity.ok(organisationService.modifierProfil(id, organisation, null));
     //}


    public Organisation modifierProfil(Long id, Organisation organisation, MultipartFile logoFile) {
        Organisation existingOrganisation = organisationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Organisation non trouvée"));

        // Mettre à jour les champs uniquement s'ils ne sont pas null
        if (organisation.getNom() != null) existingOrganisation.setNom(organisation.getNom());
        if (organisation.getAdresseLegale() != null) existingOrganisation.setAdresseLegale(organisation.getAdresseLegale());
        if (organisation.getNumeroFiscal() != null) existingOrganisation.setNumeroFiscal(organisation.getNumeroFiscal());
        if (organisation.getContact() != null) existingOrganisation.setContact(organisation.getContact());
        if (organisation.getValide() != null) existingOrganisation.setValide(organisation.getValide());

        // Gestion du logo
        if (logoFile != null && !logoFile.isEmpty()) {
            deleteLogoFile(existingOrganisation.getLogo()); // supprimer l’ancien logo
            saveOrganisationWithLogo(existingOrganisation, logoFile); // sauvegarder le nouveau
        }

        return organisationRepo.save(existingOrganisation);
    }


    // Récupérer toutes les organisations
    public List<Organisation> getAllOrganisations() {
        return organisationRepo.findAll();
    }

    //    pour supprimer
    public void deleteOrganisation(Long id) {
        Organisation organisation = organisationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Organisation non trouvée"));

        deleteLogoFile(organisation.getLogo()); // Supprimer le fichier logo

        organisationRepo.delete(organisation);
    }


    @Autowired
    private ActionDeChariteRepo actionDeChariteRepo;

    public ActionDeCharite creerActionPourOrganisation(Long organisationId, ActionDeCharite action, List<MultipartFile> medias) throws IOException {
        Organisation organisation = organisationRepo.findById(organisationId)
                .orElseThrow(() -> new RuntimeException("Organisation non trouvée"));

        action.setOrganisation(organisation);

        // Gérer l’upload des médias
        List<String> nomsFichiers = new ArrayList<>();
        for (MultipartFile media : medias) {
            if (!media.isEmpty()) {
                String nomFichier = UUID.randomUUID() + "_" + media.getOriginalFilename();
                Path chemin = Paths.get("uploads/" + nomFichier);
                Files.createDirectories(chemin.getParent());
                Files.write(chemin, media.getBytes());

                nomsFichiers.add(nomFichier);
            }
        }

        action.setMediaFilenames(nomsFichiers);
        return actionDeChariteRepo.save(action);
    }



    public List<ActionDeCharite> getActionsParOrganisation(Long id) {
        // Appel à la méthode du repository pour récupérer les actions de l'organisation par ID
        return actionDeChariteRepo.findByOrganisationId(id);
    }

    public void saveOrganisationWithLogo(Organisation organisation, MultipartFile logoFile) {
        try {
            String uploadDir = "src/main/resources/static/images/";
            String uniqueFileName = System.currentTimeMillis() + "_" + logoFile.getOriginalFilename();
            Path filePath = Path.of(uploadDir, uniqueFileName);

            Files.copy(logoFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            organisation.setLogo("/images/" + uniqueFileName);
            organisationRepo.save(organisation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String saveLogoFile(MultipartFile logoFile) throws IOException {
        // Définir le répertoire où stocker l'image
        Path path = Paths.get("src/main/resources/static/images/" + logoFile.getOriginalFilename());
        Files.copy(logoFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return "/images/" + logoFile.getOriginalFilename();  // URL relative pour l'affichage
    }

    public void deleteLogoFile(String logoPath) {
        if (logoPath != null) {
            String fileName = logoPath.substring(logoPath.lastIndexOf("/") + 1);
            Path filePath = Path.of("src/main/resources/static/images/" + fileName);
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }









}
