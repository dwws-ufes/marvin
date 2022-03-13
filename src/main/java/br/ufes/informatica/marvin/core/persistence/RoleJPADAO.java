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
import br.ufes.informatica.marvin.core.domain.Role;
import br.ufes.informatica.marvin.core.domain.Role_;

/**
 * Stateless session bean implementing a DAO for objects of the Role domain
 * class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are
 * inherited from the superclass, whereas operations that are specific to the
 * managed domain class (if any is defined in the implementing DAO interface)
 * have to be implemented in this class.
 * 
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Stateless
public class RoleJPADAO extends BaseJPADAO<Role> implements RoleDAO {
	/** The unique identifier for a serializable class. */
	private static final long serialVersionUID = 1L;

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(RoleJPADAO.class.getCanonicalName());

	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public Role retrieveByName(String name)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		logger.log(Level.FINE, "Retrieving the role whose name is \"{0}\"...", name);

		// Constructs the query over the Academic class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Role> cq = cb.createQuery(Role.class);
		Root<Role> root = cq.from(Role.class);

		// Filters the query with the email.
		cq.where(cb.equal(root.get(Role_.name), name));
		Role result = executeSingleResultQuery(cq, name);
		logger.log(Level.INFO, "Retrieve role with the name \"{0}\" returned \"{1}\"", new Object[] { name, result });
		return result;
	}

	@Override
	public List<Role> findByName(String name) {
		logger.log(Level.FINE, "Finding roles whose name contain \"{0}\"...", name);

		// Constructs the query over the Academic class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Role> cq = cb.createQuery(Role.class);
		Root<Role> root = cq.from(Role.class);

		// Filters the query with the email.
		name = "%" + name + "%";
		cq.where(cb.like(root.get(Role_.name), name));
		List<Role> result = entityManager.createQuery(cq).getResultList();
		logger.log(Level.INFO, "Found {0} roles whose name contains \"{1}\".", new Object[] { result.size(), name });
		return result;
	}
}
