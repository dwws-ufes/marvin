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

import org.primefaces.model.file.UploadedFile;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.Request;
import br.ufes.informatica.marvin.academicControl.persistence.RequestDAO;
import br.ufes.informatica.marvin.core.domain.Academic;

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
		}

		return null;
	}

	@Override
	public void createRequest(Academic currentUser, Request request) {
		request.setRequester(currentUser);
		request.setLocalfileUniversityDegree(saveFileInServer(request.getFileUniversityDegree()));
		request.setLocalfileUseOfCredits(saveFileInServer(request.getFileUseOfCredits()));
		requestDAO.save(request);
	}
}
