package br.ufes.informatica.marvin.research.application;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.research.controller.UploadLattesCVController;
import br.ufes.informatica.marvin.research.domain.Venue;
import br.ufes.informatica.marvin.research.persistence.VenueDAO;

/**
 * TODO: document this type.
 * 
 * FIXME: shouldn't we validate create to check if the e-mail is already in use?
 *
 * @author Rafael Franco (https://github.com/vitorsouza/)
 */
@Stateless
@RolesAllowed({"SysAdmin", "Professor"})
public class ManageVenuesServiceBean extends CrudServiceBean<Venue> implements ManageVenuesService {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;
  
  /** Logger for this class. */
  private static final Logger logger =
      Logger.getLogger(UploadLattesCVController.class.getCanonicalName());

  @EJB
	private VenueDAO venueDAO;
	
	@Override
	public BaseDAO<Venue> getDAO() {
			return venueDAO;
	}

	@Override
	public List<Venue> findVenueByName(String name) {
		return venueDAO.findByNameOrAcronym(name);
	}
	
	@Override
	public void uploadVenueCV(InputStream inputStream) throws Exception {
    // Parses the Lattes CV.

	    try (BufferedReader reader =
          new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
	        String line = reader.readLine();
	        while (line != null) {
	        	String[] info = line.split(";");
	        	Venue obj = new Venue(info[1],info[0]);
	        	
	        	if(info[0].equals("Journal")) {
	        		obj.setIssn(info[2]);
	        		logger.log(Level.SEVERE, "INSERT VENUE: {0}, {1}, {2}",
	  	  	  	          new Object[] {info[0], info[1], info[2]});
	        	} else {
	        		logger.log(Level.SEVERE, "INSERT VENUE: {0}, {1}",
	  	  	  	          new Object[] {info[0], info[1]});
	        	}
	        	super.create(obj);
	        	line = reader.readLine();
	        }
          
	    }
	    List<Venue> teste = venueDAO.retrieveAll();
	    String Abc = null;
	  }

}