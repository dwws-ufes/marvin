package br.ufes.informatica.marvin.core.application;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.core.application.exceptions.EmailAlreadyInUseException;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Role;
import br.ufes.informatica.marvin.core.persistence.AcademicDAO;
import br.ufes.informatica.marvin.core.persistence.RoleDAO;

/**
 * TODO: document this type.
 * 
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Stateless
@PermitAll
public class CreateAccountServiceBean implements CreateAccountService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(CreateAccountServiceBean.class.getCanonicalName());

	/** DAO for Academic objects. */
	@EJB
	private AcademicDAO academicDAO;

	/** DAO for Role objects. */
	@EJB
	private RoleDAO roleDAO;

	/** Service class that holds information on the system's current installation. */
	@EJB
	private CoreInformation coreInformation;

	/** TODO: document this field. */
	@Inject
	private Event<MailEvent> mailEvent;

	/**
	 * @see br.ufes.informatica.marvin.core.application.CreateAccountService#createAccount(br.ufes.informatica.marvin.core.domain.Academic)
	 */
	@Override
	public void createAccount(Academic academic) throws EmailAlreadyInUseException {
		// Checks if the e-mail chosen by the academic is already in use.
		try {
			academicDAO.retrieveByEmail(academic.getEmail());
			logger.log(Level.WARNING, "New academic violates unique e-mail rule: \"{0}\"", academic.getEmail());
			throw new EmailAlreadyInUseException(academic.getEmail());
		}
		catch (PersistentObjectNotFoundException e) {
			// This is the expected outcome. Just log that everything is OK.
			logger.log(Level.INFO, "New academic satisfies unique e-mail rule: \"{0}\"", academic.getEmail());
		}
		catch (MultiplePersistentObjectsFoundException e) {
			// This is a severe problem: the unique constraint has already been violated.
			logger.log(Level.SEVERE, "There are already multiple academics with the e-mail: \"{0}\"!", academic.getEmail());
			throw new EJBException(e);
		}
		
		// FIXME: should we also check CPF already in use? What is the CPF used for? How would one recover one's account if the CPF is already in use?

		// Sets automatic information for new academics (dates and password code).
		Date now = new Date(System.currentTimeMillis());
		academic.setCreationDate(now);
		academic.setLastUpdateDate(now);
		academic.setPasswordCode(UUID.randomUUID().toString());

		try {
			// Assigns the visitor role to the new academic.
			Role visitorRole = roleDAO.retrieveByName(Role.VISITOR_ROLE_NAME);
			academic.assignRole(visitorRole);
			
			// Saves the academic.
			academicDAO.save(academic);
		}
		catch (PersistentObjectNotFoundException | MultiplePersistentObjectsFoundException e) {
			logger.log(Level.SEVERE, "Could not retrieve the role: " + Role.VISITOR_ROLE_NAME, e);
			throw new EJBException(e);
		}

		try {
			// Creates the data model with the information needed to send an e-mail to the new academic.
			Map<String, Object> dataModel = new HashMap<>();
			dataModel.put("config", coreInformation.getCurrentConfig());
			dataModel.put("academic", academic);

			// Then, fire an e-mail event so the e-mail gets sent.
			mailEvent.fire(new MailEvent(academic.getEmail(), MailerTemplate.NEW_REGISTRATION, dataModel));
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "Could NOT send e-mail using template: " + MailerTemplate.NEW_REGISTRATION, e);
			// FIXME: throw a business exception to be caught by the controller, which should add a message saying the user should contact support.
		}
	}
}
