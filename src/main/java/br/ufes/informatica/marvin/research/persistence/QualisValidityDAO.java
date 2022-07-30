package br.ufes.informatica.marvin.research.persistence;

import java.util.Date;

import javax.ejb.Local;
import javax.persistence.NoResultException;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.research.domain.QualisValidity;

@Local
public interface QualisValidityDAO extends BaseDAO<QualisValidity> {

	QualisValidity retriveByDates(Date dtStart, Date dtEnd)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException, NoResultException;

}
