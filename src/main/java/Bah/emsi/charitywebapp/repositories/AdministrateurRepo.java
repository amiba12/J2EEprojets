package Bah.emsi.charitywebapp.repositories;

import Bah.emsi.charitywebapp.entities.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministrateurRepo extends JpaRepository<Administrateur, Long> {
    // Vous pouvez ajouter des méthodes personnalisées ici si nécessaire
    // Par exemple, trouver un administrateur par rôle :
    Administrateur findByRole(String role);
}

