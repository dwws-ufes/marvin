package br.ufes.informatica.marvin.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
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
import br.ufes.informatica.marvin.core.application.ManagePpgsService;
import br.ufes.informatica.marvin.core.application.ManageRolesService;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Ppg;
import br.ufes.informatica.marvin.core.domain.Role;

@Named
@SessionScoped
public class ManagePpgsController extends CrudController<Ppg> {
	private static final long serialVersionUID = 1L;

	@EJB
	private ManagePpgsService managePpgsService;

	@EJB
	private ManageAcademicsService manageAcademicsService;

	@EJB
	private ManageRolesService manageRolesService;

	private Academic selectedAcademic;

	private Role selectedRole;

	private List<Role> roleList;

	@Override
	protected CrudService<Ppg> getCrudService() {
		return managePpgsService;
	}

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(ManagePpgsController.class.getCanonicalName());

	/**
	 * Path to the folder where the view files (web pages) for this action are
	 * placed.
	 */
	private static final String VIEW_PATH = "/admin/managePpgs/";

	@Override
	protected void initFilters() {
	}

	public Academic getSelectedAcademic() {
		return this.selectedAcademic;
	}

	public void setSelectedAcademic(Academic selectedAcademic) {
		this.selectedAcademic = selectedAcademic;
	}

	public Role getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(Role selectedRole) {
		this.selectedRole = selectedRole;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<Academic> completeAcademic(String query) {
		return manageAcademicsService.findByNameEmail(query);
	}

	public String administrators() {
		String[] roles = { "Secretary", "Coordinator" };
		this.roleList = new ArrayList<Role>();
		for (String role : roles) {
			this.roleList.add(manageRolesService.findFirstByName(role));
		}
		setSelectedAcademic(null);
		return VIEW_PATH + "formadministrators.xhtml" + "?faces-redirect=" + getFacesRedirect();
	}

	public String savePPG() throws CrudException {
		managePpgsService.validateCreate(selectedEntity, selectedAcademic);
		managePpgsService.create(selectedEntity);

		Academic academic = manageAcademicsService.retrieve(this.selectedAcademic.getId());
		Role role = manageRolesService.findFirstByName("Coordinator");

		manageAcademicsService.savePpgAdminstrator(academic, role, selectedEntity);
		setSelectedAcademic(null);
		return list();
	}

	public String saveAdministrator() {
		Academic academic = manageAcademicsService.retrieve(this.selectedAcademic.getId());
		Role role = manageRolesService.retrieve(this.selectedRole.getId());
		Ppg ppg = managePpgsService.retrieve(selectedEntity.getId());

		manageAcademicsService.savePpgAdminstrator(academic, role, ppg);

		return VIEW_PATH + "index.xhtml" + "?faces-redirect=" + getFacesRedirect();
	}

	public PersistentObjectConverterFromId<Academic> getAcademicConverter() {
		return manageAcademicsService.getAcademicConverter();
	}

	public PersistentObjectConverterFromId<Role> getRoleConverter() {
		return manageAcademicsService.getRoleConverter();
	}

	public void deletePPG() {
		for (Ppg delete : trashCan) {
			Criterion cri = new Criterion("ppg", CriterionType.EQUALS, delete);
			Filter<Void> filter = new SimpleFilter("ppg", "ppg", "ppg", cri);
			int[] inteval = { 0, 100 };
			List<Academic> list = manageAcademicsService.filter(filter, "ppg", inteval);

			for (Academic academic : list) {
				academic.setPpg(null);
			}
		}
		delete();
	}
}
