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
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Academic_;
import br.ufes.informatica.marvin.core.domain.Occupation;
import br.ufes.informatica.marvin.core.domain.Occupation_;

@Stateless
public class OccupationJPADAO extends BaseJPADAO<Occupation> implements OccupationDAO {
	private static final long serialVersionUID = 1L;

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(OccupationJPADAO.class.getCanonicalName());

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	public List<Academic> retriveByNameEmail(String search) throws PersistentObjectNotFoundException {

		logger.log(Level.FINE, "Retrieving the academic whose e-mail or name is \"{0}\"...", search);

		// Constructs the query over the Academic class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Academic> cq = cb.createQuery(Academic.class);
		Root<Academic> root = cq.from(Academic.class);

		// Filters the query with the email.
		cq.where(cb.or(cb.like(root.get(Academic_.email), "%" + search + "%"),
				cb.like(root.get(Academic_.name), "%" + search + "%")));
		List<Academic> result = entityManager.createQuery(cq).getResultList();
		logger.log(Level.INFO, "Retrieve academic by the e-mail or name \"{0}\" returned \"{1}\"",
				new Object[] { search, result });
		return result;
	}

	@Override
	public Occupation retriveByAcademic(Long idAcademic)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		logger.log(Level.FINE, "Retrieving the occupation whose academic_id is \"{0}\"...", idAcademic);

		// Constructs the query over the Academic class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Occupation> cq = cb.createQuery(Occupation.class);
		Root<Occupation> root = cq.from(Occupation.class);

		// Filters the query with the academic_id.
		cq.where(cb.equal(root.get(Occupation_.academic), idAcademic));
		Occupation result = executeSingleResultQuery(cq, idAcademic);
		logger.log(Level.INFO, "Retrieve occupation by the academic_id \"{0}\" returned \"{1}\"",
				new Object[] { idAcademic, result });
		return result;
	}

	@Override
	public List<Occupation> retriveOccupationsByPPG(Long idPPG) throws PersistentObjectNotFoundException {
		logger.log(Level.FINE, "Retrieving academics who are part of the PPG \"{0}\"...", idPPG);

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Occupation> cq = cb.createQuery(Occupation.class);
		Root<Occupation> occupation = cq.from(Occupation.class);

		occupation.join(Occupation_.academic);

		cq.select(occupation);
		cq.where(cb.equal(occupation.get(Occupation_.ppg), idPPG));

		List<Occupation> result = entityManager.createQuery(cq).getResultList();

		return result;
	}
}
