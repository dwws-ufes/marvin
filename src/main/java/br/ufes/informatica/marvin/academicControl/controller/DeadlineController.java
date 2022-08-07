package br.ufes.informatica.marvin.academicControl.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.marvin.academicControl.application.DeadlineService;
import br.ufes.informatica.marvin.academicControl.domain.Deadline;

@Named
@SessionScoped
public class DeadlineController extends CrudController<Deadline> {
	private static final long serialVersionUID = 1L;

	@EJB
	private DeadlineService deadlineService;

	@Override
	protected CrudService<Deadline> getCrudService() {
		return deadlineService;
	}

	@Override
	protected void initFilters() {
	}

}
