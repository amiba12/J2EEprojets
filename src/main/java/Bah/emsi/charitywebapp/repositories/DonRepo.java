package Bah.emsi.charitywebapp.repositories;
import Bah.emsi.charitywebapp.entities.Don;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonRepo extends JpaRepository<Don, Long> {
    // Tu peux ajouter des méthodes personnalisées ici si nécessaire
}

