package br.ufes.informatica.marvin.academicControl.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.marvin.academicControl.application.SubjectOfferService;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;

@Named
@SessionScoped
public class SubjectOfferController extends CrudController<SubjectOffer> {
	private static final long serialVersionUID = 1L;

	@EJB
	private SubjectOfferService subjectOfferService;

	@Override
	protected CrudService<SubjectOffer> getCrudService() {
		return subjectOfferService;
	}

	@Override
	protected void initFilters() {
	}

}
