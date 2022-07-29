package br.ufes.informatica.marvin.research.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.research.domain.Qualis;
import br.ufes.informatica.marvin.research.domain.QualisValidity;
import br.ufes.informatica.marvin.research.domain.Qualis_;

@Stateless
public class QualisJPADAO extends BaseJPADAO<Qualis> implements QualisDAO {
	private static final long serialVersionUID = 1L;

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(QualisJPADAO.class.getCanonicalName());

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public List<Qualis> retrieveByQualisValidityId(Long id) throws PersistentObjectNotFoundException {
		logger.log(Level.FINE, "Retrieving qualis who are part of the QualisValidity \"{0}\"...", id);

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Qualis> cq = cb.createQuery(Qualis.class);
		Root<Qualis> qualis = cq.from(Qualis.class);

		cq.select(qualis);
		cq.where(cb.equal(qualis.get(Qualis_.qualisValidity), id));

		List<Qualis> result = entityManager.createQuery(cq).getResultList();

		return result;

	}

	@Override
	public Qualis retriveByNameQualisValidity(String qualisName, Date dtStart, Date dtEnd, Long idPPG)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		logger.log(Level.FINE,
				"Retrieving the qualis whose name is \"{0}\", validity date is between \"{1}\" and \"{2}\", ppg_id is \"{3}\"...",
				new Object[] { qualisName, dtStart, dtEnd, idPPG });

		// Constructs the query over the Academic class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Qualis> cq = cb.createQuery(Qualis.class);

		Root<Qualis> qualis = cq.from(Qualis.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		Join<Qualis, QualisValidity> join = qualis.join("qualisValidity");

		Path<String> name = qualis.get("name");
		Path<Long> ppg = join.get("ppg");
		Path<Date> qualisDtEnd = join.get("dtEnd");
		Path<Date> qualisDtStart = join.get("dtStart");

		Predicate predQualisName = cb.equal(name, qualisName);
		Predicate predQualisPPG = cb.equal(ppg, idPPG);
		Predicate predQualisValidityStart = cb.greaterThanOrEqualTo(qualisDtStart, dtStart);
		Predicate predQualisValidityEnd = cb.lessThanOrEqualTo(qualisDtEnd, dtEnd);

		predicateList.add(predQualisName);
		predicateList.add(predQualisPPG);
		predicateList.add(predQualisValidityStart);
		predicateList.add(predQualisValidityEnd);

		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		cq.where(predicates);
		// Filters the query with the academic_id.
		TypedQuery<Qualis> jpaQuery = entityManager.createQuery(cq);
		Qualis result = jpaQuery.getSingleResult();

		logger.log(Level.INFO, "Retrieve qualis with qualis_id \"{0}\"", result.getId());
		return result;
	}

	@Override
	public List<Qualis> retrieveByQualisValidity(Long idPPG) throws PersistentObjectNotFoundException {
		logger.log(Level.FINE, "Retrieving the validity qualis");

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Qualis> cq = cb.createQuery(Qualis.class);
		Root<Qualis> qualis = cq.from(Qualis.class);

		Join<Qualis, QualisValidity> join = qualis.join("qualisValidity");

		List<Predicate> predicateList = new ArrayList<Predicate>();

		Path<Long> ppg = join.get("ppg");
		Path<Date> qualisDtEnd = join.get("dtEnd");

		Predicate predQualisPPG = cb.equal(ppg, idPPG);
		Predicate predQualisValidityEnd = cb.isNull(qualisDtEnd);

		predicateList.add(predQualisPPG);
		predicateList.add(predQualisValidityEnd);

		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		cq.where(predicates);

		List<Qualis> result = entityManager.createQuery(cq).getResultList();

		return result;
	}
}
