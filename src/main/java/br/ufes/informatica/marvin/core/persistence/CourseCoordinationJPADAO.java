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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Course;
import br.ufes.informatica.marvin.core.domain.CourseCoordination;
import br.ufes.informatica.marvin.core.domain.CourseCoordination_;

/**
 * Stateless session bean implementing a DAO for objects of the Course Coordination domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) have
 * to be implemented in this class.
 * 
 * @author Gabriel Martins Miranda (garielmartinsmiranda@gmail.com)
 * @version 1.0
 */
@Stateless
public class CourseCoordinationJPADAO extends BaseJPADAO<CourseCoordination> implements CourseCoordinationDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(CourseCoordinationJPADAO.class.getCanonicalName());

	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;

	/** @see br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/** Check if the academic was coordinator of some course */
	@Override
	public boolean academicWasCoordinator(Academic academic) {
		logger.log(Level.FINE, "Checking if the academic was coordinator of some course");

		// Constructs the query over the Course Coordination class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<CourseCoordination> cq = cb.createQuery(CourseCoordination.class);
		Root<CourseCoordination> root = cq.from(CourseCoordination.class);

		// Filters the query with the academic.
		cq.where(cb.equal(root.get(CourseCoordination_.academic), academic));
		List<CourseCoordination> result = entityManager.createQuery(cq).getResultList();

		if (!result.isEmpty()) return true;
		return false;
	}

	/** Check if the course already been coordinated  */
	@Override
	public boolean courseHasCoordinations(Course course) {
		logger.log(Level.FINE, "Checking if the course already been coordinated");

		// Constructs the query over the Course Coordination class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<CourseCoordination> cq = cb.createQuery(CourseCoordination.class);
		Root<CourseCoordination> root = cq.from(CourseCoordination.class);

		// Filters the query with the academic.
		cq.where(cb.equal(root.get(CourseCoordination_.course), course));
		List<CourseCoordination> result = entityManager.createQuery(cq).getResultList();

		if (!result.isEmpty()) return true;
		return false;
	}

	/** Check if the course has active coordinations */
	@Override
	public boolean courseHasActiveCoordinations(Course course) {
		logger.log(Level.FINE, "Checking if the course has active coordinations");
		// Constructs the query over the Course Coordination class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<CourseCoordination> cq = cb.createQuery(CourseCoordination.class);
		Root<CourseCoordination> root = cq.from(CourseCoordination.class);

		List<Predicate> predicates = new ArrayList<>();
		predicates.add(cb.equal(root.get(CourseCoordination_.course), course));
		predicates.add(cb.isNull(root.get(CourseCoordination_.endDate)));
		// Filters the query with the academic.

		cq.select(root).where(predicates.toArray(new Predicate[] {}));
		List<CourseCoordination> result = entityManager.createQuery(cq).getResultList();

		if (!result.isEmpty()) return true;
		return false;
	}
}
