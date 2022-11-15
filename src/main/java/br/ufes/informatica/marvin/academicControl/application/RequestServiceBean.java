package br.ufes.informatica.marvin.academicControl.application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;

import org.primefaces.model.file.UploadedFile;

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

	private static final Logger logger = Logger.getLogger(RequestServiceBean.class.getCanonicalName());

	@EJB
	private RequestDAO requestDAO;

	@Override
	public BaseDAO<Request> getDAO() {
		return requestDAO;
	}

	private String saveFileInServer(UploadedFile file) {
		try {
			if (Objects.nonNull(file) && Objects.nonNull(file.getFileName()) && file.getSize() > 0) {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
				String name = System.getProperty("java.io.tmpdir") + fmt.format(new Date()) + file.getFileName();
				logger.log(Level.FINE, "Saving the file " + name + " in temp folder");
				File fileToSave = new File(name);
				InputStream is = file.getInputStream();
				OutputStream out = new FileOutputStream(fileToSave);
				byte buf[] = new byte[1024];
				int len;
				while ((len = is.read(buf)) > 0)
					out.write(buf, 0, len);
				is.close();
				out.close();
				return name;
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "Error saving file to folder Temp");
			MarvinFunctions.showMessageInScreen(FacesMessage.SEVERITY_ERROR, "Failed to save file to server!",
					e.getMessage());
		}

		return null;
	}

	@Override
	public void createRequest(Academic currentUser, Request request) {
		request.setRequester(currentUser);
		request.setLocalfileUseOfCredits(saveFileInServer(request.getFileUseOfCredits()));
		requestDAO.save(request);
		MarvinFunctions.showMessageInScreen(FacesMessage.SEVERITY_INFO, "Request realized with success!");
	}

	@Override
	public void responseRequest(Academic currentUser, Request request) {
		request.setGrantor(currentUser);
		request.setResponseDate(MarvinFunctions.sysdate());
		request.setLocalfileUniversityDegree(saveFileInServer(request.getFileUniversityDegree()));
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
}
