package br.ufes.informatica.marvin.academicControl.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.Solicitation;
import br.ufes.informatica.marvin.academicControl.persistence.SolicitationDAO;

@Stateless
@PermitAll
public class SolicitationServiceBean extends CrudServiceBean<Solicitation> implements SolicitationService {
	private static final long serialVersionUID = 1L;

	@EJB
	private SolicitationDAO solicitationDAO;

	@Override
	public BaseDAO<Solicitation> getDAO() {
		return solicitationDAO;
	}

}
