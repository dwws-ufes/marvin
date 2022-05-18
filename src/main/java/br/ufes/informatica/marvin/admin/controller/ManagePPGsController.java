package br.ufes.informatica.marvin.admin.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudException;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.application.filters.Criterion;
import br.ufes.inf.nemo.jbutler.ejb.application.filters.CriterionType;
import br.ufes.inf.nemo.jbutler.ejb.application.filters.Filter;
import br.ufes.inf.nemo.jbutler.ejb.application.filters.SimpleFilter;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.inf.nemo.jbutler.ejb.controller.PersistentObjectConverterFromId;
import br.ufes.informatica.marvin.core.application.ManageAcademicsService;
import br.ufes.informatica.marvin.core.application.ManageOccupationsService;
import br.ufes.informatica.marvin.core.application.ManagePPGsService;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Occupation;
import br.ufes.informatica.marvin.core.domain.PPG;
import br.ufes.informatica.marvin.core.domain.Role;

@Named
@SessionScoped
public class ManagePPGsController extends CrudController<PPG> {
	private static final long serialVersionUID = 1L;

	@EJB
	private ManagePPGsService managePPGsService;

	@EJB
	private ManageAcademicsService manageAcademicsService;

	@EJB
	private ManageOccupationsService manageOccupationsService;

	private List<Academic> coordinators;

	@Override
	protected CrudService<PPG> getCrudService() {
		return managePPGsService;
	}

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(ManagePPGsController.class.getCanonicalName());

	/**
	 * Path to the folder where the view files (web pages) for this action are
	 * placed.
	 */
	private static final String VIEW_PATH = "/admin/managePpgs/";

	@Override
	protected void initFilters() {
	}

	public List<Academic> getCoordinators() {
		return coordinators;
	}

	public void setCoordinators(List<Academic> coordinators) {
		this.coordinators = coordinators;
	}

	public List<Academic> completeCoordinators(String query) {
		return manageOccupationsService.findAcademicByNameEmail(query);
	}

	public PersistentObjectConverterFromId<Academic> getCoordinatorConverter() {
		return manageOccupationsService.getAcademicConverter();
	}

	public String administrators() {
//		String[] roles = { "Secretary", "Coordinator" };
//		this.roleList = new ArrayList<Role>();
//
//		setselectedCoordinator(null);
		return VIEW_PATH + "formadministrators.xhtml" + "?faces-redirect=" + getFacesRedirect();
	}

	public String savePPG() {
		try {
			managePPGsService.validateCreate(selectedEntity, coordinators);
			managePPGsService.create(selectedEntity);

			List<Occupation> oldCoordinators = manageOccupationsService.findOccupationsByPPG(selectedEntity.getId());

			for (Academic academic : this.coordinators) {

				Occupation occupation = manageOccupationsService.findOccupationByAcademic(academic.getId());

				if (occupation == null) {
					Occupation newOccupation = new Occupation();
					newOccupation.setAcademic(academic);
					newOccupation.setCoordinator(true);
					newOccupation.setSecretary(false);
					newOccupation.setDoctoral_supervisor(false);
					newOccupation.setMember(true);
					newOccupation.setPpg(selectedEntity);
					manageOccupationsService.create(newOccupation);
				} else {
					occupation.setCoordinator(true);
					occupation.setSecretary(false);
					occupation.setDoctoral_supervisor(false);
					occupation.setMember(true);
					occupation.setPpg(selectedEntity);
					manageOccupationsService.update(occupation);

					if (oldCoordinators.contains(occupation)) {
						oldCoordinators.remove(occupation);
					}
				}
			}

			for (Occupation remove : oldCoordinators) {
				manageOccupationsService.delete(remove);
			}

			setCoordinators(null);

			return list();
		} catch (CrudException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Create PPG requested", e.getMessage());
			context.addMessage(null, message);
		}

		return "";

	}

	public String updatePPG() {
		setCoordinators(null);
		if (selectedEntity != null) {
			List<Academic> coordinators = manageOccupationsService.findAcademicsByPPG(selectedEntity.getId());
			setCoordinators(coordinators);
			return update();
		}

		return "";
	}

	public String createPPG() {
		setCoordinators(null);
		return create();
	}

	public String saveAdministrator() {
//		Academic academic = manageAcademicsService.retrieve(this.selectedCoordinator.getId());
//		PPG ppg = managePPGsService.retrieve(selectedEntity.getId());

		return VIEW_PATH + "index.xhtml" + "?faces-redirect=" + getFacesRedirect();
	}

	public PersistentObjectConverterFromId<Role> getRoleConverter() {
		return manageAcademicsService.getRoleConverter();
	}

	public void deletePPG() {
		for (PPG delete : trashCan) {
			Criterion cri = new Criterion("ppg", CriterionType.EQUALS, delete);
			Filter<Void> filter = new SimpleFilter("ppg", "ppg_id", "ppg", cri);
			int[] inteval = { 0, 100 };
			List<Academic> list = manageAcademicsService.filter(filter, delete.getId().toString(), inteval);

			for (Academic academic : list) {
				manageAcademicsService.getDAO().save(academic);
			}
		}
		delete();
	}
}
