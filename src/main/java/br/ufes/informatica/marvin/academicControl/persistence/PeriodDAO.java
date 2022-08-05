package br.ufes.informatica.marvin.academicControl.persistence;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.Period;

@Local
public interface PeriodDAO extends BaseDAO<Period> {
	List<Period> retrievePeriods();

}
