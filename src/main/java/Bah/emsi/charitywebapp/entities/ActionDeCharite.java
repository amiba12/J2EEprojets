package Bah.emsi.charitywebapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Entity
@Table(name = "actions_de_charite")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ActionDeCharite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String lieu;
    private Double objectif;

    //private List<String> medias;  // Liste des médias associés à l'action
    private List<String> mediaFilenames;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;


    //@ManyToMany
    //@JoinTable(
      //      name = "utilisateur_action",
        //    joinColumns = @JoinColumn(name = "action_id"),
          //  inverseJoinColumns = @JoinColumn(name = "utilisateur_id")
    //)

    @ManyToMany(mappedBy = "actions")
    @JsonManagedReference
    private List<Utilisateur> participants = new ArrayList<>();


    //@ManyToMany
    //@JoinTable(
      //      name = "utilisateur_action",
           // joinColumns = @JoinColumn(name = "utilisateur_id"),
        //    inverseJoinColumns = @JoinColumn(name = "action_id")
    //)
    //private Set<ActionDeCharite> actions = new HashSet<>();


}
