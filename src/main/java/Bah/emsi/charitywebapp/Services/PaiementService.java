package Bah.emsi.charitywebapp.Services;

import Bah.emsi.charitywebapp.entities.Paiement;
import Bah.emsi.charitywebapp.repositories.PaiementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepo paiementRepo;

    public Paiement enregistrerPaiement(Paiement paiement) {
        return paiementRepo.save(paiement);
    }

    public Paiement getPaiementById(Long id) {
        return paiementRepo.findById(id).orElseThrow(() -> new RuntimeException("Paiement non trouvé"));
    }
    // Méthode pour traiter le paiement
    public Paiement traiterPaiement(Long paiementId) {
        // Récupérer le paiement
        Paiement paiement = paiementRepo.findById(paiementId)
                .orElseThrow(() -> new RuntimeException("Paiement non trouvé"));

        // Logique de traitement du paiement (simulation ou intégration avec un fournisseur de paiement)
        if ("succès".equals(paiement.getStatut())) {
            // Paiement déjà effectué, retourner l'objet sans modification
            return paiement;
        }

        // Simuler un traitement du paiement
        paiement.setStatut("succès"); // Mise à jour du statut à "succès" après traitement réussi
        // On pourrait aussi ajouter des étapes supplémentaires, comme la communication avec une API externe
        // Enregistrer le paiement après traitement
        return paiementRepo.save(paiement);
    }

    }

