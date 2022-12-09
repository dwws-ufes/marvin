package br.ufes.informatica.marvin.academicControl.persistence;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.SchoolSubject;

@Local
public interface SchoolSubjectDAO extends BaseDAO<SchoolSubject> {

	List<SchoolSubject> retrieveSchoolSubjects();

	SchoolSubject retrieveSubjectById(Long id);

	boolean codeAlreadyExists(SchoolSubject schoolSubject);

	boolean hasSubjectOffer(SchoolSubject schoolSubject);

}
