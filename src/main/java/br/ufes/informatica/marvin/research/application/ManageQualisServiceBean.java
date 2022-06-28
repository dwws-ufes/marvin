package br.ufes.informatica.marvin.research.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.research.domain.Qualis;
import br.ufes.informatica.marvin.research.persistence.QualisDAO;

@Stateless
@PermitAll
public class ManageQualisServiceBean extends CrudServiceBean<Qualis> implements ManageQualisService {
	private static final long serialVersionUID = 1L;

	@EJB
	private QualisDAO qualisDAO;

	@Override
	public BaseDAO<Qualis> getDAO() {
		return qualisDAO;
	}
}
