package br.ufes.informatica.marvin.research.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.inf.nemo.jbutler.ejb.controller.PersistentObjectConverterFromId;
import br.ufes.informatica.marvin.core.application.ManageOccupationsService;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Occupation;
import br.ufes.informatica.marvin.research.application.CalculateScoresService;

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

	private Academic selectedAcademic;

	private String type;

	@EJB
	private CalculateScoresService calculateScoresService;

	@EJB
	private ManageOccupationsService manageOccupationsService;

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

	public List<Academic> completeAcademics(String query) {
		return manageOccupationsService.findAcademicByNameEmail(query);
	}

	public PersistentObjectConverterFromId<Academic> getAcademicConverter() {
		return manageOccupationsService.getAcademicConverter();
	}

	public String calculate() {
		return VIEW_PATH + "score.xhtml";
	}

	public void addAcademic() {

	}
}
