package br.ufes.informatica.marvin.academicControl.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;
import br.ufes.informatica.marvin.academicControl.persistence.SubjectOfferDAO;

@Stateless
@PermitAll
public class SubjectOfferServiceBean extends CrudServiceBean<SubjectOffer> implements SubjectOfferService {
	private static final long serialVersionUID = 1L;

	@EJB
	private SubjectOfferDAO subjectOfferDAO;

	@Override
	public BaseDAO<SubjectOffer> getDAO() {
		return subjectOfferDAO;
	}

}
