package br.ufes.informatica.marvin.core.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Course;
import br.ufes.informatica.marvin.core.domain.CourseAttendance;
import br.ufes.informatica.marvin.core.domain.CourseAttendance_;

/**
 * Stateless session bean implementing a DAO for objects of the Course Attendance domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) have
 * to be implemented in this class.
 * 
 * @author Gabriel Martins Miranda (garielmartinsmiranda@gmail.com)
 * @version 1.0
 */
@Stateless
public class CourseAttendanceJPADAO extends BaseJPADAO<CourseAttendance> implements CourseAttendanceDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(AcademicJPADAO.class.getCanonicalName());

	/** @see br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/** Retrieve the courseAttendances of a academic. */
	@Override
	public List<CourseAttendance> retriveCourseAttendances(Academic academic) {
		logger.log(Level.FINE, "Retrieving the courseAttendances of a academic");
		// Constructs the query over the Course Attendance class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<CourseAttendance> cq = cb.createQuery(CourseAttendance.class);
		Root<CourseAttendance> root = cq.from(CourseAttendance.class);

		// Filters the query with the academic.
		cq.where(cb.equal(root.get(CourseAttendance_.academic), academic));
		List<CourseAttendance> result = entityManager.createQuery(cq).getResultList();
		return result;
	}

	/** Retrieve the courses in a course attendance, searching by an academic. */
	@Override
	public List<Course> retriveCoursesByAcademic(Academic academic) {
		logger.log(Level.FINE, "Retrieving the courses in a course attendance, searching by an academic named \"{0}\"", new Object[] { academic.getName() });
		// Constructs the query over the Course Attendance class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<CourseAttendance> cq = cb.createQuery(CourseAttendance.class);
		Root<CourseAttendance> root = cq.from(CourseAttendance.class);

		// Filters the query with the academic.
		cq.where(cb.equal(root.get(CourseAttendance_.academic), academic));
		List<CourseAttendance> result = entityManager.createQuery(cq).getResultList();

		List<Course> courses = new ArrayList<Course>();
		for (CourseAttendance courseAttendance : result) {
			courses.add(courseAttendance.getCourse());
		}
		return courses;
	}

	/** Check if the course has some course attendance associated */
	@Override
	public boolean courseInCourseAttendance(Course course) {
		logger.log(Level.FINE, "Checking if the course has some course attendance associated");
		// Constructs the query over the Course Attendance class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<CourseAttendance> cq = cb.createQuery(CourseAttendance.class);
		Root<CourseAttendance> root = cq.from(CourseAttendance.class);
		// Filters the query with the course.
		cq.where(cb.equal(root.get(CourseAttendance_.course), course));
		List<CourseAttendance> result = entityManager.createQuery(cq).getResultList();

		if(result.isEmpty()) return false;	
		return true;
	}
}
