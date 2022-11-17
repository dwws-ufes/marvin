package br.ufes.informatica.marvin.academicControl.persistence;

import java.util.Date;
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
import br.ufes.informatica.marvin.academicControl.domain.Period;
import br.ufes.informatica.marvin.academicControl.domain.Period_;
import br.ufes.informatica.marvin.utils.MarvinFunctions;

@Stateless
public class PeriodJPADAO extends BaseJPADAO<Period> implements PeriodDAO {
	private static final long serialVersionUID = 1L;

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(PeriodJPADAO.class.getCanonicalName());

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public List<Period> retrievePeriods() {
		logger.log(Level.FINE, "Retrieving all Periods in the system");
		List<Period> result = this.retrieveAll();
		logger.log(Level.INFO, "Retrieving Periods returned {0} results", result.size());
		return result;
	}

	@Override
	public Period retrivePeriodByName(String name) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Period> cq = cb.createQuery(Period.class);
			Root<Period> root = cq.from(Period.class);

			cq.where(cb.equal(root.get(Period_.name), name));
			Period result = executeSingleResultQuery(cq, name);

			return result;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Period retriveActualPeriod() {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Period> cq = cb.createQuery(Period.class);
			Root<Period> root = cq.from(Period.class);

			Date sysdate = MarvinFunctions.sysdate();
			cq.where(cb.lessThanOrEqualTo(root.get(Period_.offerStartDate), sysdate));
			cq.where(cb.greaterThanOrEqualTo(root.get(Period_.offerFinalDate), sysdate));
			Period result = executeSingleResultQuery(cq, sysdate, sysdate);
			return result;
		} catch (Exception e) {
			List<Period> periods = retrievePeriods();
			return periods.size() > 0 ? periods.get(0) : null;
		}
	}
}