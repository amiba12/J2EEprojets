package Bah.emsi.charitywebapp.Controllers;

import Bah.emsi.charitywebapp.Services.ActionChariteService;
import Bah.emsi.charitywebapp.entities.ActionDeCharite;
import Bah.emsi.charitywebapp.entities.Don;
import Bah.emsi.charitywebapp.Services.DonService;
import Bah.emsi.charitywebapp.entities.Utilisateur;
import Bah.emsi.charitywebapp.repositories.UtilisateurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dons")
public class DonController {

    @Autowired
    private DonService donService;

    @Autowired
    private UtilisateurRepo utilisateurRepo;
    @Autowired
    private ActionChariteService actionDeChariteService;  // Ajout du service pour les actions de charité


    // Afficher la liste des dons avec un lien pour effectuer un don
    @GetMapping("/don")
    public String afficherListeDesDons(Model model) {
        // Récupère tous les dons
        List<Don> dons = donService.getAllDons();
        model.addAttribute("dons", dons);
       // System.out.println("Afficher liste des dons");  // Ajouter un log pour vérifier si la méthode est appelée
        return "dons/dons-liste";  // Page d'affichage des dons
    }



    // Afficher le formulaire pour effectuer un don
    @GetMapping("/effectuer")
    public String afficherFormulaireDon(Model model) {
        // Récupérer tous les utilisateurs (pour associer un utilisateur)
        List<Utilisateur> utilisateurs = utilisateurRepo.findAll();
        // Récupérer toutes les actions de charité (ajouté ici)
        List<ActionDeCharite> actionsDeCharite = actionDeChariteService.findAll(); // Je viens de modifier ici

        // Ajouter les données à la vue
        model.addAttribute("utilisateurs", utilisateurs);
        model.addAttribute("actionsDeCharite", actionsDeCharite);  // Je viens de modifier ici

        return "dons/don-effectuer";  // Retourner la vue Thymeleaf don-effectuer.html
    }


    // Effectuer un don (soumettre le formulaire)
    @PostMapping("/effectuer")
    public String effectuerDon(@RequestParam Long utilisateurId,
                               @RequestParam Long actionDeChariteId,
                               @RequestParam Double montant,
                               Model model) {
        try {
            Don donEnregistre = donService.effectuerDon(utilisateurId, actionDeChariteId, montant);
            model.addAttribute("don", donEnregistre);
            //return "dons/don-sucess";  // Afficher la page de succès
            return "dons/dons-liste";
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de l'enregistrement du don");
            return "dons/don-effectuer";  // Retourner au formulaire avec erreur
        }
    }

    // Afficher l'historique des dons de l'utilisateur
    @GetMapping("/historique")
    public String afficherHistoriqueDons(@RequestParam Long utilisateurId, Model model) {
        List<Don> dons = donService.getDonsByUtilisateurId(utilisateurId);
        model.addAttribute("dons", dons);
        return "dons/don-historique";  // Affiche l'historique des dons
    }

    // Afficher les détails d'un don spécifique
    @GetMapping("/details/{donId}")
    public String afficherDetailsDon(@PathVariable Long donId, Model model) {
        Don don = donService.getDonById(donId);
        model.addAttribute("don", don);
        return "dons/details";  // Affiche les détails d'un don
    }






}
