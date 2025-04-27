package Bah.emsi.charitywebapp.Controllers;

import Bah.emsi.charitywebapp.entities.Administrateur;
import Bah.emsi.charitywebapp.entities.Don;
import Bah.emsi.charitywebapp.entities.Utilisateur;
import Bah.emsi.charitywebapp.Services.UtilisateurService;
import Bah.emsi.charitywebapp.repositories.UtilisateurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private UtilisateurRepo utilisateurRepo;

    // ------------------- Inscription -------------------

    // Affichage du formulaire d'inscription
    @GetMapping("/inscription")
    public String afficherFormulaireInscription(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "authentification/inscription";
    }
    // Traitement du formulaire d'inscription
    @PostMapping("/inscription")
    public String traiterFormulaireInscription(@ModelAttribute Utilisateur utilisateur) {
        utilisateurService.sInscrire(utilisateur);  // Inscrire l'utilisateur via le service
        return "redirect:/utilisateurs/connexion";  // Après inscription, rediriger vers la connexion
    }

    // ------------------- Connexion -------------------

    // Affichage du formulaire de connexion
    @GetMapping("/connexion")
    public String afficherFormulaireConnexion() {
        return "authentification/connexion";  // La page de connexion
    }

    @PostMapping("/connexion")
    public String traiterFormulaireConnexion(@RequestParam String email, @RequestParam String motDePasse, Model model) {
        Utilisateur utilisateur = utilisateurService.seConnecter(email, motDePasse);

        if (utilisateur != null) {
            model.addAttribute("utilisateur", utilisateur);
            return "/home_user";  // Connexion réussie → page d'accueil
        } else {
            model.addAttribute("erreur", "Email ou mot de passe incorrect");
            return "authentification/inscription";  // Reste sur la page connexion et affiche erreur
        }
    }

    @PostMapping("/{utilisateurId}/participer/{actionId}")
    public ResponseEntity<String> participerAAction(@PathVariable Long utilisateurId, @PathVariable Long actionId) {
        try {
            utilisateurService.participerAAction(utilisateurId, actionId);
            return ResponseEntity.status(HttpStatus.OK).body("L'utilisateur a bien participé à l'action.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{utilisateurId}/historique")
    public ResponseEntity<List<Don>> getHistoriqueDeDons(@PathVariable Long utilisateurId) {
        Utilisateur utilisateur = utilisateurRepo.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return ResponseEntity.ok(utilisateur.getHistoriqueDons());
    }


    // Route pour afficher tous les utilisateurs pour l'admin
    @GetMapping("/list-afficher")
    public String listeUtilisateurs(Model model) {
        model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateurs());
        return "admin/utilisateurs";  // Page HTML qui affiche la liste des utilisateurs
    }
    @GetMapping("/details/{id}")
    public String afficherDetailsUtilisateur(@PathVariable Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
        if (utilisateur == null) {
            // Optionnel : Gérer les cas où l'utilisateur n'est pas trouvé
            return "error";  // Ou rediriger vers une page d'erreur
        }
        model.addAttribute("utilisateur", utilisateur);
        return "admin/utilisateur-details";  // La vue qui affiche les détails de l'utilisateur
    }


    @GetMapping("/ajouter")
    public String afficherFormulaireAjout(Model model) {
        model.addAttribute("utilisateur", new Utilisateur()); // Créer un nouvel objet Utilisateur pour le formulaire
        return "/admin/ajouter";  // La vue avec le formulaire
    }

    @PostMapping("/ajouter")
    public String ajouterUtilisateur(@ModelAttribute Utilisateur utilisateur) {
        utilisateurService.saveUtilisateur(utilisateur); // Appel à la méthode de service pour sauvegarder l'utilisateur
        return "redirect:/utilisateurs/list-afficher";  // Rediriger vers la liste des utilisateurs
    }



    // Route pour afficher le formulaire de modification d'un utilisateur
    @GetMapping("/modifier/{id}")
    public String showModifierForm(@PathVariable Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
        model.addAttribute("utilisateur", utilisateur);
        return "admin/modifier-utilisateurs";  // Page HTML pour modifier un utilisateur
    }

    // Route pour effectuer la modification d'un utilisateur
    @PostMapping("/modifier/{id}")
    public String modifierUtilisateur(@PathVariable Long id, @ModelAttribute Utilisateur utilisateur) {
        utilisateurService.modifierProfil(id, utilisateur);
        return "redirect:/utilisateurs/list-afficher";  // Redirige vers la liste des utilisateurs
    }


    // Route pour supprimer un utilisateur
    @PostMapping("/supprimer/{id}")
    public String supprimerUtilisateur(@PathVariable Long id) {
        utilisateurService.supprimerUtilisateur(id);
        return "redirect:/utilisateurs/list-afficher";  // Redirige vers la liste après suppression
    }


    // Page d'accueil en fonction du rôle de l'utilisateur
    @GetMapping("/accueil")
    public String afficherPageAccueilUtilisateur(Model model, Principal principal) {
        // Récupérer l'utilisateur connecté par son email (principal.getName())
        String username = principal.getName();
        Utilisateur utilisateur = utilisateurService.findByEmail(username); // Tu récupères l'utilisateur générique

        // Vérification si l'utilisateur est un Administrateur
        if (utilisateur instanceof Administrateur) {
            // L'utilisateur est un Administrateur, donc on peut accéder à getRole()
            Administrateur administrateur = (Administrateur) utilisateur;
            if (administrateur.getRole().equals("ADMIN")) {
                return "home";  // Redirige vers la page d'accueil de l'admin
            }
        }
        // Si ce n'est pas un Administrateur, c'est un Utilisateur classique
        return "users/home-user";  // Page d'accueil pour les utilisateurs
    }


}
