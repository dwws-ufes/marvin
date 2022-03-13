package br.ufes.informatica.marvin.core.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.PersistentObjectConverterFromId;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Ppg;
import br.ufes.informatica.marvin.core.domain.Role;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Local
public interface ManageAcademicsService extends CrudService<Academic> {
	/**
	 * TODO: document this method.
	 * 
	 * @return
	 */
	PersistentObjectConverterFromId<Role> getRoleConverter();

	/**
	 * TODO: document this method.
	 * 
	 * @param name
	 * @return
	 */
	List<Role> findRoleByName(String name);

	/**
	 * TODO: document this method.
	 * 
	 * @param search
	 * @return
	 */
	List<Academic> findByNameEmail(String search);

	/**
	 * TODO: document this method.
	 * 
	 * 
	 * @return
	 */
	PersistentObjectConverterFromId<Academic> getAcademicConverter();

	/**
	 * TODO: document this method.
	 * 
	 * 
	 * @return
	 */
	public void savePpgAdminstrator(Academic academic, Role role, Ppg ppg);
}
