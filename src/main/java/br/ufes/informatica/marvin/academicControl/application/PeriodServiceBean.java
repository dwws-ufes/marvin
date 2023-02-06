package br.ufes.informatica.marvin.academicControl.application;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.google.common.collect.Ordering;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudException;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.Period;
import br.ufes.informatica.marvin.academicControl.persistence.PeriodDAO;
import br.ufes.informatica.marvin.utils.MarvinFunctions;

@Stateless
@PermitAll
public class PeriodServiceBean extends CrudServiceBean<Period> implements PeriodService {
	private static final long serialVersionUID = 1L;

	@EJB
	private PeriodDAO periodDAO;

	@EJB
	private SubjectOfferService subjectOfferService;

	@Override
	public BaseDAO<Period> getDAO() {
		return periodDAO;
	}

	@Override
	public List<Period> retrievePeriods() {
		return periodDAO.retrievePeriods();
	}

	@Override
	public Period retriveActualPeriod() {
		return periodDAO.retriveActualPeriod();
	}

	@Override
	public Period retrivePeriodByName(String name) {
		return periodDAO.retrivePeriodByName(name);
	}

	private void validateCreateUpdate(Period period) throws CrudException {
		CrudException crudException = null;
		Period periodDB = retrivePeriodByName(period.getName());
		if (Objects.nonNull(periodDB) && (!period.isPersistent() || !period.getId().equals(periodDB.getId())))
			crudException = addGlobalValidationError(crudException, null, "error.period.alreadyExists",
					period.getName());
		if (period.getEnrollmentFinalDate().before(period.getEnrollmentStartDate()) || //
				period.getOfferFinalDate().before(period.getOfferStartDate()) || //
				period.getPeriodFinalDate().before(period.getPeriodStartDate()))
			crudException = addGlobalValidationError(crudException, null, "error.period.finalDateBeforeStartDate");

		List<Date> listOfDates = List.of(//
				period.getOfferStartDate(), //
				period.getOfferFinalDate(), //
				period.getEnrollmentStartDate(), //
				period.getEnrollmentFinalDate(), //
				period.getPeriodStartDate(), //
				period.getPeriodFinalDate());

		if (!Ordering.natural().isOrdered(listOfDates)) {
			crudException = addGlobalValidationError(crudException, null, "error.period.periodDatesOutOfOrder");
		}

		MarvinFunctions.verifyAndThrowCrudException(crudException);
	}

	@Override
	public void validateCreate(Period period) throws CrudException {
		super.validateCreate(period);
		validateCreateUpdate(period);
	}

	@Override
	public void validateUpdate(Period period) throws CrudException {
		super.validateUpdate(period);
		validateCreateUpdate(period);
	}

	@Override
	public void validateDelete(Period period) throws CrudException {
		super.validateDelete(period);
		CrudException crudException = null;
		if (subjectOfferService.getCountSubjectOfferByPeriod(period) > 0)
			crudException = addGlobalValidationError(crudException, null, "error.period.existSubjectOfferLinked");

		MarvinFunctions.verifyAndThrowCrudException(crudException);
	}

}
