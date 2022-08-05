package br.ufes.informatica.marvin.academicControl.persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.informatica.marvin.academicControl.domain.Period;

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
}