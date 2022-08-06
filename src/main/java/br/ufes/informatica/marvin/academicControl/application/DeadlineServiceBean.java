package br.ufes.informatica.marvin.academicControl.application;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.Deadline;
import br.ufes.informatica.marvin.academicControl.persistence.DeadlineDAO;

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
}
