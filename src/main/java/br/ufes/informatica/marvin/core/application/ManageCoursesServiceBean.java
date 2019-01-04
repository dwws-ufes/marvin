package br.ufes.informatica.marvin.core.application;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudException;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.core.domain.Course;
import br.ufes.informatica.marvin.core.persistence.AcademicDAO;
import br.ufes.informatica.marvin.core.persistence.CourseAttendanceDAO;
import br.ufes.informatica.marvin.core.persistence.CourseCoordinationDAO;
import br.ufes.informatica.marvin.core.persistence.CourseDAO;

/**
 * TODO: document this type.
 *
 * @author Gabriel Martins Miranda (gabrielmartinsmiranda@gmail.com)
 * @version 1.0
 */
@Stateless
@RolesAllowed("SysAdmin")
public class ManageCoursesServiceBean extends CrudServiceBean<Course> implements ManageCoursesService {
	/** TODO: document this field. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(ManageCoursesServiceBean.class.getCanonicalName());

	/** TODO: document this field. */
	@EJB
	private AcademicDAO academicDAO;

	/** TODO: document this field. */
	@EJB
	private CourseDAO courseDAO;

	/** TODO: document this field. */
	@EJB
	private CourseCoordinationDAO courseCoordinationDAO;
	
	/** TODO: document this field. */
	@EJB
	private CourseAttendanceDAO courseAttendanceDAO;

	/** TODO: document this field. */
	@EJB
	private CoreInformation coreInformation;

	/** TODO: document this field. */
	@Resource
	private SessionContext sessionContext;

	/** @see br.ufes.inf.nemo.jbutler.ejb.application.ListingService#getDAO() */
	@Override
	public BaseDAO<Course> getDAO() {
		return courseDAO;
	}

	/**
	 * @see br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean#validate(br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObject,
	 *      br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObject)
	 */
	@Override
	protected Course validate(Course newEntity, Course oldEntity) {
		// New courses must have their creation date and password code set.
		Date now = new Date(System.currentTimeMillis());
		if (oldEntity == null) {
			newEntity.setCreationDate(now);
		}

		// All courses have their last update date set when persisted.
		newEntity.setLastUpdateDate(now);
		return newEntity;
	}

	/**
	 * @see br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean#validateDelete(br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObject)
	 */
	@Override
	public void validateDelete(Course entity) throws CrudException {
		// Possibly throwing a CRUD Exception to indicate validation error.
		CrudException crudException = null;
		String crudExceptionMessage = "The course \"" + entity.getName() + " cannot be updated due to validation errors.";

		// Validates business rules.
		// Rule 1: cannot delete a course with course coordinations.
		if (courseCoordinationDAO.courseHasCoordinations(entity)) {
			logger.log(Level.INFO, "Deletion of course \"{0}\" violates validation rule 1: cannot delete a course with coordination", new Object[] { entity.getName() });
			crudException = addGlobalValidationError(crudException, crudExceptionMessage, "manageCourses.error.deleteCourseWithCoordination", entity.getName());
		}

		// Rule 2: cannot delete a course with course attendances.
		if(courseAttendanceDAO.courseInCourseAttendance(entity))
		{
			logger.log(Level.INFO, "Deletion of course \"{0}\" violates validation rule 2: cannot delete a course with course attendances", new Object[] { entity.getName() });
			crudException = addGlobalValidationError(crudException, crudExceptionMessage, "manageCourses.error.deleteCourseWithAttendance", entity.getName());
		}

		// If one of the rules was violated, throw the exception.
		if (crudException != null) throw crudException;
	}
}
