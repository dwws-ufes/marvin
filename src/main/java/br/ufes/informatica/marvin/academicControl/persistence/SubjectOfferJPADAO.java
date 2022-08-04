package br.ufes.informatica.marvin.academicControl.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;

@Stateless
public class SubjectOfferJPADAO extends BaseJPADAO<SubjectOffer> implements SubjectOfferDAO {
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
}