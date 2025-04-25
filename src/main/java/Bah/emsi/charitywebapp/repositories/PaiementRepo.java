package Bah.emsi.charitywebapp.repositories;


import Bah.emsi.charitywebapp.entities.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementRepo extends JpaRepository<Paiement, Long> {
    // Tu peux ajouter des méthodes personnalisées ici si nécessaire
}
