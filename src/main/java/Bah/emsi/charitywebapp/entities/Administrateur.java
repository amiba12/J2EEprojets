package Bah.emsi.charitywebapp.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
//@Table(name = "administrateurs")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Administrateur extends Utilisateur {

    private String role;

    public void approuverOrganisation(Organisation organisation) {
        // Logique pour approuver l'organisation ici
        organisation.setValide(true);
    }

    public void gérerUtilisateurs() {
        // Logique pour gérer les utilisateurs ici
    }
}

