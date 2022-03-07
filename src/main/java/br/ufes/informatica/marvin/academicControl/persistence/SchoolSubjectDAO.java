package br.ufes.informatica.marvin.academicControl.persistence;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.SchoolSubject;

@Local
interface SchoolSubjectDAO extends BaseDAO<SchoolSubject> {

}
