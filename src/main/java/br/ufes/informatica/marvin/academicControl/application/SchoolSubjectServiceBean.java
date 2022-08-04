package br.ufes.informatica.marvin.academicControl.application;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.SchoolSubject;
import br.ufes.informatica.marvin.academicControl.persistence.SchoolSubjectDAO;

@Stateless
@PermitAll
public class SchoolSubjectServiceBean extends CrudServiceBean<SchoolSubject> implements SchoolSubjectService {
	private static final long serialVersionUID = 1L;

	@EJB
	private SchoolSubjectDAO schoolSubjectDAO;

	@Override
	public BaseDAO<SchoolSubject> getDAO() {
		return schoolSubjectDAO;
	}

	@Override
	public List<SchoolSubject> retrieveSchoolSubjects() {
		return schoolSubjectDAO.retrieveSchoolSubjects();
	}

}
