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
import br.ufes.informatica.marvin.core.domain.CourseAttendance;
import br.ufes.informatica.marvin.core.domain.CourseAttendance.Situation;
import br.ufes.informatica.marvin.core.domain.Role;
import br.ufes.informatica.marvin.core.persistence.AcademicDAO;
import br.ufes.informatica.marvin.core.persistence.AcademicRoleDAO;
import br.ufes.informatica.marvin.core.persistence.CourseAttendanceDAO;
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
public class ManageCourseAttendancesServiceBean extends CrudServiceBean<CourseAttendance> implements ManageCourseAttendancesService {
	/** TODO: document this field. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(ManageCourseAttendancesServiceBean.class.getCanonicalName());

	/** TODO: document this field. */
	@EJB
	private AcademicDAO academicDAO;

	/** TODO: document this field. */
	@EJB
	private CourseDAO courseDAO;

	/** TODO: document this field. */
	// FIXME: dependency from core to sae is forbidden.
	//@EJB
	//private AlumniDAO alumniDAO;

	/** TODO: document this field. */
	@EJB
	private RoleDAO roleDAO;

	/** TODO: document this field. */
	@EJB
	private AcademicRoleDAO academicRoleDAO;

	/** TODO: document this field. */
	@EJB
	private CourseAttendanceDAO courseAttendanceDAO;

	/** TODO: document this field. */
	@EJB
	private CoreInformation coreInformation;

	/** TODO: document this field. */
	@Resource
	private SessionContext sessionContext;

	// FIXME: dependency from core to sae is forbidden.
	//@Inject
	//private ManageAlumnisService manageAlumnisService;

	/** @see br.ufes.inf.nemo.jbutler.ejb.application.ListingService#getDAO() */
	@Override
	public BaseDAO<CourseAttendance> getDAO() {
		return courseAttendanceDAO;
	}

	/**
	 * @see br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean#create(br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObject)
	 */
	@Override
	public void create(CourseAttendance entity) {
		// Performs the method as inherited (create the academic).
		entity.setStartDate(new Date(System.currentTimeMillis()));
		try {
			// Removing Student Role and assign Alumni Role
			AcademicRole ar = academicRoleDAO.retrieveByName(AcademicRole.ENROLLED_STUDENT_ROLE_NAME);
			entity.getAcademic().assignAcademicRole(ar);
			entity.setSituation(Situation.ACTIVE);
			academicDAO.save(entity.getAcademic());
		}
		catch (PersistentObjectNotFoundException | MultiplePersistentObjectsFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.create(entity);
		// Retrieves the current user, i.e., the admin.*/
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
		Map<String, Course> coursesMap = new HashMap<String, Course>();
		List<Course> courses = courseDAO.retrieveAllSortedByName();
		for (Course course : courses)
			coursesMap.put(course.getName(), course);
		return coursesMap;
	}

	/** Returns a map containing all academics with some course attendance active. Load the combobox in the front-end. */
	@Override
	public Map<String, Academic> retrieveAcademics(boolean isCurrentStudent) {
		Map<String, Academic> students = new HashMap<String, Academic>();
		List<Academic> academics = retrieveAcademicbyRole(Role.STUDENT_ROLE_NAME);
		if (!isCurrentStudent) {
			AcademicRole ar;
			try {
				ar = academicRoleDAO.retrieveByName(AcademicRole.ENROLLED_STUDENT_ROLE_NAME);
				for (Academic academic : academics)
					if (!academic.getAcademicRoles().contains(ar)) students.put(academic.getName(), academic);
			}
			catch (PersistentObjectNotFoundException | MultiplePersistentObjectsFoundException e) {
				e.printStackTrace();
			}
		}
		else for (Academic academic : academics)
			students.put(academic.getName(), academic);
		return students;
	}

	/** Disable a Course Attendace (adding the end date to course attendance), remove the enrolled student academic role of the academic and create a alumni of this student */
	@Override
	public void disable(CourseAttendance entity) {
		entity.setEndDate(new Date(System.currentTimeMillis()));
		try {
			// Removing Student Role from academic and assiging Alumni Role
			AcademicRole ar = academicRoleDAO.retrieveByName(AcademicRole.ENROLLED_STUDENT_ROLE_NAME);
			entity.getAcademic().unassignAcademicRole(ar);
			ar = academicRoleDAO.retrieveByName(AcademicRole.ALUMNI_ROLE_NAME);
			entity.getAcademic().assignAcademicRole(ar);
			academicDAO.save(entity.getAcademic());

		}
		catch (PersistentObjectNotFoundException | MultiplePersistentObjectsFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.update(entity);
		
		// FIXME: dependency from core to sae is forbidden.
		// Create the Alumni
		//Alumni alumni = new Alumni();
		//alumni.setCourseAttendance(entity);
		//alumni.setCreationDate(entity.getEndDate());
		//manageAlumnisService.create(alumni);
	}
}
