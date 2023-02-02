package br.ufes.informatica.marvin.academicControl.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.PersistentObjectConverterFromId;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.academicControl.domain.Period;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;

@Local
public interface SubjectOfferService extends CrudService<SubjectOffer> {
	List<SubjectOffer> retrieveSubjectsOffer();

	SubjectOffer retrieveSubjectOfferById(Long id);

	PersistentObjectConverterFromId<SubjectOffer> getSubjectOfferConverter();

	void prePopulateSchoolSubjectOffer(SubjectOffer subjectOffer)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;

	void saveSubjectOffer(SubjectOffer subjectOffer);

	int getCountSubjectOfferByPeriod(Period period);

	boolean hasStudentEnrolled(SubjectOffer subjectOffer);

	boolean schoolSubjectWasOfferedInPeriod(SubjectOffer subjectOffer);

}
