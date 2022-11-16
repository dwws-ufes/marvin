package br.ufes.informatica.marvin.academicControl.controller;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.ObjectUtils;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.academicControl.application.ListProfessorsService;
import br.ufes.informatica.marvin.academicControl.application.PeriodService;
import br.ufes.informatica.marvin.academicControl.application.SchoolSubjectService;
import br.ufes.informatica.marvin.academicControl.application.SubjectOfferService;
import br.ufes.informatica.marvin.academicControl.domain.ClassTime;
import br.ufes.informatica.marvin.academicControl.domain.Period;
import br.ufes.informatica.marvin.academicControl.domain.SchoolSubject;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;
import br.ufes.informatica.marvin.academicControl.enums.EnumWeekDays;
import br.ufes.informatica.marvin.core.application.LoginService;
import br.ufes.informatica.marvin.core.domain.Academic;

@Named
@SessionScoped
public class SubjectOfferController extends CrudController<SubjectOffer> {
	private static final long serialVersionUID = 1L;

	private static final String VIEW_PATH = "/academicControl/subjectOffer/";

	@EJB
	private SubjectOfferService subjectOfferService;

	@EJB
	private SchoolSubjectService schoolSubjectService;

	@EJB
	private PeriodService periodService;

	@EJB
	private ListProfessorsService listProfessorsService;

	@EJB
	private LoginService loginService;

	private List<SchoolSubject> schoolSubjects;

	private List<Period> periods;

	private List<Academic> professors;

	private ClassTime classTime;

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

	public String inputSchedules() throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		classTime = new ClassTime();
		subjectOfferService.prePopulateSchoolSubjectOffer(this.selectedEntity);
		return VIEW_PATH + "createClassTime.xhtml?faces-redirect=true";
	}

	public ClassTime getClassTime() {
		return classTime;
	}

	public void setClassTime(ClassTime classTime) {
		this.classTime = classTime;
	}

	public EnumWeekDays[] getValues() {
		return EnumWeekDays.values();
	}

	private boolean isBetween(LocalTime dateBetween, LocalTime dateIni, LocalTime dateFinal) {
		if (!dateBetween.equals(dateIni) && !dateBetween.equals(dateFinal))
			if (dateBetween.isAfter(dateIni) && dateBetween.isBefore(dateFinal))
				return true;
		return false;
	}

	private boolean isBeforeAndAfter(LocalTime dateI, LocalTime dateF, LocalTime dateIni, LocalTime dateFinal) {
		if (dateI.isBefore(dateIni) && dateF.isAfter(dateFinal))
			return true;
		return false;
	}

	private boolean existSchedulesConflits(ClassTime classTime) {
		Long qtdConflits = this.selectedEntity.getClassTime()//
				.stream()//
				.filter(c -> c.getWeekDay().equals(classTime.getWeekDay()))//
				.filter(c -> isBetween(classTime.getStartTime(), c.getStartTime(), c.getEndTime()) //
						|| isBetween(classTime.getEndTime(), c.getStartTime(), c.getEndTime()) //
						|| isBeforeAndAfter(classTime.getStartTime(), classTime.getEndTime(), c.getStartTime(),
								c.getEndTime()))
				.count();
		return qtdConflits > 0L;
	}

	public void addClassTime() {
		if (ObjectUtils.allNotNull(classTime.getWeekDay(), classTime.getEndTime(), classTime.getStartTime())) {
			Long qtdEqualsClassTime = this.selectedEntity.getClassTime()//
					.stream()//
					.filter(c -> c.getWeekDay().equals(classTime.getWeekDay()))//
					.filter(c -> c.getStartTime().equals(classTime.getStartTime()))//
					.filter(c -> c.getEndTime().equals(classTime.getEndTime()))//
					.count();

			if (qtdEqualsClassTime.equals(0L) && !existSchedulesConflits(classTime))
				this.selectedEntity.getClassTime().add(classTime);
			classTime = new ClassTime();
		}
	}

	public String generateSubjectOffer() {
		subjectOfferService.saveSubjectOffer(this.selectedEntity);
		return list();
	}

	public String cleanClassTime() {
		classTime = new ClassTime();
		this.selectedEntity.setClassTime(new ArrayList<ClassTime>());
		return VIEW_PATH + "createClassTime.xhtml?faces-redirect=true";
	}

	public void setDefaultNumMaxStudents() {
		if (!this.selectedEntity.isPersistent())
			this.selectedEntity.setNumMaxStudents(40L);
	}

	public void setDefaultProfessor() {
		if (!this.selectedEntity.isPersistent())
			this.selectedEntity.setProfessor(loginService.getCurrentUser());
	}

}
