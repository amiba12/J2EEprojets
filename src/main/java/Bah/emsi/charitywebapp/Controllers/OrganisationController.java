package Bah.emsi.charitywebapp.Controllers;

import Bah.emsi.charitywebapp.entities.ActionDeCharite;
import Bah.emsi.charitywebapp.Services.OrganisationService;
import Bah.emsi.charitywebapp.entities.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/organisations")
public class OrganisationController {

    @Autowired
    private OrganisationService organisationService;

    // Affiche la liste des organisations (vue Thymeleaf)
    @GetMapping
    public String getAllOrganisations(Model model) {
        model.addAttribute("organisations", organisationService.getAllOrganisations());
        return "organisations";  // View: organisations.html
    }

    // Créer une organisation (appel API avec JSON)
    @PostMapping
    public ResponseEntity<Organisation> createOrganisation(@RequestBody Organisation organisation) {
        return ResponseEntity.ok(organisationService.saveOrganisation(organisation));
    }
    //pour html
    @PostMapping("/save")
    public String saveOrganisation(@ModelAttribute Organisation organisation,
                                   @RequestParam("logoFile") MultipartFile logoFile) {
        organisationService.saveOrganisationWithLogo(organisation, logoFile);
        return "redirect:/organisations";
    }

    // Modifier une organisation par ID (appel API)
    //@PutMapping("/{id}")
   // public ResponseEntity<Organisation> modifierOrganisation(@PathVariable Long id, @RequestBody Organisation organisation) {
     //   return ResponseEntity.ok(organisationService.modifierProfil(id, organisation));
    //}

    // Récupérer une organisation par ID (appel API)
    @GetMapping("/{id}")
    public ResponseEntity<Organisation> getOrganisation(@PathVariable Long id) {
        return ResponseEntity.ok(organisationService.getOrganisationById(id));
    }

    // Supprimer une organisation par ID (appel API)
    @PostMapping("/{id}")
    public ResponseEntity<String> deleteOrganisation(@PathVariable Long id) {
        organisationService.deleteOrganisation(id);
        return ResponseEntity.ok("Organisation supprimée avec succès !");
    }



    // Afficher les actions de charité d'une organisation (vue Thymeleaf)
    @GetMapping("/{id}/actions")
    public String afficherActionsPourOrganisation(@PathVariable Long id, Model model) {
        List<ActionDeCharite> actions = organisationService.getActionsParOrganisation(id);
        model.addAttribute("actions", actions);
        return "actiondetails";  // View: actiondetails.html
    }

    // Afficher les détails d'une organisation avec ses actions (vue Thymeleaf)
    @GetMapping("/details/{id}")
    public String afficherDetailsOrganisation(@PathVariable Long id, Model model) {
        Organisation organisation = organisationService.getOrganisationById(id);
        if (organisation == null) {
            return "erreur"; // page d'erreur si l'organisation n'existe pas
        }
        model.addAttribute("organisation", organisation);
        model.addAttribute("actions", organisation.getActionsDeCharite()); // ou organisationService.getActionsParOrganisation(id)
        return "organisation-details";  // View: organisation-details.html
    }

    // Afficher le formulaire pour créer une action de charité pour une organisation (vue Thymeleaf)
    @GetMapping("/{id}/actions/new")
    public String afficherFormulaireAction(@PathVariable Long id, Model model) {
        model.addAttribute("organisationId", id);
        model.addAttribute("action", new ActionDeCharite());
        return "formulaire-action"; // View: formulaire-action.html
    }
    @GetMapping("/form")
    public String showOrganisationForm(Model model) {
        model.addAttribute("organisation", new Organisation());
        return "Forms/organisation-form";
    }

    @PostMapping("/{id}/modif-logo")
    public String modifierOrganisationAvecLogo(@PathVariable Long id,
                                               @ModelAttribute Organisation organisation,
                                               @RequestParam(value = "logoFile", required = false) MultipartFile logoFile,
                                               Model model) {
        Organisation updated = organisationService.modifierProfil(id, organisation, logoFile);
        model.addAttribute("organisation", updated);
        return "Organisation-details"; // par exemple, la vue de détail après modif
    }

    @GetMapping("/form-modifier/{id}")
    public String afficherFormulaireModification(@PathVariable Long id, Model model) {
        Organisation organisation = organisationService.getOrganisationById(id);
        model.addAttribute("organisation", organisation);
        return "Forms/modifier-organisation"; // le formulaire HTML
    }




}
