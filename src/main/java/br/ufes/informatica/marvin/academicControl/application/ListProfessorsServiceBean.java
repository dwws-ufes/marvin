package br.ufes.informatica.marvin.academicControl.application;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.persistence.AcademicDAO;

@Stateless
@RolesAllowed({ "SysAdmin", "Professor" })
public class ListProfessorsServiceBean implements ListProfessorsService {
	private static final long serialVersionUID = 1L;

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(ListProfessorsServiceBean.class.getCanonicalName());

	@EJB
	private AcademicDAO academicDAO;

	@Override
	public List<Academic> listProfessors() {
		logger.log(Level.INFO, "Listing Professors...");
		/* TODO Filter by ROLE Professor */
		List<Academic> professors = academicDAO.retrieveAll();
		Collections.sort(professors);
		return professors;
	}

}
