package br.ufes.informatica.marvin.academicControl.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.marvin.academicControl.application.SchoolSubjectService;
import br.ufes.informatica.marvin.academicControl.domain.SchoolSubject;

@Named
@SessionScoped
public class SchoolSubjectController extends CrudController<SchoolSubject> {
	private static final long serialVersionUID = 1L;

	@EJB
	private SchoolSubjectService schoolSubjectService;

	@Override
	protected CrudService<SchoolSubject> getCrudService() {
		return schoolSubjectService;
	}

	@Override
	protected void initFilters() {
	}

}
