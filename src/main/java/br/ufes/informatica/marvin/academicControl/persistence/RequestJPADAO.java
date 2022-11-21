package br.ufes.informatica.marvin.academicControl.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.informatica.marvin.academicControl.domain.Request;
import br.ufes.informatica.marvin.academicControl.domain.Request_;
import br.ufes.informatica.marvin.academicControl.enums.EnumRequestSituation;
import br.ufes.informatica.marvin.core.domain.Academic;

@Stateless
public class RequestJPADAO extends BaseJPADAO<Request> implements RequestDAO {
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public List<Request> retrieveRequestsByUser(Academic currentUser) throws Exception {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Request> cq = cb.createQuery(Request.class);
		Root<Request> root = cq.from(Request.class);

		cq.where(cb.equal(root.get(Request_.requester), currentUser));

		return getEntityManager().createQuery(cq).getResultList();
	}

	@Override
	public boolean requestAlreadyExist(Request request) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Request> cq = cb.createQuery(Request.class);
		Root<Request> root = cq.from(Request.class);

		cq.where(cb.lessThanOrEqualTo(root.get(Request_.requester), request.getRequester()));
		cq.where(cb.notEqual(root.get(Request_.requestSituation), EnumRequestSituation.REFUSED));
		cq.where(cb.equal(root.get(Request_.deadline), request.getDeadline()));

		return getEntityManager().createQuery(cq).getResultList().size() > 0;
	}

}