package br.ufes.informatica.marvin.research.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.marvin.core.application.ManageRolesService;
import br.ufes.informatica.marvin.core.domain.Role;
import br.ufes.informatica.marvin.research.application.ManageRulesService;
import br.ufes.informatica.marvin.research.domain.Rule;

@Named("manageRulesController")
@SessionScoped
public class ManageRulesController extends CrudController<Rule> {
	private static final long serialVersionUID = 1L;

	@EJB
	private ManageRulesService manageRulesService;

	@EJB
	private ManageRolesService manageRolesService;

	private List<String> roleList;

	private String selectedRole;

	@Override
	protected CrudService<Rule> getCrudService() {
		return manageRulesService;
	}

	@Override
	protected void initFilters() {

	}

	@PostConstruct
	public void init() {
		roleList = manageRolesService.getRoleForRule();
	}

	public List<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}

	public String getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(String selectedRole) {
		this.selectedRole = selectedRole;
	}

	public String saveRule() {
		Role role = manageRolesService.findFirstByName(selectedRole);
		selectedEntity.setRole(role);
		return this.save();
	}

}
