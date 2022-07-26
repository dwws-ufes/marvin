package br.ufes.informatica.marvin.research.persistence;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.research.domain.Qualis;

@Local
public interface QualisDAO extends BaseDAO<Qualis> {

	List<Qualis> retrieveByQualisValidity(Long id) throws PersistentObjectNotFoundException;

	Qualis retriveByNameQualisValidity(String qualisName, Date dtStart, Date dtEnd, Long idPPG)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
}
