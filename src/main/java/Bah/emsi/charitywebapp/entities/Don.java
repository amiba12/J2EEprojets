package Bah.emsi.charitywebapp.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "dons")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Don {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double montant;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    @JsonBackReference
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "action_de_charite_id")
    @JsonIgnoreProperties({"participants", "organisation", "medias"})
    private ActionDeCharite actionDeCharite;

    @OneToOne
    @JoinColumn(name = "paiement_id")
    private Paiement paiement;


    public void effectuerDon() {
        // Logique d'effectuation du don ici
    }
}
