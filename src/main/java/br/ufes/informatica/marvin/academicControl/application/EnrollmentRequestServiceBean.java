package br.ufes.informatica.marvin.academicControl.application;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.EnrollmentRequest;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;
import br.ufes.informatica.marvin.academicControl.persistence.EnrollmentRequestDAO;
import br.ufes.informatica.marvin.core.domain.Academic;

@Stateless
@PermitAll
public class EnrollmentRequestServiceBean extends CrudServiceBean<EnrollmentRequest>
		implements EnrollmentRequestService {
	private static final long serialVersionUID = 1L;

	@EJB
	private EnrollmentRequestDAO enrollmentRequestDAO;

	@Override
	public BaseDAO<EnrollmentRequest> getDAO() {
		return enrollmentRequestDAO;
	}

	@Override
	public void createEnrollmentRequestSubject(Academic currentUser, List<SubjectOffer> listSubjectOffer) {
		for (SubjectOffer offer : listSubjectOffer) {
			EnrollmentRequest enrollmentRequest = new EnrollmentRequest();
			enrollmentRequest.setRequester(currentUser);
			enrollmentRequest.setSubjectOffer(offer);
			enrollmentRequestDAO.save(enrollmentRequest);
		}
	}

}
