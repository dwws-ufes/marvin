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
import br.ufes.informatica.marvin.academicControl.domain.MailSender;
import br.ufes.informatica.marvin.academicControl.domain.MailSender_;
import br.ufes.informatica.marvin.academicControl.enums.EnumStatusMail;

@Stateless
public class MailSenderJPADAO extends BaseJPADAO<MailSender> implements MailSenderDAO {
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public List<MailSender> retrieveMailsNotSent() {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<MailSender> cq = cb.createQuery(MailSender.class);
		Root<MailSender> root = cq.from(MailSender.class);

		Predicate[] predicates = new Predicate[1];
		predicates[0] = cb.equal(root.get(MailSender_.statusMail), EnumStatusMail.CREATED);

		cq.select(root).where(predicates);

		return getEntityManager().createQuery(cq).getResultList();
	}

}