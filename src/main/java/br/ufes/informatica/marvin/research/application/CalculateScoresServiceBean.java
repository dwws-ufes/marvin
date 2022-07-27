package br.ufes.informatica.marvin.research.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.core.domain.Occupation;
import br.ufes.informatica.marvin.core.persistence.OccupationDAO;

/**
 * TODO: document this type.
 * 
 * FIXME: shouldn't we validate create to check if the e-mail is already in use?
 *
 * @author Rafael Franco (https://github.com/vitorsouza/)
 */
@Stateless
@PermitAll
public class CalculateScoresServiceBean extends CrudServiceBean<Occupation> implements CalculateScoresService {
	/** The unique identifier for a serializable class. */
	private static final long serialVersionUID = 1L;

	@EJB
	private OccupationDAO occupationDAO;

	@Override
	public BaseDAO<Occupation> getDAO() {
		return occupationDAO;
	}

}
