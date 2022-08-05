package br.ufes.informatica.marvin.academicControl.application;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.Solicitation;
import br.ufes.informatica.marvin.academicControl.domain.SolicitationType;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;
import br.ufes.informatica.marvin.academicControl.persistence.SolicitationDAO;
import br.ufes.informatica.marvin.core.domain.Academic;

@Stateless
@PermitAll
public class SolicitationServiceBean extends CrudServiceBean<Solicitation> implements SolicitationService {
	private static final long serialVersionUID = 1L;

	@EJB
	private SolicitationDAO solicitationDAO;

	@Override
	public BaseDAO<Solicitation> getDAO() {
		return solicitationDAO;
	}

	@Override
	public void solicitationSubject(Academic currentUser, List<SubjectOffer> listSubjectOffer) {
		for (SubjectOffer offer : listSubjectOffer) {
			Solicitation solicitation = new Solicitation();
			solicitation.setRequester(currentUser);
			solicitation.setSubjectOffer(offer);
			solicitation.setSolicitationType(SolicitationType.ENROLLMENT_REQUEST);
			solicitationDAO.save(solicitation);
		}
	}

}
