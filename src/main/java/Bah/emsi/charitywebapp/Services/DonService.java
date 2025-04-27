package Bah.emsi.charitywebapp.Services;

import Bah.emsi.charitywebapp.entities.ActionDeCharite;
import Bah.emsi.charitywebapp.entities.Don;
import Bah.emsi.charitywebapp.entities.Paiement;
import Bah.emsi.charitywebapp.entities.Utilisateur;
import Bah.emsi.charitywebapp.repositories.ActionDeChariteRepo;
import Bah.emsi.charitywebapp.repositories.DonRepo;
import Bah.emsi.charitywebapp.repositories.PaiementRepo;
import Bah.emsi.charitywebapp.repositories.UtilisateurRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DonService {

    @Autowired
    private DonRepo donRepo;

    @Autowired
    private UtilisateurRepo utilisateurRepo;

    @Autowired
    private PaiementRepo paiementRepo;
    @Autowired
    private ActionDeChariteRepo actionDeChariteRepo;

    // Enregistrer un don
    public Don enregistrerDon(Don don) {
        return donRepo.save(don);
    }

    public List<Don> getAllDons() {
        return donRepo.findAll();
    }

    public Don getDonById(Long id) {
        return donRepo.findById(id).orElseThrow(() -> new RuntimeException("Don non trouvé"));
    }

    // Effectuer un don : enregistre le don, met à jour l'historique de l'utilisateur et crée le paiement
    public Don effectuerDon(Long utilisateurId, Long actionDeChariteId, Double montant) {
        // Trouver l'utilisateur
        Utilisateur utilisateur = utilisateurRepo.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Trouver l'action de charité
        ActionDeCharite actionDeCharite = actionDeChariteRepo.findById(actionDeChariteId)
                .orElseThrow(() -> new RuntimeException("Action de charité non trouvée"));

        // Créer le don et l'associer à l'utilisateur
        Don don = new Don();
        don.setUtilisateur(utilisateur);
        don.setMontant(montant);
        don.setDate(new Date());  // Date du don

        // Associer l'action de charité au don
        don.setActionDeCharite(actionDeCharite);

        // Créer et associer un paiement au don
        Paiement paiement = new Paiement();
        paiement.setMontant(montant); // Associer le montant au paiement
        paiement.setMethode("PayPal"); // Exemple de méthode de paiement
        paiement.setStatut("en attente"); // Statut initial du paiement
        paiementRepo.save(paiement);   // Sauvegarder le paiement

        don.setPaiement(paiement);  // Associer le paiement au don

        // Sauvegarder le don
        Don donEnregistre = donRepo.save(don);

        // Ajouter ce don à l'historique des dons de l'utilisateur
        utilisateur.getHistoriqueDons().add(donEnregistre);
        utilisateurRepo.save(utilisateur);

        // Ajouter l'utilisateur aux participants de l'action de charité
        if (!utilisateur.getActions().contains(actionDeCharite)) {
            utilisateur.getActions().add(actionDeCharite);
            utilisateurRepo.save(utilisateur);
        }

        return donEnregistre;
    // Enregistrer le don dans la base de données
            //return donRepo.save(don);
    }
    public List<Don> getDonsByUtilisateurId(Long utilisateurId) {
        return donRepo.findByUtilisateurId(utilisateurId);
    }


}

