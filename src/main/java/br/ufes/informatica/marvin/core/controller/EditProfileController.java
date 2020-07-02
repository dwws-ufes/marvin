package br.ufes.informatica.marvin.core.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.controller.JSFController;
import br.ufes.informatica.marvin.core.application.EditProfileService;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.exceptions.OperationFailedException;

/**
 * Controller for the "Edit Profile" use case.
 * 
 * This use case allows any Academic to edit their own profile information. Upon request, Marvin will show forms with
 * the user's information and by submitting new data back to Marvin it will be updated. Forms should be divided in the
 * following sections:
 * 
 * <ul>
 * <li>Authentication information: e-mail and password;</li>
 * <li>Basic information: name, short name, birth date, CPF, birth city and nationality;</li>
 * <li>Contact information: telephone numbers;</li>
 * <li>Academic information: Lattes ID.</li>
 * </ul>
 * 
 * Changes in e-mail address must be confirmed by sending a code to the new e-mail address and effecting the change only
 * after the Academic confirms the code.
 * 
 * This controller is session scoped, as it deals with the information from the authenticated Academic.
 *
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Named
@SessionScoped
public class EditProfileController extends JSFController {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Path to the folder where the view files (web pages) for this action are placed. */
	private static final String VIEW_PATH = "/core/editProfile/";

	/** The logger. */
	private static final Logger logger = Logger.getLogger(EditProfileController.class.getCanonicalName());

	/** The "Edit Profile" service. */
	@EJB
	private EditProfileService editProfileService;

	/** The session controller, with information on the authenticated user. */
	@Inject
	private SessionController sessionController;

	/** Input: the academic whose profile is being edited. */
	private Academic academic;
	
	/** Input: the password data whose profile is being edited. */
	private String password;
	private String confirmPassword;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/** Getter for academic. Returns the currently authenticated user. */
	public Academic getAcademic() {
		if (academic == null) academic = sessionController.getCurrentUser();
		return academic;
	}

	/**
	 * TODO: document this method.
	 */
	public void save() {
		// Updates the academic's data.
		logger.log(Level.INFO, "Saving academic: {0}", new Object[] { academic });
		academic = editProfileService.update(academic);
		
		// Shows a confirmation message.
		addGlobalI18nMessage("msgsCore", FacesMessage.SEVERITY_INFO, "editProfile.info.updateSuccess.summary", "editProfile.info.updateSuccess.detail");
	}
	
	public void savePassword() throws NoSuchAlgorithmException, UnsupportedEncodingException, OperationFailedException {
		
		logger.log(Level.INFO, "Saving password from academic: {0}", new Object[] { academic });
		
		if(checkPasswords()) {
			//Updates academic's password.
			academic = editProfileService.updatePassword(academic, password);
			// Shows a confirmation message.
			addGlobalI18nMessage("msgsCore", FacesMessage.SEVERITY_INFO, "editProfile.info.updateSuccess.summary", "editProfile.info.updateSuccess.detail");
		} else {
			addGlobalI18nMessage("msgsCore", FacesMessage.SEVERITY_ERROR, "editProfile.info.updateFail.summary", "editProfile.info.updatePasswordFail.detail");
		}
	}
	
	private boolean checkPasswords() {
		if (confirmPassword == null || password == null || (!confirmPassword.equals(password))) {
			logger.log(Level.INFO, "Password and repeated password are null or don't match!");
			return false;
		}
		return true;
	}
}
