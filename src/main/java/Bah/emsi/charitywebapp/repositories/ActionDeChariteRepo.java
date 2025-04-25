package Bah.emsi.charitywebapp.repositories;

import Bah.emsi.charitywebapp.entities.ActionDeCharite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionDeChariteRepo extends JpaRepository<ActionDeCharite, Long> {
    List<ActionDeCharite> findByOrganisationId(Long organisationId);
}
