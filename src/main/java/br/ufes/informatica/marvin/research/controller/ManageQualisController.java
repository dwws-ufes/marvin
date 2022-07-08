package br.ufes.informatica.marvin.research.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudException;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.marvin.core.application.LoginService;
import br.ufes.informatica.marvin.core.application.ManageOccupationsService;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Occupation;
import br.ufes.informatica.marvin.core.domain.PPG;
import br.ufes.informatica.marvin.research.application.ManageQualisService;
import br.ufes.informatica.marvin.research.application.ManageQualisValidityService;
import br.ufes.informatica.marvin.research.domain.Qualis;
import br.ufes.informatica.marvin.research.domain.QualisValidity;

@Named
@SessionScoped
public class ManageQualisController extends CrudController<Qualis> {
	private static final long serialVersionUID = 1L;

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(ManageQualisController.class.getCanonicalName());

	private String VIEW_PATH = "/research/manageQualis/";

	@EJB
	private ManageQualisService manageQualisService;

	@EJB
	private ManageQualisValidityService manageQualisValidityService;

	/** The login service. */
	@EJB
	private LoginService loginService;

	@EJB
	private ManageOccupationsService manageOccupationService;

	private QualisValidity newQualisValidity = new QualisValidity();

	private Qualis newQualis = new Qualis();

	private List<Qualis> qualis;

	@Override
	protected CrudService<Qualis> getCrudService() {
		return manageQualisService;
	}

	@Override
	protected void initFilters() {
	}

	public QualisValidity getNewQualisValidity() {
		return newQualisValidity;
	}

	public void setNewQualisValidity(QualisValidity newQualisValidity) {
		this.newQualisValidity = newQualisValidity;
	}

	public Qualis getNewQualis() {
		return newQualis;
	}

	public void setNewQualis(Qualis newQualis) {
		this.newQualis = newQualis;
	}

	public List<Qualis> getQualis() {
		return qualis;
	}

	public void setQualis(List<Qualis> qualis) {
		this.qualis = qualis;
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

	public void saveQuality() {

		try {
			PPG ppg = getUserPPGId();

			if (ppg == null) {
				throw new CrudException("Could not find your ppg", "ERROR", null);
			}

			QualisValidity qualisValidity = manageQualisValidityService.findByDates(newQualisValidity.getDtStart(),
					newQualisValidity.getDtEnd());

			if (qualisValidity == null) {
				qualisValidity = newQualisValidity;
				qualisValidity.setPpg(ppg);
				qualisValidity.setQualis(new ArrayList<Qualis>());
				manageQualisValidityService.create(qualisValidity);
			}

			newQualis.setQualisValidity(qualisValidity);
			manageQualisService.create(newQualis);

			qualisValidity.AddQualis(newQualis);
			manageQualisValidityService.update(qualisValidity);

			setQualis(manageQualisService.findByQualisValidity(qualisValidity.getId()));

		} catch (CrudException e) {

			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Create Qualis requested",
					e.getMessage());
			context.addMessage(null, message);
		}

	}

	public String administrators() {
		return VIEW_PATH + "form.xhtml" + "?faces-redirect=" + getFacesRedirect();
	}

	public String createQualis() {
		return VIEW_PATH + "form.xhtml" + "?faces-redirect=" + getFacesRedirect();
	}

}
