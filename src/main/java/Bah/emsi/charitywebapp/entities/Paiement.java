package Bah.emsi.charitywebapp.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "paiements")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String methode;  // Exemple: PayPal, Stripe, etc.
    private String statut;   // Exemple: "succès", "échec"
    private Double montant;  // Ajout de l'attribut montant

    @OneToOne(mappedBy = "paiement")
    @JsonBackReference
    private Don don;

    public void traiterPaiement() {
        // Logique de traitement du paiement ici
    }
}
