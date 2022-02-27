package br.ufes.informatica.marvin.core.application;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.core.domain.Role;
import br.ufes.informatica.marvin.core.persistence.RoleDAO;

@Stateless
@PermitAll
public class ManageRolesServiceBean extends CrudServiceBean<Role> implements ManageRolesService {
	private static final long serialVersionUID = 1L;

	@EJB
	private RoleDAO roleDAO;

	@Override
	public BaseDAO<Role> getDAO() {
		return roleDAO;
	}

	public List<String> getRoleForRule() {
		List<String> obj = new ArrayList<String>();
		List<Role> master = roleDAO.findByName("Master");
		List<Role> doctoral = roleDAO.findByName("Doctoral");

		for (Role tmp : master) {
			obj.add(tmp.getName());
		}

		for (Role tmp : doctoral) {
			obj.add(tmp.getName());
		}

		return obj;
	}

	public Role findFirstByName(String role) {
		return roleDAO.findByName(role).get(0);
	}

}
