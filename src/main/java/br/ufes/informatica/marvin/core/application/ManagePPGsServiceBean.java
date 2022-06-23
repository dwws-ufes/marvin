package br.ufes.informatica.marvin.core.application;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudException;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Occupation;
import br.ufes.informatica.marvin.core.domain.PPG;
import br.ufes.informatica.marvin.core.persistence.OccupationDAO;
import br.ufes.informatica.marvin.core.persistence.PPGDAO;

@Stateless
@PermitAll
public class ManagePPGsServiceBean extends CrudServiceBean<PPG> implements ManagePPGsService {
	private static final long serialVersionUID = 1L;

	@EJB
	private PPGDAO ppgDAO;

	@EJB
	private OccupationDAO occupationDAO;

	@Override
	public BaseDAO<PPG> getDAO() {
		return ppgDAO;
	}

	public void validateCreate(PPG entity, List<Academic> coordinator) throws CrudException {

		if (entity == null) {
			throw new CrudException("Entity PPG is NULL", "ERROR", null);
		}
		if (entity.getName().trim().isEmpty()) {
			throw new CrudException("Name of PPG cannot be empty", "ERROR", null);
		}

		if (coordinator == null || coordinator.isEmpty()) {
			throw new CrudException("Cannot create PPG without coordinator", "ERROR", null);
		}

	}

	public void validateAdminPPGDelete(PPG entity, List<Occupation> delete) throws CrudException {

		if (entity == null) {
			throw new CrudException("Entity PPG is NULL", "ERROR", null);
		}

		if (delete == null || delete.size() == 0) {
			throw new CrudException("List of Admins PPG is empty", "ERROR", null);
		}

		try {
			List<Occupation> occupations = occupationDAO.retriveOccupationsByPPG(entity.getId());

			if (occupations == null || occupations.size() == 0) {
				throw new CrudException("List of Admins PPG (" + entity.getAcronym() + ") is empty", "ERROR", null);
			}

			occupations.removeAll(delete);
			occupations.removeIf(occupation -> occupation.isSecretary());

			if (occupations.size() == 0) {
				throw new CrudException("The PPG must have at least one coordinator", "ERROR", null);
			}

		} catch (PersistentObjectNotFoundException e) {
			throw new CrudException("Cannot retrieve admins of PPG (" + entity.getAcronym() + ")", "ERROR", null);
		}

	}
}
