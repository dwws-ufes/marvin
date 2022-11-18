package br.ufes.informatica.marvin.academicControl.persistence;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.informatica.marvin.academicControl.domain.EnrollmentRequest;
import br.ufes.informatica.marvin.academicControl.domain.EnrollmentRequest_;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.utils.MarvinFunctions;

@Stateless
public class EnrollmentRequestJPADAO extends BaseJPADAO<EnrollmentRequest> implements EnrollmentRequestDAO {
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public List<EnrollmentRequest> getListEnrollmentRequestActualPeriodByUser(Academic currentUser) throws Exception {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<EnrollmentRequest> cq = cb.createQuery(EnrollmentRequest.class);
		Root<EnrollmentRequest> root = cq.from(EnrollmentRequest.class);

		Date sysdate = MarvinFunctions.sysdate();
		cq.where(cb.lessThanOrEqualTo(root.get("subjectOffer").get("period").get("offerStartDate"), sysdate));
		cq.where(cb.greaterThanOrEqualTo(root.get("subjectOffer").get("period").get("periodFinalDate"), sysdate));
		cq.where(cb.equal(root.get(EnrollmentRequest_.requester), currentUser));

		return getEntityManager().createQuery(cq).getResultList();
	}
}