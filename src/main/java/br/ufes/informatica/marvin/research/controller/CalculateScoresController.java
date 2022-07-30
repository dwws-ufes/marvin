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
import br.ufes.inf.nemo.jbutler.ejb.controller.PersistentObjectConverterFromId;
import br.ufes.informatica.marvin.core.application.LoginService;
import br.ufes.informatica.marvin.core.application.ManageOccupationsService;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Occupation;
import br.ufes.informatica.marvin.core.domain.PPG;
import br.ufes.informatica.marvin.research.application.CalculateScoresService;
import br.ufes.informatica.marvin.research.application.ManageQualisService;
import br.ufes.informatica.marvin.research.application.ManageRulesService;
import br.ufes.informatica.marvin.research.domain.Rule;
import br.ufes.informatica.marvin.research.domain.Score;

@Named
@SessionScoped
public class CalculateScoresController extends CrudController<Occupation> {

	private static final long serialVersionUID = 1L;

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(CalculateScoresController.class.getCanonicalName());

	/**
	 * Path to the folder where the view files (web pages) for this action are
	 * placed.
	 */
	private static final String VIEW_PATH = "/research/calculateScores/";

	private List<Occupation> academics = new ArrayList<Occupation>();

	private Academic selectedAcademic;

	private String type;

	private List<Score> scores;

	@EJB
	private CalculateScoresService calculateScoresService;

	@EJB
	private ManageOccupationsService manageOccupationsService;

	@EJB
	private ManageRulesService manageRulesService;

	@EJB
	private ManageQualisService manageQualisService;

	/** The login service. */
	@EJB
	private LoginService loginService;

	@Override
	protected CrudService<Occupation> getCrudService() {
		return calculateScoresService;
	}

	@Override
	protected void initFilters() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Academic getSelectedAcademic() {
		return selectedAcademic;
	}

	public void setSelectedAcademic(Academic selectedAcademic) {
		this.selectedAcademic = selectedAcademic;
	}

	public List<Occupation> getAcademics() {

		PPG ppg = getUserPPGId();

		if (ppg == null) {
			return this.manageOccupationsService.findOccupationByDoctoralMaster(0l);
		}

		return this.manageOccupationsService.findOccupationByDoctoralMaster(ppg.getId());
	}

	public void setAcademics(List<Occupation> academics) {
		this.academics = academics;
	}

	public List<Score> getScores() {
		return scores;
	}

	public void setScores(List<Score> scores) {
		this.scores = scores;
	}

	public List<Academic> completeAcademics(String query) {
		return manageOccupationsService.findAcademicByNameEmail(query);
	}

	public PersistentObjectConverterFromId<Academic> getAcademicConverter() {
		return manageOccupationsService.getAcademicConverter();
	}

	public String calculate() {

		try {
			PPG ppg = getUserPPGId();

			if (ppg == null) {
				throw new CrudException("Could not find your ppg", "ERROR", null);
			}

			List<Occupation> academicsEvaluation = getAcademics();

			List<Rule> rules = manageRulesService.findValidityRules();

			this.scores = calculateScoresService.calculate(academicsEvaluation, rules);

			return VIEW_PATH + "score.xhtml";

		} catch (CrudException e) {

			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Create Qualis requested",
					e.getMessage());
			context.addMessage(null, message);
		}
		return VIEW_PATH + "index.xhtml";
	}

	public void addAcademic() {

		try {

			Occupation occupation = manageOccupationsService.findOccupationByAcademic(selectedAcademic.getId());

			if (occupation != null) {

				if (type.equals("master")) {
					occupation.setDoctoral_supervisor(false);
				} else if (type.equals("doctoral")) {
					occupation.setDoctoral_supervisor(true);
				}

				occupation.setMember(true);
				occupation.setSecretary(false);
				occupation.setCoordinator(false);

				manageOccupationsService.update(occupation);

			} else {

				Occupation newOccupation = new Occupation();
				newOccupation.setAcademic(selectedAcademic);

				if (type.equals("master")) {
					newOccupation.setDoctoral_supervisor(false);
				} else if (type.equals("doctoral")) {
					newOccupation.setDoctoral_supervisor(true);
				}

				newOccupation.setMember(true);
				newOccupation.setSecretary(false);
				newOccupation.setCoordinator(false);

				PPG ppg = getUserPPGId();

				if (ppg == null) {
					throw new CrudException("Could not find your ppg", "ERROR", null);
				}

				newOccupation.setPpg(ppg);

				manageOccupationsService.create(newOccupation);
			}

		} catch (CrudException e) {

			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Create Qualis requested",
					e.getMessage());
			context.addMessage(null, message);
		}

	}

	public PPG getUserPPGId() {
		Academic currentAcademic = loginService.getCurrentUser();
		if (currentAcademic == null) {
			return null;
		}

		Occupation currentOccupation = manageOccupationsService.findOccupationByAcademic(currentAcademic.getId());

		if (currentOccupation == null) {
			return null;
		}

		return currentOccupation.getPpg();

	}
}
