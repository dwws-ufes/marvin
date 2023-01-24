package br.ufes.informatica.marvin.academicControl.application;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudException;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.EnrollmentRequest;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;
import br.ufes.informatica.marvin.academicControl.enums.EnumEnrollmentRequestSituation;
import br.ufes.informatica.marvin.academicControl.persistence.EnrollmentRequestDAO;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.utils.MarvinFunctions;

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
		MarvinFunctions.showMessageInScreen(FacesMessage.SEVERITY_INFO, "Enrollment Request realized with success!");
	}

	@Override
	public List<EnrollmentRequest> retrieveEnrollmentRequests(Academic currentUser) throws Exception {
		if (!MarvinFunctions.isStaffOrAdmin()) {
			return enrollmentRequestDAO.retrieveEnrollmentRequestsActualPeriodByUser(currentUser);
		}
		return enrollmentRequestDAO.retrieveAll();
	}

	@Override
	public void registeredOnSappg(EnrollmentRequest enrollmentRequest) {
		enrollmentRequest.setRegisteredSappg(enrollmentRequest.getRegisteredSappg() == false ? true : false);
		this.update(enrollmentRequest);
		enrollmentRequestDAO.save(enrollmentRequest);
	}

	@Override
	public void validateUpdate(EnrollmentRequest enrollmentRequest) throws CrudException {
		super.validateUpdate(enrollmentRequest);
		CrudException crudException = null;
		EnrollmentRequest enrollmentRequestBD = enrollmentRequestDAO.retrieveById(enrollmentRequest.getId());

		if (enrollmentRequestBD.getRegisteredSappg() == false && //
				enrollmentRequest.getRegisteredSappg() == true && //
				EnumEnrollmentRequestSituation.REFUSED.equals(enrollmentRequest.getEnrollmentRequestSituation()))
			crudException = addGlobalValidationError(crudException, null,
					"error.enrollmentRequest.enrollmentRequestWasRefused");

		if (crudException != null)
			throw crudException;
	}

}
