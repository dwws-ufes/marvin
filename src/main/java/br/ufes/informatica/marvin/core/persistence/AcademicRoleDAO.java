package br.ufes.informatica.marvin.core.persistence;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.core.domain.AcademicRole;

/**
 * Interface for a DAO for objects of the Academic Role domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any) are specified in this class.
 * 
 * @author Gabriel Martins Miranda (garielmartinsmiranda@gmail.com)
 * @version 1.0
 */
@Local
public interface AcademicRoleDAO extends BaseDAO<AcademicRole> {

	AcademicRole retrieveByName(String name) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;

	List<AcademicRole> findByName(String name);

}
