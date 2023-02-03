package br.ufes.informatica.marvin.academicControl.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang3.ObjectUtils;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudException;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.controller.PersistentObjectConverterFromId;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.academicControl.domain.ClassTime;
import br.ufes.informatica.marvin.academicControl.domain.Period;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;
import br.ufes.informatica.marvin.academicControl.persistence.SubjectOfferDAO;
import br.ufes.informatica.marvin.utils.MarvinFunctions;

@Stateless
@PermitAll
public class SubjectOfferServiceBean extends CrudServiceBean<SubjectOffer> implements SubjectOfferService {
	private static final long serialVersionUID = 1L;

	@EJB
	private SubjectOfferDAO subjectOfferDAO;

	private PersistentObjectConverterFromId<SubjectOffer> subjectOfferConverter;

	private static final Logger logger = Logger.getLogger(SubjectOfferServiceBean.class.getCanonicalName());

	@Override
	public BaseDAO<SubjectOffer> getDAO() {
		return subjectOfferDAO;
	}

	@Override
	public List<SubjectOffer> retrieveSubjectsOffer() {
		return subjectOfferDAO.retrieveSubjectsOffer();
	}

	@Override
	public PersistentObjectConverterFromId<SubjectOffer> getSubjectOfferConverter() {
		if (Objects.isNull(subjectOfferConverter))
			subjectOfferConverter = new PersistentObjectConverterFromId<SubjectOffer>(subjectOfferDAO);
		return subjectOfferConverter;
	}

	@Override
	public SubjectOffer retrieveSubjectOfferById(Long id) {
		return subjectOfferDAO.retrieveSubjectOfferById(id);
	}

	@Override
	public void saveSubjectOffer(SubjectOffer subjectOffer) {
		subjectOfferDAO.save(subjectOffer);
	}

	@Override
	public void prePopulateSchoolSubjectOffer(SubjectOffer subjectOffer)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		if (Objects.isNull(subjectOffer.getClassTime())) {
			SubjectOffer lastOffered = subjectOfferDAO
					.retrieveLastSubjectOfferBySchoolSubject(subjectOffer.getSchoolSubject().getId());
			subjectOffer.setClassTime(MarvinFunctions.nvl(subjectOffer.getClassTime(), new ArrayList<ClassTime>()));
			if (ObjectUtils.allNotNull(subjectOffer, lastOffered)) {
				List<ClassTime> classTimes = lastOffered.getClassTime();
				if (Objects.nonNull(classTimes) && !classTimes.isEmpty()) {
					for (ClassTime classTime : classTimes) {
						ClassTime classTimeToadd = new ClassTime();
						classTimeToadd.setWeekDay(classTime.getWeekDay());
						classTimeToadd.setStartTime(classTime.getStartTime());
						classTimeToadd.setEndTime(classTime.getEndTime());
						subjectOffer.getClassTime().add(classTimeToadd);
					}
				}
			}
		}
	}

	@Override
	public int getCountSubjectOfferByPeriod(Period period) {
		return subjectOfferDAO.getCountSubjectOfferByPeriod(period);
	}

	@Override
	public void validateCreate(SubjectOffer subjectOffer) throws CrudException {
		CrudException crudException = null;

		try {
			prePopulateSchoolSubjectOffer(subjectOffer);
		} catch (Exception e) {
			logger.log(Level.INFO, "Error to pre populate school subject offer class times");
		}

		if (schoolSubjectWasOfferedInPeriod(subjectOffer)) {
			crudException = addGlobalValidationError(crudException, null, "error.subjectOffer.schoolSubjectWasOfered");
		} else if (Objects.isNull(subjectOffer.getClassTime()) || subjectOffer.getClassTime().isEmpty())
			crudException = addGlobalValidationError(crudException, null,
					"error.subjectOffer.notPosibleGenerateSchedules");
		MarvinFunctions.verifyAndThrowCrudException(crudException);
	}

	@Override
	public void validateUpdate(SubjectOffer subjectOffer) throws CrudException {
		CrudException crudException = null;
		if (subjectOffer.getPeriod().getEnrollmentFinalDate().before(MarvinFunctions.sysdate()))
			crudException = addGlobalValidationError(crudException, null, "error.subjectOffer.requestDateClosed");
		MarvinFunctions.verifyAndThrowCrudException(crudException);
	}

	@Override
	public void validateDelete(SubjectOffer subjectOffer) throws CrudException {
		CrudException crudException = null;
		if (subjectOffer.getPeriod().getEnrollmentStartDate().before(MarvinFunctions.sysdate()))
			crudException = addGlobalValidationError(crudException, null, "error.subjectOffer.requestDateStarted");
		if (hasStudentEnrolled(subjectOffer))
			crudException = addGlobalValidationError(crudException, null, "error.subjectOffer.hasStudentEnrolled");
		MarvinFunctions.verifyAndThrowCrudException(crudException);
	}

	@Override
	public boolean hasStudentEnrolled(SubjectOffer subjectOffer) {
		return subjectOfferDAO.hasStudentEnrolled(subjectOffer);
	}

	@Override
	public boolean schoolSubjectWasOfferedInPeriod(SubjectOffer subjectOffer) {
		return subjectOfferDAO.schoolSubjectWasOfferedInPeriod(subjectOffer);
	}

}
