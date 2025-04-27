package Bah.emsi.charitywebapp.repositories;
import Bah.emsi.charitywebapp.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepo extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);
    boolean existsByRole(String role); // Cette méthode va vérifier l'existence d'un utilisateur avec un rôle spécifique
    boolean existsByEmail(String email); // Vérifier si un utilisateur existe déjà avec l'email donné
    List<Utilisateur> findByRole(String role);
    Utilisateur findOneByRole(String role);

}
