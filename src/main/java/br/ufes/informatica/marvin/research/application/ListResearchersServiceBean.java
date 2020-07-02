package br.ufes.informatica.marvin.research.application;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.persistence.AcademicDAO;
import br.ufes.informatica.marvin.research.persistence.PublicationDAO;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Stateless
@RolesAllowed({"SysAdmin", "Professor"})
public class ListResearchersServiceBean implements ListResearchersService {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** Logger for this class. */
  private static final Logger logger =
      Logger.getLogger(ListResearchersServiceBean.class.getCanonicalName());

  /** TODO: document this field. */
  @EJB
  private AcademicDAO academicDAO;

  /** TODO: document this field. */
  @EJB
  private PublicationDAO publicationDAO;

  @Override
  public List<Academic> listResearchers() {
    logger.log(Level.INFO, "Listing researchers...");

    // Retrieves the researchers, sorts the list and returns.
    List<Academic> researchers = academicDAO.retrieveResearchers();
    Collections.sort(researchers);
    return researchers;
  }

  @Override
  public Long countPublications(Academic researcher) {
    logger.log(Level.INFO, "Retrieving the number of publications of a researcher...");
    return publicationDAO.retrieveCountByAcademic(researcher);
  }
}
