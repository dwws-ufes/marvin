package br.ufes.informatica.marvin.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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

	private Academic selectedAdmin;

	private List<Occupation> listAdmins;

	private List<Occupation> trashAdmins;

	private String typeAdmin;

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
	private static final String VIEW_PATH = "/admin/managePPGs/";

	@Override
	protected void initFilters() {
	}

	public Academic getSelectedAdmin() {
		return selectedAdmin;
	}

	public void setSelectedAdmin(Academic selectedAdmin) {
		this.selectedAdmin = selectedAdmin;
	}

	public List<Occupation> getListAdmins() {
		return listAdmins;
	}

	public void setListAdmins(List<Occupation> listAdmins) {
		this.listAdmins = listAdmins;
	}

	public List<Academic> getCoordinators() {
		return coordinators;
	}

	public void setCoordinators(List<Academic> coordinators) {
		this.coordinators = coordinators;
	}

	public String getTypeAdmin() {
		return typeAdmin;
	}

	public void setTypeAdmin(String typeAdmin) {
		this.typeAdmin = typeAdmin;
	}

	public List<Occupation> getTrashAdmins() {
		return trashAdmins;
	}

	public void setTrashAdmins(List<Occupation> trashAdmins) {
		this.trashAdmins = trashAdmins;
	}

	public List<Academic> completeCoordinators(String query) {
		return manageOccupationsService.findAcademicByNameEmail(query);
	}

	public PersistentObjectConverterFromId<Academic> getCoordinatorConverter() {
		return manageOccupationsService.getAcademicConverter();
	}

	public String administrators() {
		setListAdmins(manageOccupationsService.findOccupationsByPPG(selectedEntity.getId()));
		this.trashAdmins = new ArrayList<Occupation>();
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

		Occupation newOccupation = new Occupation();
		newOccupation.setAcademic(selectedAdmin);
		if (typeAdmin.equals("coordinator")) {
			newOccupation.setSecretary(false);
			newOccupation.setCoordinator(true);
		} else if (typeAdmin.equals("secretary")) {
			newOccupation.setSecretary(true);
			newOccupation.setCoordinator(false);
		}
		newOccupation.setDoctoral_supervisor(false);
		newOccupation.setMember(true);
		newOccupation.setPpg(selectedEntity);
		manageOccupationsService.create(newOccupation);

		setListAdmins(manageOccupationsService.findOccupationsByPPG(selectedEntity.getId()));

		return VIEW_PATH + "formadministrators.xhtml" + "?faces-redirect=" + getFacesRedirect();
	}

	public PersistentObjectConverterFromId<Role> getRoleConverter() {
		return manageAcademicsService.getRoleConverter();
	}

	public void deletePPG() {
		for (PPG delete : trashCan) {
			List<Occupation> list = manageOccupationsService.findOccupationsByPPG(delete.getId());
			for (Occupation occupation : list) {
				manageOccupationsService.delete(occupation);
			}

		}
		delete();
	}

	public String setAdminType(Long id, String type) {
		Occupation occupation = manageOccupationsService.retrieve(id);

		if (occupation != null) {
			if (type.equals("coordinator")) {
				occupation.setSecretary(false);
				occupation.setCoordinator(true);
			} else if (type.equals("secretary")) {
				occupation.setSecretary(true);
				occupation.setCoordinator(false);
			}
			manageOccupationsService.update(occupation);
		}

		setListAdmins(manageOccupationsService.findOccupationsByPPG(selectedEntity.getId()));

		return VIEW_PATH + "formadministrators.xhtml" + "?faces-redirect=" + getFacesRedirect();
	}

	public void trashAdmin(Long id) {
		Occupation occupation = manageOccupationsService.retrieve(id);

		if (occupation == null) {
			logger.log(Level.WARNING, "Method trashAdmin() called, but cant find occupation with id: {0}!", id);
			return;
		}

		trashAdmins.add(occupation);

	}

	public void cancelDeletionAdmin() {
		// Removes all entities from the trash and cancel their deletion.
		logger.log(Level.INFO, "Deletion has been cancelled. Clearing trash can");
		trashAdmins.clear();
	}

	public void deleteAdminPPG() {
		try {
			managePPGsService.validateAdminPPGDelete(selectedEntity, trashAdmins);

			for (Occupation delete : trashAdmins) {
				manageOccupationsService.delete(delete);
			}

			logger.log(Level.INFO, "Deletion of PPG admins was done successfully");
			trashAdmins.clear();

		} catch (CrudException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Create PPG requested", e.getMessage());
			context.addMessage(null, message);
		}
	}
}
