package br.ufes.informatica.marvin.research.application;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudException;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.core.domain.PPG;
import br.ufes.informatica.marvin.research.controller.UploadLattesCVController;
import br.ufes.informatica.marvin.research.domain.Qualis;
import br.ufes.informatica.marvin.research.domain.Venue;
import br.ufes.informatica.marvin.research.persistence.QualisDAO;
import br.ufes.informatica.marvin.research.persistence.VenueDAO;

/**
 * TODO: document this type.
 * 
 * FIXME: shouldn't we validate create to check if the e-mail is already in use?
 *
 * @author Rafael Franco (https://github.com/vitorsouza/)
 */
@Stateless
@RolesAllowed({ "SysAdmin", "Professor" })
public class ManageVenuesServiceBean extends CrudServiceBean<Venue> implements ManageVenuesService {
	/** The unique identifier for a serializable class. */
	private static final long serialVersionUID = 1L;

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(UploadLattesCVController.class.getCanonicalName());

	@EJB
	private VenueDAO venueDAO;

	@EJB
	private QualisDAO qualisDAO;

	@Override
	public BaseDAO<Venue> getDAO() {
		return venueDAO;
	}

	@Override
	public List<Venue> findVenueByName(String name) {
		return venueDAO.findByNameOrAcronym(name);
	}

	@Override
	public void uploadVenueCV(InputStream inputStream, Date dtStart, Date dtEnd, PPG ppg) throws Exception {
		// Parses the Lattes CV.

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
			String line = reader.readLine();
			while (line != null) {
				String[] info = new String[5];
				String[] infoSplit = line.split(";");

				for (int i = 0; i < infoSplit.length; i++) {
					info[i] = infoSplit[i].isEmpty() ? null : infoSplit[i];
				}

				Qualis qualis = qualisDAO.retriveByNameQualisValidity(info[4], dtStart, dtEnd, ppg.getId());

				if (qualis == null) {
					throw new CrudException("Could not find your qualis", "ERROR", null);
				}

				Venue obj = new Venue(info[3], info[2], info[1], info[0], dtStart, dtEnd, qualis, ppg);

				if (info[0].equals("Journal")) {
					logger.log(Level.SEVERE, "INSERT VENUE: {0}, {1}, {2}, {3}, {4}, {5}, {6}",
							new Object[] { info[3], info[2], info[1], info[0], dtStart, dtEnd });
				} else {
					logger.log(Level.SEVERE, "INSERT VENUE: {0}, {1}, {2}, {3}, {4}, {5}",
							new Object[] { info[3], info[2], info[1], info[0], dtStart, dtEnd });
				}
				super.create(obj);
				line = reader.readLine();
			}

		}
	}

}
