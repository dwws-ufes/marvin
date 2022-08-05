package br.ufes.informatica.marvin.academicControl.persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;

@Stateless
public class SubjectOfferJPADAO extends BaseJPADAO<SubjectOffer> implements SubjectOfferDAO {
	private static final long serialVersionUID = 1L;

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(SubjectOfferJPADAO.class.getCanonicalName());

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public List<SubjectOffer> retrieveSubjectsOffer() {
		logger.log(Level.FINE, "Retrieving all Periods in the system");
		List<SubjectOffer> result = this.retrieveAll();
		logger.log(Level.INFO, "Retrieving Periods returned {0} results", result.size());
		return result;
	}

	@Override
	public SubjectOffer retrieveSubjectOfferById(Long id) {
		return this.retrieveById(id);
	}

}