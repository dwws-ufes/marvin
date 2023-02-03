package br.ufes.informatica.marvin.academicControl.application;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudException;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.Deadline;
import br.ufes.informatica.marvin.academicControl.persistence.DeadlineDAO;
import br.ufes.informatica.marvin.utils.MarvinFunctions;

@Stateless
@PermitAll
public class DeadlineServiceBean extends CrudServiceBean<Deadline> implements DeadlineService {
	private static final long serialVersionUID = 1L;

	@EJB
	private DeadlineDAO deadlineDAO;

	@Override
	public BaseDAO<Deadline> getDAO() {
		return deadlineDAO;
	}

	@Override
	public List<Deadline> retrieveDeadline() {
		return deadlineDAO.retrieveDeadline();
	}

	@Override
	public void validateUpdate(Deadline deadline) throws CrudException {
		CrudException crudException = null;
		super.validateUpdate(deadline);
		Deadline deadLineBd = this.retrieve(deadline.getId());
		if (!deadLineBd.getDeadlineType().equals(deadline.getDeadlineType())) {
			crudException = addGlobalValidationError(crudException, null,
					"error.deadline.notPossibleChangeTypeDeadline");
		}
		MarvinFunctions.verifyAndThrowCrudException(crudException);
	}
}
