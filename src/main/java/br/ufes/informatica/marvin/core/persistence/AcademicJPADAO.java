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
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Academic_;
import br.ufes.informatica.marvin.core.domain.Role;
import br.ufes.informatica.marvin.people.domain.Person_;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Stateless
public class AcademicJPADAO extends BaseJPADAO<Academic> implements AcademicDAO {
	/** The unique identifier for a serializable class. */
	private static final long serialVersionUID = 1L;

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(AcademicJPADAO.class.getCanonicalName());

	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	protected List<Order> getOrderList(CriteriaBuilder cb, Root<Academic> root) {
		// Orders by name.
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(cb.asc(root.get(Person_.name)));
		return orderList;
	}

	@Override
	public Academic retrieveByEmail(String email)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		logger.log(Level.FINE, "Retrieving the academic whose e-mail is \"{0}\"...", email);

		// Constructs the query over the Academic class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Academic> cq = cb.createQuery(Academic.class);
		Root<Academic> root = cq.from(Academic.class);

		// Filters the query with the email.
		cq.where(cb.equal(root.get(Academic_.email), email));
		Academic result = executeSingleResultQuery(cq, email);
		logger.log(Level.INFO, "Retrieve academic by the email \"{0}\" returned \"{1}\"",
				new Object[] { email, result });
		return result;
	}

	@Override
	public Academic retrieveByLattesId(Long lattesId)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		logger.log(Level.FINE, "Retrieving the academic whose Lattes ID is \"{0}\"...", lattesId);

		// Constructs the query over the Academic class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Academic> cq = cb.createQuery(Academic.class);
		Root<Academic> root = cq.from(Academic.class);

		// Filters the query with the Lattes ID.
		cq.where(cb.equal(root.get(Academic_.lattesId), lattesId));
		Academic result = executeSingleResultQuery(cq, lattesId);
		logger.log(Level.INFO, "Retrieve academic by the Lattes ID \"{0}\" returned \"{1}\"",
				new Object[] { lattesId, result });
		return result;
	}

	@Override
	public List<Academic> retrieveResearchers() {
		logger.log(Level.FINE, "Retrieving all researchers in the system");

		// Constructs the query over the Academic class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Academic> cq = cb.createQuery(Academic.class);
		Root<Academic> root = cq.from(Academic.class);

		// Filters academics that have Lattes ID set.
		// FIXME: also check if the academic has either the professor or the student
		// role.
		cq.where(cb.isNotNull(root.get(Academic_.lattesId)));
		List<Academic> result = entityManager.createQuery(cq).getResultList();
		logger.log(Level.INFO, "Retrieving researchers returned {0} results", result.size());
		return result;
	}

	@Override
	public Academic retrieveByPasswordCode(String passwordCode)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		logger.log(Level.FINE, "Retrieving the academic whose password code is \"{0}\"...", passwordCode);

		// Constructs the query over the Academic class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Academic> cq = cb.createQuery(Academic.class);
		Root<Academic> root = cq.from(Academic.class);

		// Filters the query with the email.
		cq.where(cb.equal(root.get(Academic_.passwordCode), passwordCode));
		Academic result = executeSingleResultQuery(cq, passwordCode);
		logger.log(Level.INFO, "Retrieve academic by the password code \"{0}\" returned \"{1}\"",
				new Object[] { passwordCode, result });
		return result;
	}

	@Override
	public List<Academic> retrieveByRole(Role role)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		logger.log(Level.FINE, "Retrieving the academic whose role is \"{0}\"...", role);

		// Constructs the query over the Academic class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Academic> cq = cb.createQuery(Academic.class);
		Root<Academic> root = cq.from(Academic.class);

		// Filters the query with the role.
		cq.where(cb.isMember(role, root.get(Academic_.roles)));
		List<Academic> result = entityManager.createQuery(cq).getResultList();
		logger.log(Level.INFO, "Retrieving academics by the role \"{0}\" returned {1} results",
				new Object[] { role, result.size() });
		return result;
	}

}
