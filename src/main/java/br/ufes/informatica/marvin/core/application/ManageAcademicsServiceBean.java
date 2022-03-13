package br.ufes.informatica.marvin.core.application;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudException;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.controller.PersistentObjectConverterFromId;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Ppg;
import br.ufes.informatica.marvin.core.domain.Role;
import br.ufes.informatica.marvin.core.persistence.AcademicDAO;
import br.ufes.informatica.marvin.core.persistence.RoleDAO;

/**
 * TODO: document this type.
 * 
 * FIXME: shouldn't we validate create to check if the e-mail is already in use?
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Stateless
@RolesAllowed("SysAdmin")
public class ManageAcademicsServiceBean extends CrudServiceBean<Academic> implements ManageAcademicsService {
	/** The unique identifier for a serializable class. */
	private static final long serialVersionUID = 1L;

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(ManageAcademicsServiceBean.class.getCanonicalName());

	/** TODO: document this field. */
	@EJB
	private AcademicDAO academicDAO;

	/** TODO: document this field. */
	@EJB
	private RoleDAO roleDAO;

	/** TODO: document this field. */
	@EJB
	private CoreInformation coreInformation;

	/** TODO: document this field. */
	@Inject
	private Event<MailEvent> mailEvent;

	/** TODO: document this field. */
	@Resource
	private SessionContext sessionContext;

	/** TODO: document this field. */
	private PersistentObjectConverterFromId<Role> roleConverter;

	/** TODO: document this field. */
	private PersistentObjectConverterFromId<Academic> academicConverter;

	@Override
	public BaseDAO<Academic> getDAO() {
		return academicDAO;
	}

	@Override
	protected Academic validate(Academic newEntity, Academic oldEntity) {
		// New academics must have their creation date and password code set.
		Date now = new Date(System.currentTimeMillis());
		if (oldEntity == null) {
			newEntity.setCreationDate(now);
			newEntity.setPasswordCode(UUID.randomUUID().toString());
		}

		// All academics have their last update date set when persisted.
		newEntity.setLastUpdateDate(now);
		return newEntity;
	}

	@Override
	public void validateDelete(Academic entity) throws CrudException {
		// Possibly throwing a CRUD Exception to indicate validation error.
		CrudException crudException = null;
		String email = entity.getEmail();
		String crudExceptionMessage = "The academic \"" + entity.getName() + "(" + email
				+ ")\" cannot be deleted due to validation errors.";

		// Validates business rules.
		// Rule 1: cannot delete an admin.
		try {
			Role adminRole = roleDAO.retrieveByName(Role.SYSADMIN_ROLE_NAME);
			if (entity.getRoles().contains(adminRole)) {
				logger.log(Level.INFO,
						"Deletion of academic \"{0}\" violates validation rule 1: acadmic has SysAdmin role",
						new Object[] { email });
				crudException = addGlobalValidationError(crudException, crudExceptionMessage,
						"manageAcademics.error.deleteAdmin", email);
			}
		} catch (MultiplePersistentObjectsFoundException | PersistentObjectNotFoundException e) {
			logger.log(Level.SEVERE,
					"Problem retrieving role " + Role.SYSADMIN_ROLE_NAME + " while validating an academic deletion!",
					e);
		}

		// If one of the rules was violated, throw the exception.
		if (crudException != null)
			throw crudException;
	}

	@Override
	public void create(Academic entity) {
		// Performs the method as inherited (create the academic).
		super.create(entity);
		try {
			// Retrieves the current user, i.e., the admin.
			Academic admin = academicDAO.retrieveByEmail(sessionContext.getCallerPrincipal().getName());

			// Creates the data model with the information needed to send an e-mail to the
			// new academic.
			Map<String, Object> dataModel = new HashMap<>();
			dataModel.put("config", coreInformation.getCurrentConfig());
			dataModel.put("admin", admin);
			dataModel.put("academic", entity);

			// Then, fire an e-mail event so the e-mail gets sent.
			mailEvent.fire(new MailEvent(entity.getEmail(), MailerTemplate.NEW_ACADEMIC_REGISTERED, dataModel));
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Could NOT send e-mail using template: " + MailerTemplate.NEW_ACADEMIC_REGISTERED,
					e);
		}
	}

	@Override
	public PersistentObjectConverterFromId<Role> getRoleConverter() {
		if (roleConverter == null)
			roleConverter = new PersistentObjectConverterFromId<Role>(roleDAO);
		return roleConverter;
	}

	@Override
	public PersistentObjectConverterFromId<Academic> getAcademicConverter() {
		if (academicConverter == null)
			academicConverter = new PersistentObjectConverterFromId<Academic>(academicDAO);
		return academicConverter;
	}

	@Override
	public List<Role> findRoleByName(String name) {
		return roleDAO.findByName(name);
	}

	public List<Academic> findByNameEmail(String search) {
		try {
			return this.academicDAO.retriveByNameEmail(search);
		} catch (PersistentObjectNotFoundException e) {
			return null;
		}
	}

	@Override
	public void savePpgAdminstrator(Academic academic, Role role, Ppg ppg) {

		if (academic == null || role == null || ppg == null) {
			throw new NullPointerException();
		}

		Set<Role> roles = academic.getRoles();
		for (Role interator : roles) {
			if (interator.getName().equals("Secretary") || interator.getName().equals("Coordinato")) {
				roles.remove(interator);
			}
		}
		roles.add(role);
		academic.setRoles(roles);
		academic.setPpg(ppg);
		academicDAO.save(academic);
	}

}
