package br.ufes.informatica.marvin.core.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.marvin.core.domain.Role;

@Local
public interface ManageRolesService extends CrudService<Role> {

	public List<String> getRoleForRule();

	public Role findFirstByName(String role);
}
