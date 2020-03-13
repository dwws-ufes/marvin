package br.ufes.informatica.marvin.core.application;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.persistence.AcademicDAO;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Stateless
public class EditProfileServiceBean implements EditProfileService {
	/** TODO: document this field. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(EditProfileServiceBean.class.getCanonicalName());

	/** TODO: document this field. */
	@EJB
	private AcademicDAO academicDAO;

	/** @see br.ufes.informatica.marvin.core.application.EditProfileService#update(br.ufes.informatica.marvin.core.domain.Academic) */
	@Override
	public Academic update(Academic academic) {
		logger.log(Level.INFO, "Persisting academic: {0}", new Object[] { academic });
		
		// Saves the object, then merges with the current session to return the updated object.
		academicDAO.save(academic);
		return academicDAO.merge(academic);
	}
}
