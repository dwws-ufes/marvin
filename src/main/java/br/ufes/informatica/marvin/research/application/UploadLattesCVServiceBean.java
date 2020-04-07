package br.ufes.informatica.marvin.research.application;

import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import br.ufes.inf.nemo.jbutler.ejb.controller.PersistentObjectConverterFromId;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.persistence.AcademicDAO;
import br.ufes.informatica.marvin.research.domain.BookChapter;
import br.ufes.informatica.marvin.research.domain.Publication;
import br.ufes.informatica.marvin.research.domain.Venue;
import br.ufes.informatica.marvin.research.exceptions.LattesIdNotRegisteredException;
import br.ufes.informatica.marvin.research.exceptions.LattesParseException;
import br.ufes.informatica.marvin.research.persistence.PublicationDAO;
import br.ufes.informatica.marvin.research.persistence.VenueDAO;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Stateless
@RolesAllowed({ "SysAdmin", "Professor" })
public class UploadLattesCVServiceBean implements UploadLattesCVService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(UploadLattesCVServiceBean.class.getCanonicalName());

	/** The DAO for Academic objects. */
	@EJB
	private AcademicDAO academicDAO;

	/** The DAO for Publication objects. */
	@EJB
	private PublicationDAO publicationDAO;

	/** The DAO for Venue objects. */
	@EJB
	private VenueDAO venueDAO;

	/** Calls Venue-Publication Matching */
	@Inject
	private Event<MatchPublicationsEvent> matchPublicationsEvent;

	/** TODO: document this field. */
	private PersistentObjectConverterFromId<Venue> venueConverter;

	/** @see br.ufes.informatica.marvin.research.application.UploadLattesCVService#getVenueConverter() */
	@Override
	public PersistentObjectConverterFromId<Venue> getVenueConverter() {
		if (venueConverter == null) venueConverter = new PersistentObjectConverterFromId<Venue>(venueDAO);
		return venueConverter;
	}

	/** @see br.ufes.informatica.marvin.research.application.UploadLattesCVService#findVenueByName(java.lang.String) */
	@Override
	public List<Venue> findVenueByName(String query) {
		return venueDAO.findByNameOrAcronym(query);
	}

	/** @see br.ufes.informatica.marvin.research.application.UploadLattesCVService#uploadLattesCV(java.io.InputStream) */
	@Override
	public PublicationInfo uploadLattesCV(InputStream inputStream) throws LattesIdNotRegisteredException, LattesParseException {
		// Parses the Lattes CV.
		LattesParser parser = new LattesParser(inputStream);
		parser.parse();

		// Retrieves some basic information.
		Long lattesId = parser.getLattesId();
		String researcherName = parser.getResearcherName();

		try {
			// Checks if the curriculum's ID matches an ID registered in the system.
			Academic owner = academicDAO.retrieveByLattesId(lattesId);
			logger.log(Level.INFO, "Found academic with Lattes ID {0}: {1} ({2}).", new Object[] { lattesId, owner.getName(), owner.getEmail() });

			// Adds the researcher and the current amount of publications associated with her.
			parser.setResearcher(owner);
			parser.setPreviousQuantity(publicationDAO.retrieveCountByAcademic(owner));
		}
		catch (PersistentObjectNotFoundException e) {
			// If there is no academic with the given Lattes ID, throw an exception from the domain.
			logger.log(Level.WARNING, "No academics found with Lattes ID {0} (name: {1}). Cannot assign production to anyone in the system.", new Object[] { lattesId, researcherName });
			throw new LattesIdNotRegisteredException(lattesId, researcherName);
		}
		catch (MultiplePersistentObjectsFoundException e) {
			// This is a bug. Log and throw a runtime exception.
			logger.log(Level.SEVERE, "Multiple academics found with the same Lattes ID: " + lattesId, e);
			throw new EJBException(e);
		}

		// Returns the set of publications extracted from the CV.
		return parser;
	}

	/**
	 * @see br.ufes.informatica.marvin.research.application.UploadLattesCVService#assignPublicationsToAcademic(java.util.Set,
	 *      br.ufes.informatica.marvin.core.domain.Academic)
	 */
	@Override
	public void assignPublicationsToAcademic(Set<Publication> publications, Academic owner) {
		// Delete the previous publications of the academic.
		List<Publication> previousPublications = publicationDAO.retrieveByAcademic(owner);
		for (Publication previous : previousPublications)
			publicationDAO.delete(previous);

		// Assign the new publications to the academic and save.
		for (Publication publication : publications) {
			publication.setOwner(owner);
			publicationDAO.save(publication);
		}

		// FIXME: the matches need to be confirmed by calling saveMatches(). Therefore, the publications should be detached
		// here. Update JButler, adding detach() method to BaseDAO.

		// Fires an event that triggers the matching between venues and publications.
		matchPublicationsEvent.fire(new MatchPublicationsEvent(owner));
	}

	/** @see br.ufes.informatica.marvin.research.application.UploadLattesCVService#saveMatches(java.util.Set) */
	@Override
	public void saveMatches(Set<Publication> publications) {
		// Saves all publications.
		for (Publication publication : publications) {
			publicationDAO.save(publication);
		}
	}
}
