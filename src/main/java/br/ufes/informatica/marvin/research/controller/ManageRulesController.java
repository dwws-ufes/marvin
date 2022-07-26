package br.ufes.informatica.marvin.research.controller;

import javax.annotation.PostConstruct;
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
import br.ufes.informatica.marvin.research.application.ManageRulesService;
import br.ufes.informatica.marvin.research.domain.Rule;

@Named("manageRulesController")
@SessionScoped
public class ManageRulesController extends CrudController<Rule> {
	private static final long serialVersionUID = 1L;

	@EJB
	private ManageRulesService manageRulesService;

	/** The login service. */
	@EJB
	private LoginService loginService;

	@EJB
	private ManageOccupationsService manageOccupationService;

	private String VIEW_PATH = "/research/manageRules/";

	@Override
	protected CrudService<Rule> getCrudService() {
		return manageRulesService;
	}

	@Override
	protected void initFilters() {

	}

	@PostConstruct
	public void init() {
	}

	public String saveRule() {
		try {
			PPG ppg = getUserPPGId();

			if (ppg == null) {
				throw new CrudException("Could not find your ppg", "ERROR", null);
			}

			this.selectedEntity.setPpg(ppg);
			return this.save();
		} catch (CrudException e) {

			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Create Rules requested",
					e.getMessage());
			context.addMessage(null, message);
		}

		return VIEW_PATH + "form.xhtml";

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

}
