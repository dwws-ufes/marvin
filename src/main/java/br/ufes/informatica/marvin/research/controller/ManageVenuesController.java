package br.ufes.informatica.marvin.research.controller;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.model.file.UploadedFile;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudException;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.marvin.core.application.LoginService;
import br.ufes.informatica.marvin.core.application.ManageOccupationsService;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Occupation;
import br.ufes.informatica.marvin.core.domain.PPG;
import br.ufes.informatica.marvin.research.application.ManageQualisService;
import br.ufes.informatica.marvin.research.application.ManageVenuesService;
import br.ufes.informatica.marvin.research.domain.Qualis;
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
@Named
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

	/** The venue service. */
	@EJB
	private ManageVenuesService manageVenuesService;

	/** The qualis service. */
	@EJB
	private ManageQualisService manageQualisService;

	/** The login service. */
	@EJB
	private LoginService loginService;

	/** The occupation service. */
	@EJB
	private ManageOccupationsService manageOccupationService;

	private List<Qualis> qualisValidity;

	private String qualisSelected;

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

	public List<Qualis> getQualisValidity() {
		return qualisValidity;
	}

	public String getQualisSelected() {
		return qualisSelected;
	}

	public void setQualisSelected(String qualisSelected) {
		this.qualisSelected = qualisSelected;
	}

	@Override
	protected void initFilters() {
		// TODO Auto-generated method stub

	}

	public String upload() {

		try {

			PPG ppg = getUserPPGId();

			if (ppg == null) {
				throw new CrudException("Could not find your ppg", "ERROR", null);
			}
			// Performs the upload
			manageVenuesService.uploadVenueCV(file.getInputStream(), this.dtStart, this.dtEnd, ppg);
			// Retrieve information on the researcher.
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), new Object[] { "Exception" });
			return null;
		}

		return list();
	}

	public PPG getUserPPGId() {
		Academic currentAcademic = loginService.getCurrentUser();
		if (currentAcademic == null) {
			return null;
		}

		Occupation currentOccupation = manageOccupationService.findOccupationByAcademic(currentAcademic.getId());

		if (currentOccupation == null) {
			return null;
		}

		return currentOccupation.getPpg();

	}

	public String UpdateVenue() {
		this.qualisValidity = manageQualisService.findByNameValidity(selectedEntity.getDtStart(),
				selectedEntity.getDtEnd(), selectedEntity.getPpg().getId());
		setQualisSelected(selectedEntity.getQualis().getName());
		return VIEW_PATH + "formVenue.xhtml" + "?faces-redirect=" + getFacesRedirect();
	}

	public String saveVenue() {
		for (Qualis qualis : qualisValidity) {
			if (qualis.getName().equals(qualisSelected)) {
				this.selectedEntity.setQualis(qualis);
			}
		}
		return save();
	}
}
