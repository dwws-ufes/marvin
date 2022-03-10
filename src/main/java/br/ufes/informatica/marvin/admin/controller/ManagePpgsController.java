package br.ufes.informatica.marvin.admin.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
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
	private ManageAcademicsService manageAdacemicsService;

	@EJB
	private ManageRolesService manageRolesService;

	private Academic selectedAcademic;

	private Role selectedRole;

	private List<String> roleList;

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

	public void setSelectedacademic(Academic selectedAcademic) {
		this.selectedAcademic = selectedAcademic;
	}

	public Role getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(Role selectedRole) {
		this.selectedRole = selectedRole;
	}

	public List<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}

	public List<Academic> completeAcademic(String query) {
		return manageAdacemicsService.getDAO().retrieveAll();
	}

	public String administrators() {
		String[] roles = { "Secretary", "Coordinato" };
		this.roleList = manageRolesService.getStringRolesbyName(roles);
		return VIEW_PATH + "administrators.xhtml" + "?faces-redirect=" + getFacesRedirect();
	}
}
