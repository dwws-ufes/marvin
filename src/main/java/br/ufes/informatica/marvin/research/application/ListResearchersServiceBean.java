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
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Stateless
@RolesAllowed({ "SysAdmin", "Professor" })
public class ListResearchersServiceBean implements ListResearchersService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(ListResearchersServiceBean.class.getCanonicalName());

	/** The DAO for Academic objects. */
	@EJB
	private AcademicDAO academicDAO;

	/** TODO: document this field. */
	@EJB
	private PublicationDAO publicationDAO;

	/** @see br.ufes.informatica.marvin.research.application.ListResearchersService#listResearchers() */
	@Override
	public List<Academic> listResearchers() {
		logger.log(Level.INFO, "Listing researchers...");

		// Retrieves the researchers, sorts the list and returns.
		List<Academic> researchers = academicDAO.retrieveResearchers();
		Collections.sort(researchers);
		return researchers;
	}

	/**
	 * @see br.ufes.informatica.marvin.research.application.ListResearchersService#countPublications(br.ufes.informatica.marvin.core.domain.Academic)
	 */
	@Override
	public Long countPublications(Academic researcher) {
		logger.log(Level.INFO, "Retrieving the number of publications of a researcher...");
		return publicationDAO.retrieveCountByAcademic(researcher);
	}
}
