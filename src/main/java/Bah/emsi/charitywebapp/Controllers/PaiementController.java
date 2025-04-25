package Bah.emsi.charitywebapp.Controllers;

import Bah.emsi.charitywebapp.entities.Paiement;
import Bah.emsi.charitywebapp.Services.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paiements")
public class PaiementController {

    @Autowired
    private PaiementService paiementService;

    @PostMapping
    public ResponseEntity<Paiement> enregistrerPaiement(@RequestBody Paiement paiement) {
        Paiement paiementEnregistre = paiementService.enregistrerPaiement(paiement);
        return ResponseEntity.status(HttpStatus.CREATED).body(paiementEnregistre);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paiement> getPaiementById(@PathVariable Long id) {
        return ResponseEntity.ok(paiementService.getPaiementById(id));
    }
    // Endpoint pour traiter le paiement
    @PostMapping("/traiter/{id}")
    public ResponseEntity<Paiement> traiterPaiement(@PathVariable Long id) {
        Paiement paiement = paiementService.traiterPaiement(id);
        return ResponseEntity.ok(paiement);  // Retourner le paiement après traitement
    }
}

