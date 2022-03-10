package br.ufes.informatica.marvin.core.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.core.domain.Ppg;
import br.ufes.informatica.marvin.core.persistence.PpgDAO;

@Stateless
@PermitAll
public class ManagePpgsServiceBean extends CrudServiceBean<Ppg> implements ManagePpgsService {
	private static final long serialVersionUID = 1L;

	@EJB
	private PpgDAO ppgDAO;

	@Override
	public BaseDAO<Ppg> getDAO() {
		return ppgDAO;
	}
}
