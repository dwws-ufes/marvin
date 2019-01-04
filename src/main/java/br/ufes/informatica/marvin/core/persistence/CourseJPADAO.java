package br.ufes.informatica.marvin.core.persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.informatica.marvin.core.domain.Course;

/**
 * Stateless session bean implementing a DAO for objects of the Course domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) have
 * to be implemented in this class.
 * 
 * @author Gabriel Martins Miranda (garielmartinsmiranda@gmail.com)
 * @version 1.0
 */
@Stateless
public class CourseJPADAO extends BaseJPADAO<Course> implements CourseDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(CourseJPADAO.class.getCanonicalName());

	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;

	/** @see br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/** Retrieve all course sorted by name.*/
	@Override
	public List<Course> retrieveAllSortedByName() {
		logger.log(Level.FINE, "Retrieving the courses sorted by name");
		// FIXME: use Criteria API and typed query.
		Query query = entityManager.createQuery("SELECT c FROM Course c ORDER BY c.name");
		return (List<Course>) query.getResultList();
	}
}
