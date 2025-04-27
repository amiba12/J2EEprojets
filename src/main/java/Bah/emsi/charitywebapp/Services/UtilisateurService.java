package Bah.emsi.charitywebapp.Services;

import Bah.emsi.charitywebapp.entities.ActionDeCharite;
import Bah.emsi.charitywebapp.entities.Administrateur;
import Bah.emsi.charitywebapp.entities.Utilisateur;
import Bah.emsi.charitywebapp.repositories.ActionDeChariteRepo;
import Bah.emsi.charitywebapp.repositories.UtilisateurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepo utilisateurRepo;
    @Autowired
    private ActionDeChariteRepo actionDeChariteRepo;  // Injection du repos

    @Autowired
    private PasswordEncoder passwordEncoder; // Injection du bean PasswordEncoder (pas besoin de BCryptPasswordEncoder)


    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepo.save(utilisateur);
    }

    public Utilisateur getUtilisateurById(Long id) {
        return utilisateurRepo.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }
    public Utilisateur findByEmail(String email) {
        return utilisateurRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("mail non trouvé"));
    }

    public Iterable<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepo.findAll();
    }


    public Utilisateur modifierProfil(Long id, Utilisateur utilisateur) {
        Utilisateur existingUtilisateur = utilisateurRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        // Mettre à jour uniquement les champs qui ont été envoyés
        if (utilisateur.getNom() != null) existingUtilisateur.setNom(utilisateur.getNom());
        if (utilisateur.getEmail() != null) existingUtilisateur.setEmail(utilisateur.getEmail());
        if (utilisateur.getMotDePasse() != null) existingUtilisateur.setMotDePasse(utilisateur.getMotDePasse());

        return utilisateurRepo.save(existingUtilisateur);
    }

    public void participerAAction(Long utilisateurId, Long actionId) {
        Utilisateur utilisateur = utilisateurRepo.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        ActionDeCharite action = actionDeChariteRepo.findById(actionId) // Assure-toi d'avoir le bon repo ici
                .orElseThrow(() -> new RuntimeException("Action non trouvée"));

        // Ajouter l'action à la liste des participants de l'utilisateur
        if (!utilisateur.getActions().contains(action)) {
            utilisateur.getActions().add(action);
        }

        // Ajouter l'utilisateur à la liste des participants de l'action (si nécessaire)
        if (!action.getParticipants().contains(utilisateur)) {
            action.getParticipants().add(utilisateur);
        }


        utilisateurRepo.save(utilisateur);  // Sauvegarder l'utilisateur
        actionDeChariteRepo.save(action);   // Sauvegarder l'action si nécessaire
    }


    public void sInscrire(Utilisateur utilisateur) {
        // Vérifier s'il existe déjà un administrateur
        if (utilisateurRepo.findByRole("Administrateur").isEmpty()) {
            // Si aucun administrateur n'existe, l'utilisateur sera un administrateur par défaut
            if (utilisateur instanceof Administrateur) {
                // Si l'utilisateur est de type Administrateur, lui attribuer le rôle ADMIN
                ((Administrateur) utilisateur).setRole("Aministrateur");
            }
        } else {
            // Sinon, l'utilisateur devient un simple utilisateur
            if (utilisateur instanceof Administrateur) {
                // Si c'est un administrateur, l'attribuer en rôle USER par défaut
                ((Administrateur) utilisateur).setRole("Utilisateur");
            }
        }
        // Hash du mot de passe avant de l'enregistrer
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        // Vérifier si l'email est déjà utilisé
        if (utilisateurRepo.existsByEmail(utilisateur.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }
        // Enregistrement de l'utilisateur dans la base de données
        utilisateurRepo.save(utilisateur);
    }



    public Utilisateur seConnecter(String email, String motDePasse) {
        Utilisateur utilisateur = utilisateurRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérification du mot de passe
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(motDePasse, utilisateur.getMotDePasse())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        return utilisateur; // Retourner l'utilisateur si la connexion est réussie
    }



    public boolean supprimerUtilisateur(Long id) {
        Optional<Utilisateur> utilisateur = utilisateurRepo.findById(id);
        if (utilisateur.isPresent()) {
            utilisateurRepo.deleteById(id);
            return true;  // L'utilisateur a été supprimé
        }
        return false;  // L'utilisateur n'a pas été trouvé
    }


}
