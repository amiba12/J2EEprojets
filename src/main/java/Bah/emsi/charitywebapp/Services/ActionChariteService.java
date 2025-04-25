package Bah.emsi.charitywebapp.Services;
import Bah.emsi.charitywebapp.entities.ActionDeCharite;
import Bah.emsi.charitywebapp.repositories.ActionDeChariteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service

public class ActionChariteService {

    @Autowired
    private ActionDeChariteRepo actionDeChariteRepo;

    // Méthode pour récupérer toutes les actions de charité
    public List<ActionDeCharite> findAll() {
        return actionDeChariteRepo.findAll();
    }

    // Méthode pour récupérer une action de charité par ID
    @Transactional(readOnly = true)
    public Optional<ActionDeCharite> findById(Long id) {

        return actionDeChariteRepo.findById(id);
    }

    public void delete(Long id) {
        actionDeChariteRepo.deleteById(id);
    }


    public ActionDeCharite saveActionDeCharite(ActionDeCharite action) {
        return actionDeChariteRepo.save(action);
    }

    public ActionDeCharite ajouterMedia(Long actionId, List<String> nouveauxMedias) {
        ActionDeCharite action = actionDeChariteRepo.findById(actionId)
                .orElseThrow(() -> new RuntimeException("Action non trouvée avec l'id : " + actionId));

        action.getMediaFilenames().addAll(nouveauxMedias); // Ajoute les nouveaux liens à la liste
        return actionDeChariteRepo.save(action); // Sauvegarde la mise à jour
    }


    public ActionDeCharite modifierAction(Long id, ActionDeCharite updatedAction) {
        ActionDeCharite actionExistante = actionDeChariteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Action de charité non trouvée"));
        if (updatedAction.getTitre() != null) actionExistante.setTitre(updatedAction.getTitre());
        if (updatedAction.getDescription() != null) actionExistante.setDescription(updatedAction.getDescription());
        if (updatedAction.getDate() != null) actionExistante.setDate(updatedAction.getDate());
        if (updatedAction.getLieu() != null) actionExistante.setLieu(updatedAction.getLieu());
        if (updatedAction.getObjectif() != null) actionExistante.setObjectif(updatedAction.getObjectif());
        return actionDeChariteRepo.save(actionExistante);

    }
}
