package br.ufes.informatica.marvin.core.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.controller.JSFController;
import br.ufes.informatica.marvin.core.application.ChangePasswordService;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.exceptions.InvalidPasswordCodeException;
import br.ufes.informatica.marvin.core.exceptions.OperationFailedException;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Named
@ConversationScoped
public class ChangePasswordController extends JSFController {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(ChangePasswordController.class.getCanonicalName());

	/** The JSF conversation. */
	@Inject
	private Conversation conversation;

	/** Information on the current visitor. */
	@Inject
	private SessionController sessionController;

	/** The "Change Password" service. */
	@EJB
	private ChangePasswordService changePasswordService;

	/** TODO: document this field. */
	private String passwordCode;

	/** TODO: document this field. */
	private boolean validCode;

	/** TODO: document this field. */
	private Academic academic;

	/** TODO: document this field. */
	private String password;

	/** TODO: document this field. */
	private String repeatPassword;

	/** TODO: document this field. */
	private String email;

	/** Getter for academic. */
	public Academic getAcademic() {
		return academic;
	}

	/** Getter for passwordCode. */
	public String getPasswordCode() {
		return passwordCode;
	}

	/** Setter for passwordCode. */
	public void setPasswordCode(String passwordCode) {
		this.passwordCode = passwordCode;
	}

	/** Getter for validCode. */
	public boolean isValidCode() {
		return validCode;
	}

	/** Getter for password. */
	public String getPassword() {
		return password;
	}

	/** Setter for password. */
	public void setPassword(String password) {
		this.password = password;
	}

	/** Getter for repeatPassword. */
	public String getRepeatPassword() {
		return repeatPassword;
	}

	/** Setter for repeatPassword. */
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	/** Getter for email. */
	public String getEmail() {
		return email;
	}

	/** Setter for email. */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * TODO: document this method.
	 */
	public void begin() {
		// Begins the conversation, dropping any previous conversation, if existing.
		logger.log(Level.INFO, "Beginning conversation. Current conversation transient? -> {0}", new Object[] { conversation.isTransient() });
		if (!conversation.isTransient()) conversation.end();
		conversation.begin();
	}

	/**
	 * TODO: document this method.
	 */
	public void checkCode() {
		logger.log(Level.INFO, "Checking code: {0}", new Object[] { passwordCode });
		
		// First checks if a password code was given at all, then checks if it's valid.
		validCode = passwordCode != null;
		if (validCode) try {
			// Attempts to find the academic with the given password code.
			academic = changePasswordService.retrieveAcademicByPasswordCode(passwordCode);

			// If all went well, begins the conversation.
			begin();
		}

		// If an academic could not be found, deem the code invalid.
		catch (InvalidPasswordCodeException e) {
			validCode = false;
		}
	}

	/**
	 * TODO: document this method.
	 */
	public void checkAuthenticatedUser() {
		logger.log(Level.FINEST, "Checking the authenticated user...");

		// Obtains the authenticated user.
		academic = sessionController.getCurrentUser();

		// If all went well, begins the conversation.
		begin();
	}

	/**
	 * Checks if both password fields have the same value.
	 * 
	 * This method is intended to be used with AJAX.
	 */
	public void ajaxCheckPasswords() {
		checkPasswords();
	}

	/**
	 * Checks if the contents of the password fields match.
	 * 
	 * @return <code>true</code> if the passwords match, <code>false</code> otherwise.
	 */
	private boolean checkPasswords() {
		if (repeatPassword == null || password == null || (! repeatPassword.equals(password))) {
			logger.log(Level.INFO, "Password and repeated password are null or don't match!");
			addGlobalI18nMessage("msgsCore", FacesMessage.SEVERITY_WARN, "changePassword.error.passwordsDontMatch.summary", "changePassword.error.passwordsDontMatch.detail");
			return false;
		}
		return true;
	}

	/**
	 * TODO: document this method.
	 * 
	 * @return
	 */
	public String setNewPassword() {
		logger.log(Level.INFO, "Setting new password for academic {0} (password code {1})", new Object[] { academic, passwordCode });

		// Checks if the passwords match.
		if (!checkPasswords()) return null;

		// Changes the password.
		try {
			changePasswordService.setNewPassword(passwordCode, password);
		}
		catch (OperationFailedException e) {
			logger.log(Level.SEVERE, "Change password threw exception", e);
			addGlobalI18nMessage("msgsCore", FacesMessage.SEVERITY_FATAL, "changePassword.error.operationFailed.summary", "changePassword.error.operationFailed.detail");
			return null;
		}

		// If the code is suddenly invalid, send the user back to start.
		catch (InvalidPasswordCodeException e) {
			validCode = false;
		}

		// Ends the conversation.
		logger.log(Level.FINEST, "Ending conversation. Current conversation transient? -> {0}", new Object[] { conversation.isTransient() });
		if (!conversation.isTransient()) conversation.end();

		// Redirects to the conclusion if everything went fine.
		if (validCode) return "done.xhtml?faces-redirect=true";
		return null;
	}

	/**
	 * TODO: document this method.
	 * 
	 * @return
	 */
	public void resetPassword() {
		// Resets the password and displays a message.
		changePasswordService.resetPassword(email);
		addGlobalI18nMessage("msgsCore", FacesMessage.SEVERITY_INFO, "changePassword.message.resetRequested.summary", new Object[] {}, "changePassword.message.resetRequested.detail", new Object[] { email });
	}

	public String changePassword() {
		logger.log(Level.FINEST, "Changing password for academic {0} (password code {1})", new Object[] { academic, passwordCode });

		// Checks if the passwords match.
		if (!checkPasswords()) return null;

		System.out.println("########### Change password of " + academic + " to: " + password);
		return null;
	}
}
