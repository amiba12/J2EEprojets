package Bah.emsi.charitywebapp.entities;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "organisations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logo;
    private String nom;
    private String adresseLegale;
    private String numeroFiscal;
    private String contact;
    private Boolean valide;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateCreation;


    @OneToMany(mappedBy = "organisation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ActionDeCharite> actionsDeCharite = new ArrayList<>(); // Initialisation de la liste

    public void créerAction(ActionDeCharite action) {
        // Logique de création d'action de charité ici
        actionsDeCharite.add(action);
    }

    public void modifierProfil() {
        // Logique de modification du profil de l'organisation ici
    }
}

