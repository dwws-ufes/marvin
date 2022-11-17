package br.ufes.informatica.marvin.academicControl.persistence;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.academicControl.domain.Period;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;

@Local
public interface SubjectOfferDAO extends BaseDAO<SubjectOffer> {
	List<SubjectOffer> retrieveSubjectsOffer();

	SubjectOffer retrieveSubjectOfferById(Long id);

	SubjectOffer retrieveLastSubjectOfferBySchoolSubject(Long schoolSubjectId)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;

	int getCountSubjectOfferByPeriod(Period period);
}
