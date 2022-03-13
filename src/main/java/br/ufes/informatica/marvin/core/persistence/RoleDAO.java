package br.ufes.informatica.marvin.core.persistence;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.core.domain.Role;

/**
 * Interface for a DAO for objects of the Role domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are
 * inherited from the superclass, whereas operations that are specific to the
 * managed domain class (if any) are specified in this class.
 * 
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Local
public interface RoleDAO extends BaseDAO<Role> {
	/**
	 * TODO: document this method.
	 * 
	 * @param name
	 * @return
	 * @throws PersistentObjectNotFoundException
	 * @throws MultiplePersistentObjectsFoundException
	 */
	Role retrieveByName(String name) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;

	/**
	 * TODO: document this method.
	 * 
	 * @param name
	 * @return
	 */
	List<Role> findByName(String name);
}
