package Bah.emsi.charitywebapp.Controllers;

import Bah.emsi.charitywebapp.entities.ActionDeCharite;
import Bah.emsi.charitywebapp.entities.Organisation;
import Bah.emsi.charitywebapp.entities.Utilisateur;
import Bah.emsi.charitywebapp.repositories.ActionDeChariteRepo;
import Bah.emsi.charitywebapp.repositories.OrganisationRepo;
import Bah.emsi.charitywebapp.repositories.UtilisateurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private OrganisationRepo organisationRepo;

    @Autowired
    private ActionDeChariteRepo actionDeChariteRepo;
    @Autowired
    private UtilisateurRepo utilisateurRepo;

    @GetMapping("/")
    public String home(Model model) {
        // Vérifier si l'utilisateur connecté est un admin
        if (!isAdmin()) {
            return "redirect:/connexion";  // Rediriger si ce n'est pas un admin
        }
        List<Organisation> organisations = organisationRepo.findAll();
        List<ActionDeCharite> actions = actionDeChariteRepo.findAll();
        // List<Utilisateur> utilisateurs = utilisateurRepo.findAll();


        model.addAttribute("organisations", organisations);
        model.addAttribute("actions", actions);
        //model.addAttribute("utilisateurs", utilisateurs);

        return "home";  // Renvoie la vue home.html
    }
    private boolean isAdmin() {
        // Logique pour vérifier si l'utilisateur est un admin, par exemple avec un rôle dans la session
        return true;  // Cette méthode doit être adaptée selon ton système d'authentification
    }

}
