package br.ufes.informatica.marvin.academicControl.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.informatica.marvin.academicControl.domain.Deadline;
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

		Predicate[] predicates = new Predicate[3];
		predicates[0] = cb.equal(root.get(Request_.requester), request.getRequester());
		predicates[1] = cb.notEqual(root.get(Request_.requestSituation), EnumRequestSituation.REFUSED);
		predicates[2] = cb.equal(root.get(Request_.deadline), request.getDeadline());

		cq.select(root).where(predicates);

		return getEntityManager().createQuery(cq).getResultList().size() > 0;
	}

	@Override
	public boolean deadlineIsLinkedInRequest(Deadline deadline) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Request> cq = cb.createQuery(Request.class);
		Root<Request> root = cq.from(Request.class);

		Predicate[] predicates = new Predicate[1];
		predicates[0] = cb.equal(root.get(Request_.deadline), deadline);

		cq.select(root).where(predicates);

		return getEntityManager().createQuery(cq).getResultList().size() > 0;
	}

	@Override
	public List<Request> requestWithoutAnswer() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Request> cq = cb.createQuery(Request.class);
		Root<Request> root = cq.from(Request.class);
		cq.where(root.get(Request_.requestSituation)
				.in(List.of(EnumRequestSituation.UNDER_ANALYSIS, EnumRequestSituation.WAITING)));

		/* TODO inserir calculo do tempo que falta para expirar o tempo da requisição */
		/*
		 * cq.where(cb.lessThanOrEqualTo(root.get(Request_.daysLeftToDeadline),
		 * root.get("deadline").get("maxDaysToSendMail")));
		 */
		return getEntityManager().createQuery(cq).getResultList();
	}

}