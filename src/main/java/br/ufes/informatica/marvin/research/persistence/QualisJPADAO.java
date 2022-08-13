package br.ufes.informatica.marvin.research.persistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.research.domain.Publication;
import br.ufes.informatica.marvin.research.domain.Qualis;
import br.ufes.informatica.marvin.research.domain.QualisValidity;
import br.ufes.informatica.marvin.research.domain.Qualis_;
import br.ufes.informatica.marvin.research.domain.Venue;

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
		logger.log(Level.INFO,
				"Retrieving the qualis whose name is \"{0}\", validity date is between \"{1}\" and \"{2}\", ppg_id is \"{3}\"...",
				new Object[] { qualisName, dtStart, dtEnd, idPPG });

		// Constructs the query over the Academic class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Qualis> cq = cb.createQuery(Qualis.class);

		Root<Qualis> qualis = cq.from(Qualis.class);

		Join<Qualis, QualisValidity> join = qualis.join("qualisValidity");

		List<Predicate> predicateList = new ArrayList<Predicate>();

		Path<String> name = qualis.get("name");
		Path<Long> ppg = join.get("ppg");
		Path<Date> qualisDtEnd = join.get("dtEnd");
		Path<Date> qualisDtStart = join.get("dtStart");

		Predicate predQualisName = cb.equal(name, qualisName);
		Predicate predQualisPPG = cb.equal(ppg, idPPG);
		Predicate predQualisValidityStart = cb.greaterThanOrEqualTo(qualisDtStart, dtStart);
		Predicate predQualisValidityEnd = dtEnd != null ? cb.lessThanOrEqualTo(qualisDtEnd, dtEnd)
				: cb.isNull(qualisDtEnd);

		predicateList.add(predQualisName);
		predicateList.add(predQualisPPG);
		predicateList.add(predQualisValidityStart);
		predicateList.add(predQualisValidityEnd);

		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		cq.where(predicates);
		// Filters the query with the academic_id.

		Qualis result = entityManager.createQuery(cq).getSingleResult();

		logger.log(Level.INFO, "Retrieve qualis with qualis_id \"{0}\"", result.getId());
		return result;
	}

	@Override
	public List<Qualis> retrieveByQualisValidity(Long idPPG) throws PersistentObjectNotFoundException {
		logger.log(Level.INFO, "Retrieving the validity qualis");

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

	@Override
	public List<Tuple> retriveQualisByAcademicPublic(Long idAcademic, int year)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		logger.log(Level.FINE, "Retrieving qualis from academic_id \"{0}\" and year \"{0}\"...",
				new Object[] { idAcademic, year });

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
		Root<Publication> publication = cq.from(Publication.class);
		Join<Publication, Venue> venue = publication.join("venue");
		Join<Venue, Qualis> qualis = venue.join("qualis");

		List<Predicate> predicateList = new ArrayList<Predicate>();

		Path<Long> owner = publication.get("owner");
		Path<Integer> publicationYear = publication.get("year");

		Predicate predOwner = cb.equal(owner, idAcademic);
		Predicate predYear = cb.greaterThanOrEqualTo(publicationYear, year);

		predicateList.add(predOwner);
		predicateList.add(predYear);

		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);

		cq.where(predicates);

		cq.select(cb.tuple(venue, qualis));

		List<Tuple> result = entityManager.createQuery(cq).getResultList();

		return result;
	}

	@Override
	public Qualis retriveByNameValidity(String name, QualisValidity qualisValidity)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {

		SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
		Long idValidity = qualisValidity.getId();
		String dtStart = ft.format(qualisValidity.getDtStart());
		String dtEnd = qualisValidity.getDtEnd() == null ? "null" : ft.format(qualisValidity.getDtEnd());

		logger.log(Level.INFO, "Retrieving qualis \"{0}\" from validity \"{1}\" to \"{2}\"...",
				new Object[] { name, dtStart, dtEnd });

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Qualis> cq = cb.createQuery(Qualis.class);
		Root<Qualis> qualis = cq.from(Qualis.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		Predicate predName = cb.equal(qualis.get(Qualis_.name), name);
		Predicate predValidity = cb.equal(qualis.get(Qualis_.qualisValidity), idValidity);

		predicateList.add(predName);
		predicateList.add(predValidity);

		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);

		cq.where(predicates);

		Qualis result = entityManager.createQuery(cq).getSingleResult();

		return result;
	}

	@Override
	public List<Qualis> retriveByValidity(Date dtStart, Date dtEnd, Long idPPG)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		logger.log(Level.INFO, "Retrieving qualis by date validity");

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Qualis> cq = cb.createQuery(Qualis.class);
		Root<Qualis> qualis = cq.from(Qualis.class);

		Join<Qualis, QualisValidity> join = qualis.join("qualisValidity");

		List<Predicate> predicateList = new ArrayList<Predicate>();

		Path<Long> ppg = join.get("ppg");
		Path<Date> qualisDtStart = join.get("dtStart");
		Path<Date> qualisDtEnd = join.get("dtEnd");

		Predicate predQualisPPG = cb.equal(ppg, idPPG);
		Predicate predQualisValidityStart = cb.greaterThanOrEqualTo(qualisDtStart, dtStart);
		Predicate predQualisValidityEnd = dtEnd != null ? cb.lessThanOrEqualTo(qualisDtEnd, dtEnd)
				: cb.isNull(qualisDtEnd);

		predicateList.add(predQualisPPG);
		predicateList.add(predQualisValidityStart);
		predicateList.add(predQualisValidityEnd);

		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		cq.where(predicates);

		List<Qualis> result = entityManager.createQuery(cq).getResultList();

		return result;
	}
}