package br.ufes.informatica.marvin.academicControl.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.marvin.academicControl.domain.Period;

@Local
public interface PeriodService extends CrudService<Period> {
	List<Period> retrievePeriods();
}
