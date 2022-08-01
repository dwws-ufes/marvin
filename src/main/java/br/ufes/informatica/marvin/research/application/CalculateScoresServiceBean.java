package br.ufes.informatica.marvin.research.application;

import java.math.BigDecimal;
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
import javax.persistence.Tuple;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.core.domain.Occupation;
import br.ufes.informatica.marvin.core.persistence.OccupationDAO;
import br.ufes.informatica.marvin.research.domain.Qualis;
import br.ufes.informatica.marvin.research.domain.Rule;
import br.ufes.informatica.marvin.research.domain.Score;
import br.ufes.informatica.marvin.research.domain.Venue;
import br.ufes.informatica.marvin.research.domain.VenueCategory;
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

			Long academicId = academic.getAcademic().getId();
			String academicName = academic.getAcademic().getName();

			String type = activeRule.isDoctoral() ? "Doctoral" : "Master";

			try {
				List<Tuple> tupleDotted = qualisDAO.retriveQualisByAcademicPublic(academicId, year);

				BigDecimal totalDotted = new BigDecimal(0);
				BigDecimal journalDotted = new BigDecimal(0);
				BigDecimal restrictedDotted = new BigDecimal(0);
				BigDecimal journalRestrictedDotted = new BigDecimal(0);
				for (Tuple tuple : tupleDotted) {
					Venue venueDotted = (Venue) tuple.get(0);
					Qualis qualisDotted = (Qualis) tuple.get(1);

					VenueCategory category = venueDotted.getCategory();

					BigDecimal journalQualis = new BigDecimal(0);
					BigDecimal conferenceQualis = new BigDecimal(0);
					if (category.getName().equalsIgnoreCase("JOURNAL")) {
						journalQualis = BigDecimal.valueOf(qualisDotted.getScoreJournal());
					} else if (category.getName().equalsIgnoreCase("CONFERENCE")) {
						conferenceQualis = BigDecimal.valueOf(qualisDotted.getScoreConference());
					}
					totalDotted = totalDotted.add(journalQualis);
					totalDotted = totalDotted.add(conferenceQualis);
					journalDotted = journalDotted.add(journalQualis);

					if (qualisDotted.isRestrito()) {
						restrictedDotted = restrictedDotted.add(journalQualis);
						restrictedDotted = restrictedDotted.add(conferenceQualis);
						journalRestrictedDotted = journalRestrictedDotted.add(journalQualis);
					}

				}

				float total = totalDotted.floatValue();
				float journal = journalDotted.floatValue();
				float restricted = restrictedDotted.floatValue();
				float journalRestricted = journalRestrictedDotted.floatValue();

				boolean aproved = false;
				if (total >= activeRule.getTotal() && journal >= activeRule.getScoreJournal()
						&& restricted >= activeRule.getScoreRestricted()
						&& journalRestricted >= activeRule.getScoreJournalRestricted()) {
					aproved = true;
				}

				Score academicScore = new Score(academicName, type, total, journal, restricted, journalRestricted,
						aproved);

				score.add(academicScore);

			} catch (PersistentObjectNotFoundException | MultiplePersistentObjectsFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return score;
	}

}
