package br.ufes.informatica.marvin.academicControl.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.marvin.academicControl.application.PeriodService;
import br.ufes.informatica.marvin.academicControl.domain.Period;

@Named
@SessionScoped
public class PeriodController extends CrudController<Period> {
	private static final long serialVersionUID = 1L;

	@EJB
	private PeriodService periodService;

	@Override
	protected CrudService<Period> getCrudService() {
		return periodService;
	}

	@Override
	protected void initFilters() {
	}

}
