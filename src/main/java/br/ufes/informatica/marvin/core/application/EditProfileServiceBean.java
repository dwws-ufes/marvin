package br.ufes.informatica.marvin.core.application;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.TextUtils;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.exceptions.OperationFailedException;
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
	
	public Academic updatePassword(Academic academic, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException, OperationFailedException {
		
		try {
		// Sets a new password to academic
		academic.setPassword(TextUtils.produceBase64EncodedMd5Hash(password));
		// Saves the academic.
		academicDAO.save(academic);
		
		return academicDAO.merge(academic);
		}
		catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// Logs and rethrows the exception for the controller to display the error to the user.
			logger.log(Level.SEVERE, "Could not find MD5 algorithm for password encription!", e);
			throw new OperationFailedException(e);
		}
	}
}
