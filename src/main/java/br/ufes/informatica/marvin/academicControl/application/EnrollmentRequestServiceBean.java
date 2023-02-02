package br.ufes.informatica.marvin.academicControl.application;

import java.util.Date;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudException;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.EnrollmentRequest;
import br.ufes.informatica.marvin.academicControl.domain.Period;
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

	@EJB
	private MailSenderService mailSenderService;

	private static final String subjectMailChangeSituation = "Marvin - Alteração de situação da solicitação de matrícula";

	@Override
	public BaseDAO<EnrollmentRequest> getDAO() {
		return enrollmentRequestDAO;
	}

	@Override
	public void validateUpdate(EnrollmentRequest enrollmentRequest) throws CrudException {
		super.validateUpdate(enrollmentRequest);
		CrudException crudException = null;

		EnrollmentRequest enrollmentRequestBD = enrollmentRequestDAO.retrieveById(enrollmentRequest.getId());

		if (!enrollmentRequestBD.getRegisteredSappg()
				.equals(enrollmentRequest.getRegisteredSappg() && !isAllowedChangeSappg(enrollmentRequest)))
			crudException = addGlobalValidationError(crudException, null,
					"error.enrollmentRequest.notPossibleRegisterSAPPG");
		if (crudException != null)
			throw crudException;

		this.afterUpdate(enrollmentRequest);
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
		if (EnumEnrollmentRequestSituation.WAITING.equals(enrollmentRequest.getEnrollmentRequestSituation()))
			enrollmentRequest.setEnrollmentRequestSituation(EnumEnrollmentRequestSituation.REGISTRED);
		this.update(enrollmentRequest);
	}

	@Override
	public boolean isAllowedChangeSappg(EnrollmentRequest enrollmentRequest) {
		EnrollmentRequest enrollmentRequestBD = enrollmentRequestDAO.retrieveById(enrollmentRequest.getId());

		if (EnumEnrollmentRequestSituation.REFUSED.equals(enrollmentRequest.getEnrollmentRequestSituation()))
			return false;

		Date sysdate = MarvinFunctions.sysdate();
		Period period = enrollmentRequestBD.getSubjectOffer().getPeriod();
		if (!(period.getPeriodStartDate().after(sysdate) && period.getPeriodFinalDate().before(sysdate)))
			return false;

		return true;
	}

	public void afterUpdate(EnrollmentRequest enrollmentRequest) {
		mailSenderService.createMailSender(//
				enrollmentRequest.getRequester().getEmail(), //
				subjectMailChangeSituation, //
				"Sua solicitação de matrícula teve a situação alterada."//
		);
	}
}
