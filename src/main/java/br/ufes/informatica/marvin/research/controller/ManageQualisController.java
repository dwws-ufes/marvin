package br.ufes.informatica.marvin.research.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.model.file.UploadedFile;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.marvin.research.application.ManageQualisService;
import br.ufes.informatica.marvin.research.domain.Qualis;

@Named("manageQualisController")
@SessionScoped
public class ManageQualisController extends CrudController<Qualis> {
	private static final long serialVersionUID = 1L;

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(ManageQualisController.class.getCanonicalName());

	@EJB
	private ManageQualisService manageQualisService;

	private UploadedFile file;

	@Override
	protected CrudService<Qualis> getCrudService() {
		return manageQualisService;
	}

	@Override
	protected void initFilters() {
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String uploadCSV() {

		try {
			// Performs the upload.
			manageQualisService.uploadQualisCSV(file.getInputStream());
			// Retrieve information on the researcher.
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), new Object[] { "Exception" });
			return null;
		}

		return list();
	}

}
