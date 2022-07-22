package br.ufes.informatica.marvin.research.controller;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.model.file.UploadedFile;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.application.filters.LikeFilter;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.marvin.research.application.ManageVenuesService;
import br.ufes.informatica.marvin.research.domain.Venue;

/**
 * Controller for the "Change Password" use case.
 * 
 * This use case allows any user to supply an e-mail address and ask Marvin to
 * reset her password. Marvin then sends an e-mail with a code embeded in a URL.
 * When the user opens that URL, she comes back to Marvin to register her new
 * password.
 * 
 * This controller is conversation scoped, beginning the conversation in method
 * checkCode() and ending in method end(), which should be called explicitly by
 * the web pages.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Named("manageVenueController")
@SessionScoped
public class ManageVenuesController extends CrudController<Venue> {
	/** The unique identifier for a serializable class. */
	private static final long serialVersionUID = 1L;

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(ManageVenuesController.class.getCanonicalName());

	/**
	 * Path to the folder where the view files (web pages) for this action are
	 * placed.
	 */
	private static final String VIEW_PATH = "/research/manageVenues/";

	/** TODO: document this field. */
	private UploadedFile file;

	private Date dtStart;

	private Date dtEnd;

	@EJB
	private ManageVenuesService manageVenuesService;

	@Override
	protected CrudService<Venue> getCrudService() {
		return manageVenuesService;
	}

	/** Getter for file. */
	public UploadedFile getFile() {
		return file;
	}

	/** Setter for file. */
	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Date getDtStart() {
		return dtStart;
	}

	public void setDtStart(Date dtStart) {
		this.dtStart = dtStart;
	}

	public Date getDtEnd() {
		return dtEnd;
	}

	public void setDtEnd(Date dtEnd) {
		this.dtEnd = dtEnd;
	}

	protected void initFilters() {
		addFilter(new LikeFilter("manageVeunues.filter.byName", "name",
				getI18nMessage("msgsResearch", "manageVeunues.text.filter.byName")));
	}

	public String upload() {

		try {
			// Performs the upload.
			manageVenuesService.uploadVenueCV(file.getInputStream());
			// Retrieve information on the researcher.
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), new Object[] { "Exception" });
			return null;
		}

		return VIEW_PATH + "index.xhtml";
	}

	public String teste() {
		return VIEW_PATH + "form.xhtml";
	}

}
