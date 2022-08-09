package br.ufes.informatica.marvin.academicControl.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.ClassTime;
import br.ufes.informatica.marvin.academicControl.persistence.ClassTimeDAO;

@Stateless
@PermitAll
public class ClassTimeServiceBean extends CrudServiceBean<ClassTime> implements ClassTimeService {
	private static final long serialVersionUID = 1L;

	@EJB
	private ClassTimeDAO classTimeDAO;

	@Override
	public BaseDAO<ClassTime> getDAO() {
		return classTimeDAO;
	}

}
