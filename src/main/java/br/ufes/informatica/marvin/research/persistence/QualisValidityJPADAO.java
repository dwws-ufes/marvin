package br.ufes.informatica.marvin.research.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.research.domain.QualisValidity;

@Stateless
public class QualisValidityJPADAO extends BaseJPADAO<QualisValidity> implements QualisValidityDAO {
	private static final long serialVersionUID = 1L;

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(QualisValidityJPADAO.class.getCanonicalName());

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public QualisValidity retriveByDates(Date dtStart, Date dtEnd)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException, NoResultException {
		logger.log(Level.FINE, "Retrieving the QualisValidity whose dt_start is \"{0}\" and dtEnd is \"{1}\"...",
				new Object[] { dtStart, dtEnd });

		// Constructs the query over the QualisValidity class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<QualisValidity> cq = cb.createQuery(QualisValidity.class);
		Root<QualisValidity> root = cq.from(QualisValidity.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		Path<Date> qualisDtStart = root.get("dtStart");
		Path<Date> qualisDtEnd = root.get("dtEnd");

		Predicate predQualisValidityStart = cb.equal(qualisDtStart, dtStart);
		Predicate predQualisValidityEnd = dtEnd == null ? cb.isNull(qualisDtEnd) : cb.equal(qualisDtEnd, dtEnd);

		predicateList.add(predQualisValidityStart);
		predicateList.add(predQualisValidityEnd);

		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);

		// Filters the query with the dtStart and dtEnd.
		cq.where(predicates);

		QualisValidity result = entityManager.createQuery(cq).getSingleResult();

		logger.log(Level.INFO, "Retrieve QualisValidity by the dtStart \"{0}\" and dtEnd \"{1}\" returned \"{2}\"",
				new Object[] { dtStart, dtEnd, result });
		return result;
	}
}