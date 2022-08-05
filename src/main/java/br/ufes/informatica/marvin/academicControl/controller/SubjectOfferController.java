package br.ufes.informatica.marvin.academicControl.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.academicControl.application.ListProfessorsService;
import br.ufes.informatica.marvin.academicControl.application.PeriodService;
import br.ufes.informatica.marvin.academicControl.application.SchoolSubjectService;
import br.ufes.informatica.marvin.academicControl.application.SubjectOfferService;
import br.ufes.informatica.marvin.academicControl.domain.Period;
import br.ufes.informatica.marvin.academicControl.domain.SchoolSubject;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;
import br.ufes.informatica.marvin.core.domain.Academic;

@Named
@SessionScoped
public class SubjectOfferController extends CrudController<SubjectOffer> {
	private static final long serialVersionUID = 1L;

	@EJB
	private SubjectOfferService subjectOfferService;

	@EJB
	private SchoolSubjectService schoolSubjectService;

	@EJB
	private PeriodService periodService;

	@EJB
	private ListProfessorsService listProfessorsService;

	private List<SchoolSubject> schoolSubjects;

	private List<Period> periods;

	private List<Academic> professors;

	@Inject
	public void init() throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		setSchoolSubjects(schoolSubjectService.retrieveSchoolSubjects());
		setPeriods(periodService.retrievePeriods());
		setProfessors(listProfessorsService.listProfessors());
	}

	@Override
	protected CrudService<SubjectOffer> getCrudService() {
		return subjectOfferService;
	}

	@Override
	protected void initFilters() {
	}

	public List<SchoolSubject> getSchoolSubjects() {
		return schoolSubjects;
	}

	public void setSchoolSubjects(List<SchoolSubject> schoolSubjects) {
		this.schoolSubjects = schoolSubjects;
	}

	public List<Academic> getProfessors() {
		return professors;
	}

	public void setProfessors(List<Academic> professors) {
		this.professors = professors;
	}

	public List<Period> getPeriods() {
		return periods;
	}

	public void setPeriods(List<Period> periods) {
		this.periods = periods;
	}

}
