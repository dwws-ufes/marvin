package br.ufes.informatica.marvin.core.persistence;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Occupation;

@Local
public interface OccupationDAO extends BaseDAO<Occupation> {
	/**
	 * TODO: document this method.
	 * 
	 * @param search
	 * @return List<Academic>
	 * @throws PersistentObjectNotFoundException
	 * @throws MultiplePersistentObjectsFoundException
	 */
	List<Academic> retriveByNameEmail(String search) throws PersistentObjectNotFoundException;

	Occupation retriveByAcademic(Long idAcademic)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;

	List<Occupation> retriveOccupationsByPPG(Long idPPG) throws PersistentObjectNotFoundException;
}
