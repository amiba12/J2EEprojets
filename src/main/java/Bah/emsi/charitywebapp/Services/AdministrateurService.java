package Bah.emsi.charitywebapp.Services;

import Bah.emsi.charitywebapp.entities.Organisation;
import Bah.emsi.charitywebapp.entities.Utilisateur;
import Bah.emsi.charitywebapp.repositories.OrganisationRepo;
import Bah.emsi.charitywebapp.repositories.UtilisateurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdministrateurService extends UtilisateurService {

    @Autowired
    private OrganisationRepo organisationRepo;

    @Autowired
    private UtilisateurRepo utilisateurRepo;

    // Méthode pour approuver une organisation
    public void approuverOrganisation(Long id) {
        Organisation organisation = organisationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Organisation non trouvée"));
        organisation.setValide(true);  // Logique d'approbation
        organisationRepo.save(organisation);
    }

    // Méthode pour gérer un utilisateur (activer ou désactiver)
    public void gererUtilisateurs(Long utilisateurId, boolean activer) {
        Utilisateur utilisateur = utilisateurRepo.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Logique de gestion de l'utilisateur : on peut activer ou désactiver l'utilisateur
        if (activer) {
            utilisateur.setActive(true);
        } else {
            utilisateur.setActive(false);
        }

        utilisateurRepo.save(utilisateur);
    }



    // Récupérer une organisation par son ID
    public Organisation getOrganisation(Long id) {
        return organisationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Organisation non trouvée"));
    }

    // Récupérer toutes les organisations
    public List<Organisation> getAllOrganisations() {
        return organisationRepo.findAll();
    }

    // Autres fonctionnalités d'administration
}
