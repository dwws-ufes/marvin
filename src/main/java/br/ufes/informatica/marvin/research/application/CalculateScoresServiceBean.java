package br.ufes.informatica.marvin.research.application;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.core.domain.Occupation;
import br.ufes.informatica.marvin.core.persistence.OccupationDAO;
import br.ufes.informatica.marvin.research.domain.Qualis;
import br.ufes.informatica.marvin.research.domain.Rule;
import br.ufes.informatica.marvin.research.domain.Score;
import br.ufes.informatica.marvin.research.persistence.QualisDAO;

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

	@EJB
	private QualisDAO qualisDAO;

	@Override
	public BaseDAO<Occupation> getDAO() {
		return occupationDAO;
	}

	public List<Score> calculate(List<Occupation> academics, List<Rule> rule) {
		List<Score> score = new ArrayList<Score>();
		Map<String, Rule> mapRule = new HashMap<String, Rule>();

		Date today = new Date();
		SimpleDateFormat yearsFormat = new SimpleDateFormat("yyyy");

		for (Rule elem : rule) {
			String key = elem.isDoctoral() ? "doctoral" : "master";
			mapRule.put(key, elem);
		}

		for (Occupation academic : academics) {
			Rule activeRule = academic.isDoctoral_supervisor() ? mapRule.get("doctoral") : mapRule.get("master");
			int qtdPassYears = activeRule.getQtdPassYears();

			Calendar c = Calendar.getInstance();
			c.setTime(today);

			c.add(Calendar.YEAR, -qtdPassYears);
			Date currentDate = c.getTime();

			int year = Integer.parseInt(yearsFormat.format(currentDate));

			try {
				List<Qualis> qualis = qualisDAO.retriveQualisByAcademicPublic(academic.getId(), year);
			} catch (PersistentObjectNotFoundException | MultiplePersistentObjectsFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			year = Integer.parseInt(yearsFormat.format(currentDate));
		}

		return score;
	}

}
