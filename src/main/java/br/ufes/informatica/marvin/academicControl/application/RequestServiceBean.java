package br.ufes.informatica.marvin.academicControl.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.Request;
import br.ufes.informatica.marvin.academicControl.persistence.RequestDAO;

@Stateless
@PermitAll
public class RequestServiceBean extends CrudServiceBean<Request> implements RequestService {
	private static final long serialVersionUID = 1L;

	@EJB
	private RequestDAO requestDAO;

	@Override
	public BaseDAO<Request> getDAO() {
		return requestDAO;
	}
}
