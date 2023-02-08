package br.ufes.informatica.marvin.academicControl.application;

import java.util.List;
import java.util.Objects;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudException;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.Deadline;
import br.ufes.informatica.marvin.academicControl.domain.Request;
import br.ufes.informatica.marvin.academicControl.enums.EnumRequestSituation;
import br.ufes.informatica.marvin.academicControl.persistence.RequestDAO;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.utils.MarvinFunctions;

@Stateless
@PermitAll
public class RequestServiceBean extends CrudServiceBean<Request> implements RequestService {
	private static final long serialVersionUID = 1L;
	private static final String subjectMailChangeSituation = "Marvin - Alteração de situação da requisição";

	@EJB
	private RequestDAO requestDAO;

	@EJB
	private MailSenderService mailSenderService;

	@Override
	public BaseDAO<Request> getDAO() {
		return requestDAO;
	}

	@Override
	public boolean requestAlreadyExist(Request request) {
		return requestDAO.requestAlreadyExist(request);
	}

	@Override
	public void responseRequest(Academic currentUser, Request request) {
		request.setGrantor(currentUser);
		request.setResponseDate(MarvinFunctions.sysdate());
		request.setLocalfileUniversityDegree(MarvinFunctions.saveFileInServer(request.getFileUniversityDegree()));
		request.setUserSituation(MarvinFunctions.nvl(request.getUserSituation(), currentUser));
		request.setUserSituationDate(MarvinFunctions.nvl(request.getUserSituationDate(), MarvinFunctions.sysdate()));
		request.setRequestSituation(EnumRequestSituation.FINALIZED);
		/* TODO set field RegistrationNumber in Academic using nvl */
		requestDAO.save(request);
		mailSenderService.createMailSender(//
				request.getRequester().getEmail(), //
				subjectMailChangeSituation, //
				"Sua requisição de " + request.getDeadline().getDeadlineType().getDescription() + " foi respondida."//
		);
	}

	@Override
	public void refuseRequest(Academic currentUser, Request request) {
		request.setGrantor(currentUser);
		request.setResponseDate(MarvinFunctions.sysdate());
		request.setRequestSituation(EnumRequestSituation.REFUSED);
		requestDAO.save(request);
		mailSenderService.createMailSender(//
				request.getRequester().getEmail(), //
				subjectMailChangeSituation, //
				"Sua requisição de " + request.getDeadline().getDeadlineType()
						.getDescription() + " foi recusada, favor checar as observações do porque ela foi recusada."//
		);
	}

	private void setSituationAndSave(Academic currentUser, Request request, EnumRequestSituation situation) {
		request.setUserSituation(currentUser);
		request.setUserSituationDate(MarvinFunctions.sysdate());
		request.setRequestSituation(situation);
		requestDAO.save(request);
	}

	@Override
	public void changeStatus(Academic currentUser, Request request) {
		if (!Objects.equals(EnumRequestSituation.WAITING, request.getRequestSituation())) {
			MarvinFunctions.showMessageInScreen(FacesMessage.SEVERITY_ERROR,
					"Request situation don't allow this action!");
		} else {
			setSituationAndSave(currentUser, request, EnumRequestSituation.UNDER_ANALYSIS);
			mailSenderService.createMailSender(//
					request.getRequester().getEmail(), //
					subjectMailChangeSituation, //
					"Sua requisição de " + request.getDeadline().getDeadlineType()
							.getDescription() + " está sob análise.");
		}
	}

	@Override
	public void revokeStatus(Academic currentUser, Request request) {
		request.setGrantor(null);
		request.setResponseDate(null);
		request.setRequestResponseDetailing(null);
		setSituationAndSave(currentUser, request, EnumRequestSituation.UNDER_ANALYSIS);
		mailSenderService.createMailSender(//
				request.getRequester().getEmail(), //
				subjectMailChangeSituation, //
				"Sua requisição de " + request.getDeadline().getDeadlineType()
						.getDescription() + " voltou para a situação de análise.");
	}

	@Override
	public List<Request> retrieveRequests(Academic currentUser) throws Exception {
		if (!MarvinFunctions.isStaffOrAdmin()) {
			return requestDAO.retrieveRequestsByUser(currentUser);
		}
		return requestDAO.retrieveAll();
	}

	@Override
	public void validateDelete(Request request) throws CrudException {
		super.validateDelete(request);
		CrudException crudException = null;
		if (!EnumRequestSituation.WAITING.equals(request.getRequestSituation()))
			crudException = addGlobalValidationError(crudException, null, "error.request.situationDontAllow");
		MarvinFunctions.verifyAndThrowCrudException(crudException);
	}

	@Override
	public void validateCreate(Request request) throws CrudException {
		super.validateCreate(request);
		CrudException crudException = null;
		if (requestAlreadyExist(request))
			crudException = addGlobalValidationError(crudException, null, "error.request.typeAlreadyExists");
		MarvinFunctions.verifyAndThrowCrudException(crudException);
	}

	@Override
	public List<Request> requestWithoutAnswer() {
		return requestDAO.requestWithoutAnswer();
	}

	@Override
	public boolean deadlineIsLinkedInRequest(Deadline deadline) {
		return requestDAO.deadlineIsLinkedInRequest(deadline);
	}

}
