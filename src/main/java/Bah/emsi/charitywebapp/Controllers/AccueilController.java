package Bah.emsi.charitywebapp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccueilController {

    // Gérer la page d'accueil (preaccueil)
    @GetMapping("/home")
    public String afficherPagePreaccueil() {
        return "Accueil";  // Nom de la vue (préaccueil.html)
    }

}

