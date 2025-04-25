package Bah.emsi.charitywebapp.Controllers;
import Bah.emsi.charitywebapp.Services.AdministrateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdministrateurController {

    private final AdministrateurService administrateurService;

    @Autowired
    public AdministrateurController(AdministrateurService administrateurService) {
        this.administrateurService = administrateurService;
    }

    // Approuver une organisation
    @PostMapping("/approuver-organisation/{organisationId}")
    public String approuverOrganisation(@PathVariable Long organisationId) {
        administrateurService.approuverOrganisation(organisationId);
        return "Organisation approuvée avec succès!";
    }

    // Gérer un utilisateur (activer/désactiver)
    @PostMapping("/gerer-utilisateur/{utilisateurId}/{activer}")
    public String gererUtilisateur(@PathVariable Long utilisateurId, @PathVariable boolean activer) {
        administrateurService.gererUtilisateurs(utilisateurId, activer);
        return activer ? "Utilisateur activé avec succès!" : "Utilisateur désactivé avec succès!";
    }

    // Récupérer une organisation par son ID
    @GetMapping("/organisation/{organisationId}")
    public Object getOrganisation(@PathVariable Long organisationId) {
        return administrateurService.getOrganisation(organisationId);
    }

    // Récupérer toutes les organisations
    @GetMapping("/organisations")
    public Object getAllOrganisations() {
        return administrateurService.getAllOrganisations();
    }
}
