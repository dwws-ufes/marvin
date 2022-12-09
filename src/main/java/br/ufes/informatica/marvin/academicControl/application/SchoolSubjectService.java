package br.ufes.informatica.marvin.academicControl.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.marvin.academicControl.domain.SchoolSubject;

@Local
public interface SchoolSubjectService extends CrudService<SchoolSubject> {

	List<SchoolSubject> retrieveSchoolSubjects();

	boolean codeAlreadyExists(SchoolSubject entity);

	boolean hasSubjectOffer(SchoolSubject schoolSubject);

}
