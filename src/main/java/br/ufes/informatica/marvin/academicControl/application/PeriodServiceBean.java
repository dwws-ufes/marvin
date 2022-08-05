package br.ufes.informatica.marvin.academicControl.application;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.Period;
import br.ufes.informatica.marvin.academicControl.persistence.PeriodDAO;

@Stateless
@PermitAll
public class PeriodServiceBean extends CrudServiceBean<Period> implements PeriodService {
	private static final long serialVersionUID = 1L;

	@EJB
	private PeriodDAO periodDAO;

	@Override
	public BaseDAO<Period> getDAO() {
		return periodDAO;
	}

	@Override
	public List<Period> retrievePeriods() {
		return periodDAO.retrievePeriods();
	}

}
