package Bah.emsi.charitywebapp.Controllers;
import Bah.emsi.charitywebapp.repositories.UtilisateurRepo;
import Bah.emsi.charitywebapp.entities.Don;
import Bah.emsi.charitywebapp.entities.Utilisateur;
import Bah.emsi.charitywebapp.Services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;
    private UtilisateurRepo utilisateurRepo;

    @GetMapping("")
    public ResponseEntity<Iterable<Utilisateur>> listerUtilisateurs() {
        return ResponseEntity.ok(utilisateurService.getAllUtilisateurs());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateur(@PathVariable Long id) {
        return ResponseEntity.ok(utilisateurService.getUtilisateurById(id));
    }

    @PutMapping("/{id}/modifier")
    public ResponseEntity<Utilisateur> modifierProfil(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
        return ResponseEntity.ok(utilisateurService.modifierProfil(id, utilisateur));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerUtilisateur(@PathVariable Long id) {
        boolean isDeleted = utilisateurService.supprimerUtilisateur(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();  // 204 No Content
        } else {
            return ResponseEntity.notFound().build();  // 404 Not Found
        }
    }


    @GetMapping("/tous")
    public ResponseEntity<Iterable<Utilisateur>> getAllUtilisateurs() {
        return ResponseEntity.ok(utilisateurService.getAllUtilisateurs());
    }


    @PostMapping("/inscription")
    public ResponseEntity<Utilisateur> sInscrire(@RequestBody Utilisateur utilisateur) {
        Utilisateur utilisateurInscrit = utilisateurService.sInscrire(utilisateur);
        return ResponseEntity.status(HttpStatus.CREATED).body(utilisateurInscrit);
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

        // Retourner l'historique des dons
        return ResponseEntity.ok(utilisateur.getHistoriqueDons());
    }


    @PostMapping("/connexion")
    public ResponseEntity<Utilisateur> seConnecter(@RequestParam String email, @RequestParam String motDePasse) {
        Utilisateur utilisateur = utilisateurService.seConnecter(email, motDePasse);

        // Si la connexion est réussie, rediriger l'utilisateur vers la page d'accueil
        if (utilisateur != null) {
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/home").build();
        } else {
            // Si la connexion échoue, renvoyer une erreur 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
