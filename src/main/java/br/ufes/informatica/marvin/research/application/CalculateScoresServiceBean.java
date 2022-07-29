package br.ufes.informatica.marvin.research.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.core.domain.Occupation;
import br.ufes.informatica.marvin.core.persistence.OccupationDAO;
import br.ufes.informatica.marvin.research.domain.Qualis;
import br.ufes.informatica.marvin.research.domain.Rule;
import br.ufes.informatica.marvin.research.domain.Score;

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

	public List<Score> calculate(List<Occupation> academics, List<Rule> rule, Map<String, Qualis> qualis) {
		List<Score> score = new ArrayList<Score>();

		for (Occupation academic : academics) {

		}

		return score;
	}

}
