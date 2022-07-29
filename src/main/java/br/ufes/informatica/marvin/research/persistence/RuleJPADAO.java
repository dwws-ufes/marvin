package br.ufes.informatica.marvin.research.persistence;

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
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.research.domain.Rule;
import br.ufes.informatica.marvin.research.domain.Rule_;

@Stateless
public class RuleJPADAO extends BaseJPADAO<Rule> implements RuleDAO {
	private static final long serialVersionUID = 1L;

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(RuleJPADAO.class.getCanonicalName());

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public List<Rule> retrieveValidityRule() throws PersistentObjectNotFoundException {
		logger.log(Level.FINE, "Retrieving the validity rule ...");

		// Constructs the query over the Academic class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Rule> cq = cb.createQuery(Rule.class);
		Root<Rule> rule = cq.from(Rule.class);

		cq.where(cb.isNull(rule.get(Rule_.dtEnd)));

		List<Rule> result = entityManager.createQuery(cq).getResultList();
		logger.log(Level.INFO, "Retrieving the validity rule");
		return result;
	}
}
