package Bah.emsi.charitywebapp.repositories;
import Bah.emsi.charitywebapp.entities.Don;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonRepo extends JpaRepository<Don, Long> {
    List<Don> findByUtilisateurId(Long utilisateurId);

    // Tu peux ajouter des méthodes personnalisées ici si nécessaire
}

