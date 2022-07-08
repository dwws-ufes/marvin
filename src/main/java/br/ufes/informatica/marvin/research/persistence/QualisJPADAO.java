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
import br.ufes.informatica.marvin.research.domain.Qualis;
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
	public List<Qualis> retrieveByQualisValidity(Long id) throws PersistentObjectNotFoundException {
		logger.log(Level.FINE, "Retrieving qualis who are part of the QualisValidity \"{0}\"...", id);

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Qualis> cq = cb.createQuery(Qualis.class);
		Root<Qualis> qualis = cq.from(Qualis.class);

		cq.select(qualis);
		cq.where(cb.equal(qualis.get(Qualis_.qualisValidity), id));

		List<Qualis> result = entityManager.createQuery(cq).getResultList();

		return result;

	}
}
