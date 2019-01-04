package br.ufes.informatica.marvin.core.persistence;

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
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.core.domain.AcademicRole;
import br.ufes.informatica.marvin.core.domain.AcademicRole_;

/**
 * Stateless session bean implementing a DAO for objects of the Academic Role domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) have
 * to be implemented in this class.
 * 
 * @author Gabriel Martins Miranda (garielmartinsmiranda@gmail.com)
 * @version 1.0
 */
@Stateless
public class AcademicRoleJPADAO extends BaseJPADAO<AcademicRole> implements AcademicRoleDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(AcademicRoleJPADAO.class.getCanonicalName());

	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;

	/** @see br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/** @see br.ufes.inf.nemo.marvin.core.persistence.RoleDAO#retrieveByName(java.lang.String) */
	@Override
	public AcademicRole retrieveByName(String name) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		logger.log(Level.FINE, "Retrieving the academic role whose name is \"{0}\"...", name);

		// Constructs the query over the Academic class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AcademicRole> cq = cb.createQuery(AcademicRole.class);
		Root<AcademicRole> root = cq.from(AcademicRole.class);

		// Filters the query with the name.
		cq.where(cb.equal(root.get(AcademicRole_.name), name));
		AcademicRole result = executeSingleResultQuery(cq, name);
		logger.log(Level.INFO, "Retrieve academic role with the name \"{0}\" returned \"{1}\"", new Object[] { name, result });
		return result;
	}

	/** @see br.ufes.inf.nemo.marvin.core.persistence.AcademicRoleDAO#findByName(java.lang.String) */
	@Override
	public List<AcademicRole> findByName(String name) {
		logger.log(Level.FINE, "Finding academic roles whose name contain \"{0}\"...", name);

		// Constructs the query over the Academic class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AcademicRole> cq = cb.createQuery(AcademicRole.class);
		Root<AcademicRole> root = cq.from(AcademicRole.class);

		// Filters the query with the email.
		name = "%" + name + "%";
		cq.where(cb.like(root.get(AcademicRole_.name), name));
		List<AcademicRole> result = entityManager.createQuery(cq).getResultList();
		logger.log(Level.INFO, "Found {0} academic roles whose name contains \"{1}\".", new Object[] { result.size(), name });
		return result;
	}
}
