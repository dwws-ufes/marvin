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
import br.ufes.informatica.marvin.academicControl.domain.Request;
import br.ufes.informatica.marvin.academicControl.enums.EnumRequestSituation;
import br.ufes.informatica.marvin.academicControl.persistence.RequestDAO;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.utils.MarvinFunctions;

@Stateless
@PermitAll
public class RequestServiceBean extends CrudServiceBean<Request> implements RequestService {
	private static final long serialVersionUID = 1L;

	@EJB
	private RequestDAO requestDAO;

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
		MarvinFunctions.showMessageInScreen(FacesMessage.SEVERITY_INFO, "Request realized with success!");
	}

	@Override
	public void refuseRequest(Academic currentUser, Request request) {
		request.setGrantor(currentUser);
		request.setResponseDate(MarvinFunctions.sysdate());
		request.setRequestSituation(EnumRequestSituation.REFUSED);
		requestDAO.save(request);
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
		}
	}

	@Override
	public void revokeStatus(Academic currentUser, Request request) {
		request.setGrantor(null);
		request.setResponseDate(null);
		request.setRequestResponseDetailing(null);
		setSituationAndSave(currentUser, request, EnumRequestSituation.UNDER_ANALYSIS);
	}

	@Override
	public List<Request> retrieveRequestsByUser(Academic currentUser) throws Exception {
		return requestDAO.retrieveRequestsByUser(currentUser);
	}

	@Override
	public void validateDelete(Request request) throws CrudException {
		CrudException crudException = null;
		if (!EnumRequestSituation.WAITING.equals(request.getRequestSituation()))
			crudException = addGlobalValidationError(crudException, null, "error.request.situationDontAllow");
		if (crudException != null)
			throw crudException;
	}

	@Override
	public void validateCreate(Request request) throws CrudException {
		CrudException crudException = null;
		if (requestAlreadyExist(request))
			crudException = addGlobalValidationError(crudException, null, "error.request.typeAlreadyExists");
		if (crudException != null)
			throw crudException;
	}
}
