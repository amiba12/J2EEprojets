package Bah.emsi.charitywebapp.Controllers;

import Bah.emsi.charitywebapp.entities.Don;
import Bah.emsi.charitywebapp.Services.DonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dons")
public class DonController {

    @Autowired
    private DonService donService;

    // Effectuer un don (avec utilisateurId, actionDeChariteId et montant)
    @PostMapping("/effectuer")
    public ResponseEntity<Don> effectuerDon(@RequestParam Long utilisateurId,
                                            @RequestParam Long actionDeChariteId,
                                            @RequestParam Double montant) {
        try {
            // Utilisation de la méthode effectuerDon du service
            Don donEnregistre = donService.effectuerDon(utilisateurId, actionDeChariteId, montant);
            return ResponseEntity.status(HttpStatus.CREATED).body(donEnregistre);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Récupérer un don par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Don> getDonById(@PathVariable Long id) {
        Don don = donService.getDonById(id);
        if (don != null) {
            return ResponseEntity.ok(don);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Récupérer tous les dons
    @GetMapping("/all")
    public List<Don> getAllDons() {
        return donService.getAllDons();
    }
}
