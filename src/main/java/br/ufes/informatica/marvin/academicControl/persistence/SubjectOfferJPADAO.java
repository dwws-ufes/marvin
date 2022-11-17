package br.ufes.informatica.marvin.academicControl.persistence;

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
import br.ufes.informatica.marvin.academicControl.domain.Period;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer_;

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

	@Override
	public SubjectOffer retrieveLastSubjectOfferBySchoolSubject(Long schoolSubjectId)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		logger.log(Level.FINE, "Retrieving the subject offer whose schoolSubject id is \"{0}\"...", schoolSubjectId);

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SubjectOffer> cq = cb.createQuery(SubjectOffer.class);
		Root<SubjectOffer> root = cq.from(SubjectOffer.class);

		cq.select(root);
		cq.where(cb.equal(root.get(SubjectOffer_.schoolSubject), schoolSubjectId));

		List<SubjectOffer> result = entityManager.createQuery(cq).getResultList();

		logger.log(Level.INFO, "Retrieving subject offers by the id \"{0}\" returned {1} results",
				new Object[] { schoolSubjectId, result.size() });
		return result.size() == 0 ? null : result.get(result.size() - 1);
	}

	@Override
	public int getCountSubjectOfferByPeriod(Period period) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SubjectOffer> cq = cb.createQuery(SubjectOffer.class);
		Root<SubjectOffer> root = cq.from(SubjectOffer.class);

		cq.select(root);
		cq.where(cb.equal(root.get(SubjectOffer_.period), period));

		return entityManager.createQuery(cq).getResultList().size();
	}

}