package Bah.emsi.charitywebapp.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "administrateurs")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Administrateur extends Utilisateur {

    private String role= "Administrateur";

    {
        this.setRole("Administrateur");
    }


    public void approuverOrganisation(Organisation organisation) {
        // Logique pour approuver l'organisation ici
        organisation.setValide(true);
    }

    // Getter et Setter pour le rôle
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



}

