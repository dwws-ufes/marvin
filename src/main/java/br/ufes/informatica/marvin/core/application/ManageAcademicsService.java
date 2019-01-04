package br.ufes.informatica.marvin.core.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.PersistentObjectConverterFromId;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.AcademicRole;
import br.ufes.informatica.marvin.core.domain.Role;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
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

	PersistentObjectConverterFromId<AcademicRole> getAcademicRoleConverter();

	List<AcademicRole> findAcademicRoleByName(String name);
}
