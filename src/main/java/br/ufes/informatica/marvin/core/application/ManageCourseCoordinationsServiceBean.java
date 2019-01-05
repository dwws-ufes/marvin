package br.ufes.informatica.marvin.core.application;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.AcademicRole;
import br.ufes.informatica.marvin.core.domain.Course;
import br.ufes.informatica.marvin.core.domain.CourseCoordination;
import br.ufes.informatica.marvin.core.domain.Role;
import br.ufes.informatica.marvin.core.persistence.AcademicDAO;
import br.ufes.informatica.marvin.core.persistence.AcademicRoleDAO;
import br.ufes.informatica.marvin.core.persistence.CourseCoordinationDAO;
import br.ufes.informatica.marvin.core.persistence.CourseDAO;
import br.ufes.informatica.marvin.core.persistence.RoleDAO;

/**
 * TODO: document this type.
 *
 * @author Gabriel Martins Miranda (gabrielmartinsmiranda@gmail.com)
 * @version 1.0
 */
@Stateless
@RolesAllowed("SysAdmin")
public class ManageCourseCoordinationsServiceBean extends CrudServiceBean<CourseCoordination> implements ManageCourseCoordinationsService {
	/** TODO: document this field. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(ManageCourseCoordinationsServiceBean.class.getCanonicalName());

	/** TODO: document this field. */
	@EJB
	private AcademicDAO academicDAO;

	/** TODO: document this field. */
	@EJB
	private CourseDAO courseDAO;

	/** TODO: document this field. */
	@EJB
	private RoleDAO roleDAO;

	/** TODO: document this field. */
	@EJB
	private AcademicRoleDAO academicRoleDAO;

	/** TODO: document this field. */
	@EJB
	private CourseCoordinationDAO courseCoordinationDAO;

	/** TODO: document this field. */
	@EJB
	private CoreInformation coreInformation;

	/** TODO: document this field. */
	@Resource
	private SessionContext sessionContext;

	/** @see br.ufes.inf.nemo.jbutler.ejb.application.ListingService#getDAO() */
	@Override
	public BaseDAO<CourseCoordination> getDAO() {
		return courseCoordinationDAO;
	}

	/**
	 * @see br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean#create(br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObject)
	 */
	@Override
	public void create(CourseCoordination entity) {
		// Performs the method as inherited (create the academic).
		entity.setStartDate(new Date(System.currentTimeMillis()));
		try {
			AcademicRole ar = academicRoleDAO.retrieveByName(AcademicRole.COURSECOORDINATOR_ROLE_NAME);
			entity.getAcademic().assignAcademicRole(ar);
			academicDAO.save(entity.getAcademic());
		}
		catch (PersistentObjectNotFoundException | MultiplePersistentObjectsFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.create(entity);
		// Retrieves the current user, i.e., the admin.
	}

	/** @see br.ufes.inf.nemo.marvin.core.application.ManageAcademicsService#findRoleByName(java.lang.String) */
	@Override
	public List<Role> findRoleByName(String name) {
		return roleDAO.findByName(name);
	}

	/** Retrieve the all Academic with a specific role */
	@Override
	public List<Academic> retrieveAcademicbyRole(String roleName) {
		try {
			List<Role> roles = findRoleByName(roleName);
			if (roles.isEmpty()) {
				logger.log(Level.SEVERE, "No role found!");
				return null;
			}
			else {
				return academicDAO.retrieveByRole(roles.get(0));
			}
		}
		catch (PersistentObjectNotFoundException | MultiplePersistentObjectsFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/** Returns a map containing all courses that have a coordinator. Load the combobox in the front-end. */
	@Override
	public Map<String, Course> retrieveCourses(boolean hasCoordinator) {
		Map<String, Course> coursesWithoutCoordination = new HashMap<String, Course>();
		List<Course> courses = courseDAO.retrieveAll();
		if (!hasCoordinator) {
			for (Course course : courses)
				if (!courseCoordinationDAO.courseHasActiveCoordinations(course)) coursesWithoutCoordination.put(course.getName(), course);
		}
		else for (Course course : courses)
			coursesWithoutCoordination.put(course.getName(), course);
		return coursesWithoutCoordination;
	}

	/** Retrieve the all Academic with or without the coordinator academic role */
	@Override
	public Map<String, Academic> retrieveAcademics(boolean isCoordinator) {
		Map<String, Academic> courseCoordinators = new HashMap<String, Academic>();
		List<Academic> academics = retrieveAcademicbyRole("Professor");
		if (!isCoordinator) {
			AcademicRole ar;
			try {
				ar = academicRoleDAO.retrieveByName(AcademicRole.COURSECOORDINATOR_ROLE_NAME);
				for (Academic academic : academics)
					if (!academic.getAcademicRoles().contains(ar)) courseCoordinators.put(academic.getName(), academic);
			}
			catch (PersistentObjectNotFoundException | MultiplePersistentObjectsFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else for (Academic academic : academics)
			courseCoordinators.put(academic.getName(), academic);
		return courseCoordinators;
	}

	/** Disable a Course Coordination (adding the end date to course coordination) and remove the coordinator academic role of the course coordinator */
	@Override
	public void disable(CourseCoordination entity) {
		entity.setEndDate(new Date(System.currentTimeMillis()));
		try {
			AcademicRole ar = academicRoleDAO.retrieveByName(AcademicRole.COURSECOORDINATOR_ROLE_NAME);
			entity.getAcademic().unassignAcademicRole(ar);
			academicDAO.save(entity.getAcademic());
		}
		catch (PersistentObjectNotFoundException | MultiplePersistentObjectsFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.update(entity);
	}
}
