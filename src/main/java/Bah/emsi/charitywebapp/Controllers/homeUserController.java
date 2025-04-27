package Bah.emsi.charitywebapp.Controllers;

import Bah.emsi.charitywebapp.entities.ActionDeCharite;
import Bah.emsi.charitywebapp.entities.Organisation;
import Bah.emsi.charitywebapp.repositories.ActionDeChariteRepo;
import Bah.emsi.charitywebapp.repositories.OrganisationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class homeUserController {

    @Autowired
    private OrganisationRepo organisationRepo;

    @Autowired
    private ActionDeChariteRepo actionDeChariteRepo;

    @GetMapping("/user/home")
    public String homeUser(Model model) {
        // Vérifier si l'utilisateur est un utilisateur et non un admin
        if (!isUser()) {
            return "redirect:/connexion";  // Rediriger si ce n'est pas un utilisateur
        }

        List<Organisation> organisations = organisationRepo.findAll();
        List<ActionDeCharite> actions = actionDeChariteRepo.findAll();

        model.addAttribute("organisations", organisations);
        model.addAttribute("actions", actions);

        return "home_user";  // Renvoie la vue pour l'utilisateur
    }

    private boolean isUser() {
        // Logique pour vérifier si l'utilisateur est un utilisateur
        return true;  // Adapter selon ton système d'authentification
    }
}
