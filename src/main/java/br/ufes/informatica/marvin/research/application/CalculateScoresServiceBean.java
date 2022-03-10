package br.ufes.informatica.marvin.research.application;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Role;
import br.ufes.informatica.marvin.core.persistence.AcademicDAO;
import br.ufes.informatica.marvin.core.persistence.RoleDAO;

/**
 * TODO: document this type.
 * 
 * FIXME: shouldn't we validate create to check if the e-mail is already in use?
 *
 * @author Rafael Franco (https://github.com/vitorsouza/)
 */
@Stateless
@RolesAllowed({ "SysAdmin", "Professor" })
public class CalculateScoresServiceBean extends CrudServiceBean<Academic> implements CalculateScoresService {
	/** The unique identifier for a serializable class. */
	private static final long serialVersionUID = 1L;

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(CalculateScoresServiceBean.class.getCanonicalName());

	@EJB
	private AcademicDAO academicDAO;

	@EJB
	private RoleDAO roleDAO;

	public List<Academic> findAcademicQualified()
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		List<Academic> result = new ArrayList<Academic>();
		Role doctoral = roleDAO.retrieveByName("Doctoral");
		Role master = roleDAO.retrieveByName("Master");
		result.addAll(academicDAO.retrieveByRole(doctoral));
		result.addAll(academicDAO.retrieveByRole(master));
		return result;
	}

	@Override
	public BaseDAO<Academic> getDAO() {
		return academicDAO;
	}

}
