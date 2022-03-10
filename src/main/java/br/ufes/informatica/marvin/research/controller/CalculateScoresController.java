package br.ufes.informatica.marvin.research.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.application.filters.Filter;
import br.ufes.inf.nemo.jbutler.ejb.application.filters.LikeFilter;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Role;
import br.ufes.informatica.marvin.research.application.CalculateScoresService;

@Named("calculateScoresController")
@SessionScoped
public class CalculateScoresController extends CrudController<Academic> {

	private static final long serialVersionUID = 1L;

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(CalculateScoresController.class.getCanonicalName());

	/**
	 * Path to the folder where the view files (web pages) for this action are
	 * placed.
	 */
	private static final String VIEW_PATH = "/research/calculateScores/";

	private List<Academic> academics;

	@EJB
	private CalculateScoresService calculateScoresService;

	@Override
	protected void initFilters() {
		addFilter(new LikeFilter("calcualteScores.filter.byName", "name", "name"));
		this.filters = new ArrayList<Filter<?>>();
		this.setFilterKey("calcualteScores.filter.byName");
		this.setFilterParam("rafael");
		filter();
	}

	@Override
	protected CrudService<Academic> getCrudService() {
		return calculateScoresService;
	}

	public void teste() {
		this.filters = new ArrayList<Filter<?>>();
		addFilter(new LikeFilter("calcualteScores.filter.byName", "name", "name"));
		this.setFilterKey("calcualteScores.filter.byName");
		this.setFilterParam("rafael");
		filter();
	}

	public String translateRole(Set<Role> roles) {
		for (Role role : roles) {
			if (role.getName().equals("Doctoral") || role.getName().equals("Master")) {
				return "Sim";
			}
		}
		return "Não";
	}

	public String calculate() {
		try {
			this.academics = this.calculateScoresService.findAcademicQualified();

			return "";
//			return VIEW_PATH + "list.xhtml?faces-redirect=true";
		} catch (PersistentObjectNotFoundException e) {
			logger.log(Level.INFO, "Lattes parser error.");
			addGlobalI18nMessage("msgsResearch", FacesMessage.SEVERITY_ERROR,
					"uploadLattesCV.error.lattesParseError.summary",
					"uploadLattesCV.error.lattesIdNotRegistered.detail");
			return "";
		} catch (MultiplePersistentObjectsFoundException e) {
			return "";
		}
	}

}
