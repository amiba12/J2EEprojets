package Bah.emsi.charitywebapp.Controllers;

import Bah.emsi.charitywebapp.Services.ActionChariteService;
import Bah.emsi.charitywebapp.Services.OrganisationService;
import Bah.emsi.charitywebapp.entities.ActionDeCharite;
import Bah.emsi.charitywebapp.entities.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/actions")
public class ActionDeChariteController {

    @Autowired
    private ActionChariteService actionDeChariteService;
    @Autowired
    private OrganisationService organisationService;

    // Méthode pour récupérer toutes les actions de charité et les afficher avec Thymeleaf
    //@GetMapping("/actions")
    @GetMapping
    public String getAllActionsDeCharite(Model model) {
        List<ActionDeCharite> actions = actionDeChariteService.findAll();
        model.addAttribute("actions", actions); // Ajout de la liste d'actions au modèle
        return "actions";  // Renvoie la vue Thymeleaf "actions.html"
    }

    // Méthode pour récupérer une action de charité par son ID et afficher les détails
    @GetMapping("/actionDeCharite/{id}")
    public String getActionDetails(@PathVariable Long id, Model model) {
        Optional<ActionDeCharite> actionOpt = actionDeChariteService.findById(id);
        if (actionOpt.isPresent()) {
            model.addAttribute("action", actionOpt.get()); // Ajout de l'action au modèle
            return "action-details"; // Renvoie la vue Thymeleaf "action-details.html"
        } else {
            return "redirect:/error"; // Redirige vers la liste des actions si l'action n'est pas trouvée
        }
    }

    // Méthode pour créer une action de charité
    //@PostMapping
    //public ResponseEntity<ActionDeCharite> createAction(@RequestBody ActionDeCharite action) {
      //  ActionDeCharite createdAction = actionDeChariteService.saveActionDeCharite(action);
        //return ResponseEntity.ok(createdAction);
   // }

    @PostMapping("/save")
    public String saveAction(@ModelAttribute("actionDeCharite") ActionDeCharite action,
                             @RequestParam("organisationId") Long organisationId,
                             @RequestParam("medias") List<MultipartFile> medias,
                             RedirectAttributes redirectAttributes) {
        try {
            // Appel du service de l'organisation
            organisationService.creerActionPourOrganisation(organisationId, action, medias);

            redirectAttributes.addFlashAttribute("success", "Action enregistrée avec succès !");
            return "redirect:/actions";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Une erreur est survenue : " + e.getMessage());
            return "redirect:/actions/create-form";
        }
    }

    @PostMapping("/modifier")
    public String modifierAction(@ModelAttribute("actionDeCharite") ActionDeCharite actionForm,
                                 @RequestParam("organisationId") Long organisationId) {

        // Récupérer l'organisation sélectionnée
        Organisation organisation = organisationService.getOrganisationById(organisationId);
        if (organisation == null) {
            throw new RuntimeException("Organisation non trouvée");
        }
        // Associer l'organisation à l'action modifiée
        actionForm.setOrganisation(organisation);

        // Appel du service pour appliquer les modifications
        actionDeChariteService.modifierAction(actionForm.getId(), actionForm);

        // Redirection vers la liste des actions (ou autre vue)
        return "redirect:/actions";
    }



    // Supprimer une action de charité
    @GetMapping("/delete/{id}")
    public String deleteAction(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            actionDeChariteService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Action de charité supprimée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Une erreur est survenue lors de la suppression.");
        }
        return "redirect:/actions";
    }

    // Méthode pour ajouter des médias à une action de charité existante
    @PutMapping("/{id}/addMedia")
    public ResponseEntity<ActionDeCharite> ajouterMedia(
            @PathVariable Long id,
            @RequestBody List<String> nouveauxMedias) {
        ActionDeCharite updated = actionDeChariteService.ajouterMedia(id, nouveauxMedias);
        return ResponseEntity.ok(updated);
    }


    // Afficher le formulaire de création d'une nouvelle action de charité
    @GetMapping("/create-form")
    public String createActionForm(Model model) {
        model.addAttribute("actionDeCharite", new ActionDeCharite());
        model.addAttribute("organisations", organisationService.getAllOrganisations());
        return "Forms/action-form"; // Formulaire pour créer une action
    }

    @GetMapping("/form-edit/{id}")
    public String editActionForm(@PathVariable("id") Long id, Model model) {
        // Déballer l'Optional et récupérer l'objet ActionDeCharite
        ActionDeCharite action = actionDeChariteService.findById(id)
                .orElseThrow(() -> new RuntimeException("Action de charité non trouvée"));
        // Récupére l'organisation associée à cette action
        Organisation organisation = action.getOrganisation(); // Assuming getOrganisation() exists in ActionDeCharite
        // Ajout l'action et l'organisation au modèle
        model.addAttribute("actionDeCharite", action);
        model.addAttribute("organisation", organisation); // Juste l'organisation liée à l'action
        // Retourner le formulaire pour modifier l'action
        return "Forms/modif-action"; // Formulaire pour modifier une action
    }

}
