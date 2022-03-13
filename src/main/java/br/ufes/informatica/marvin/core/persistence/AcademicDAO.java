package br.ufes.informatica.marvin.core.persistence;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Role;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Local
public interface AcademicDAO extends BaseDAO<Academic> {
	/**
	 * TODO: document this method.
	 * 
	 * @param email
	 * @return
	 * @throws PersistentObjectNotFoundException
	 * @throws MultiplePersistentObjectsFoundException
	 */
	Academic retrieveByEmail(String email)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;

	/**
	 * TODO: document this method.
	 * 
	 * @param lattesId
	 * @return
	 * @throws PersistentObjectNotFoundException
	 * @throws MultiplePersistentObjectsFoundException
	 */
	Academic retrieveByLattesId(Long lattesId)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;

	/**
	 * TODO: document this method.
	 * 
	 * @return
	 */
	List<Academic> retrieveResearchers();

	/**
	 * TODO: document this method.
	 * 
	 * @param passwordCode
	 * @return
	 * @throws PersistentObjectNotFoundException
	 * @throws MultiplePersistentObjectsFoundException
	 */
	Academic retrieveByPasswordCode(String passwordCode)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;

	/**
	 * TODO: document this method.
	 * 
	 * @param role
	 * @return
	 * @throws PersistentObjectNotFoundException
	 * @throws MultiplePersistentObjectsFoundException
	 */
	List<Academic> retrieveByRole(Role role)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;

	/**
	 * TODO: document this method.
	 * 
	 * @param search
	 * @return List<Academic>
	 * @throws PersistentObjectNotFoundException
	 * @throws MultiplePersistentObjectsFoundException
	 */
	List<Academic> retriveByNameEmail(String search) throws PersistentObjectNotFoundException;
}
