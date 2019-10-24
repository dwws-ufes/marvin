package br.ufes.informatica.marvin.core.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;

import br.ufes.inf.nemo.jbutler.ejb.controller.JSFController;
import br.ufes.informatica.marvin.core.application.ChangePasswordService;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.exceptions.InvalidPasswordCodeException;
import br.ufes.informatica.marvin.core.exceptions.OperationFailedException;

/**
 * Controller for the "Change Password" use case.
 * 
 * This use case allows any user to supply an e-mail address and ask Marvin to reset her password. Marvin then sends
 * an e-mail with a code embeded in a URL. When the user opens that URL, she comes back to Marvin to register her new
 * password.
 * 
 * This controller is conversation scoped, beginning the conversation in method checkCode() and ending in method end(),
 * which should be called explicitly by the web pages.
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

	/** The "Change Password" service. */
	@EJB
	private ChangePasswordService changePasswordService;

	/** Input: the e-mail supplied by the user to initiate the password changing process. */
	private String email;

	/** Input: the code the user receives in their e-mail in order to verify that they own the account. */
	private String passwordCode;

	/** Input: the new password to set. */
	private String password;

	/** Input: the password repeated in a different field, to make sure the user didn't make a mistake. */
	private String repeatPassword;

	/** Indicates if the code is valid (if it's the same code we have in our database). */
	private boolean validCode;

	/** The academic object associated with the given password code. */
	private Academic academic;

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
	 * Checks the code the academic received via e-mail (loaded as a parameter in the URL) and begins the password
	 * changing process.
	 */
	public void checkCode() {
		logger.log(Level.INFO, "Checking code: {0}", passwordCode);

		// First check if a password code was given at all, then check if it's valid.
		validCode = passwordCode != null;
		if (validCode) try {
			// Attempt to find the academic with the given password code.
			academic = changePasswordService.retrieveAcademicByPasswordCode(passwordCode);

			// If all went well, begin the conversation (if one hasn't begun already).
			logger.log(Level.INFO, "Beginning conversation. Current conversation transient? -> {0}", new Object[] { conversation.isTransient() });
			if (conversation.isTransient()) conversation.begin();
		}

		// If an academic could not be found, deem the code invalid.
		catch (InvalidPasswordCodeException e) {
			validCode = false;
		}
	}

	/**
	 * Ends the password changing process.
	 */
	public void end() {
		logger.log(Level.FINEST, "Ending conversation. Current conversation transient? -> {0}", new Object[] { conversation.isTransient() });

		// Ends the conversation.
		if (!conversation.isTransient()) conversation.end();
		else try {
			Faces.redirect(Faces.getRequestContextPath() + "/index.xhtml");
		}
		catch (IOException e) {
			logger.log(Level.WARNING, "Trying to end transient conversation usually means the user is reloading the page after the conversation has already ended. Tried to redirect back home, but failed.", e);
		}
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
		if (repeatPassword == null || password == null || (!repeatPassword.equals(password))) {
			logger.log(Level.INFO, "Password and repeated password are null or don't match!");
			addGlobalI18nMessage("msgsCore", FacesMessage.SEVERITY_WARN, "changePassword.error.passwordsDontMatch.summary", "changePassword.error.passwordsDontMatch.detail");
			return false;
		}
		return true;
	}

	/**
	 * Controller method that starts the password reset process by asking the service class to send the e-mail with the
	 * code to the given address.
	 */
	public void resetPassword() {
		// Request the password reset and display a message.
		changePasswordService.resetPassword(email);
		addGlobalI18nMessage("msgsCore", FacesMessage.SEVERITY_INFO, "changePassword.message.resetRequested.summary", new Object[] {}, "changePassword.message.resetRequested.detail", new Object[] { email });
	}

	/**
	 * Controller method that asks the service class to actually change the password after the code and the new password
	 * have been checked.
	 * 
	 * @return The path to the web page that shows that the operation completed successfully.
	 */
	public String setNewPassword() {
		logger.log(Level.INFO, "Setting new password for academic {0} (password code {1})", new Object[] { academic, passwordCode });

		// Check if the passwords match.
		if (!checkPasswords()) return null;

		// Change the password.
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

		// Redirect to the conclusion if everything went fine.
		if (validCode) return "done.xhtml?faces-redirect=true";
		return null;
	}
}
