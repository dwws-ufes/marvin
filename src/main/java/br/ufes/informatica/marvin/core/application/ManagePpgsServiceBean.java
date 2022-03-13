package br.ufes.informatica.marvin.core.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudException;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Ppg;
import br.ufes.informatica.marvin.core.persistence.AcademicDAO;
import br.ufes.informatica.marvin.core.persistence.PpgDAO;

@Stateless
@PermitAll
public class ManagePpgsServiceBean extends CrudServiceBean<Ppg> implements ManagePpgsService {
	private static final long serialVersionUID = 1L;

	@EJB
	private PpgDAO ppgDAO;

	@EJB
	private AcademicDAO academicDAO;

	@Override
	public BaseDAO<Ppg> getDAO() {
		return ppgDAO;
	}

	public void validateCreate(Ppg entity, Academic coordinator) throws CrudException {

		if (entity == null) {
			throw new CrudException("Entity PPG is NULL", "ERROR", null);
		}
		if (entity.getName().trim().isEmpty()) {
			throw new CrudException("Name of PPG cannot be empty", "ERROR", null);
		}

		if (coordinator == null) {
			throw new CrudException("Cannot create PPG withour coordinator", "ERROR", null);
		}

	}
}
