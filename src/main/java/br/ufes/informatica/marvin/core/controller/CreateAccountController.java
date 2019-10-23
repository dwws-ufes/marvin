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
import br.ufes.informatica.marvin.core.application.CreateAccountService;
import br.ufes.informatica.marvin.core.application.exceptions.EmailAlreadyInUseException;
import br.ufes.informatica.marvin.core.domain.Academic;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Named
@ConversationScoped
public class CreateAccountController extends JSFController {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Path to the folder where the view files (web pages) for this action are placed. */
	private static final String VIEW_PATH = "/core/createAccount/";

	/** The logger. */
	private static final Logger logger = Logger.getLogger(CreateAccountController.class.getCanonicalName());

	/** The JSF conversation. */
	@Inject
	private Conversation conversation;

	/** The "Create Account" service. */
	@EJB
	private CreateAccountService createAccountService;

	/** Input: the academic being registered. */
	private Academic academic = new Academic();

	/** Input: the repeated password for the registration. */
	private String repeatPassword;

	/** Getter for repeatPassword. */
	public String getRepeatPassword() {
		return repeatPassword;
	}

	/** Setter for repeatPassword. */
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	/** Getter for academic. */
	public Academic getAcademic() {
		return academic;
	}

	/**
	 * Analyzes the name that was given to the administrator and, if the short name field is still empty, suggests a value
	 * for it based on the given name.
	 * 
	 * This method is intended to be used with AJAX.
	 */
	public void suggestShortName() {
		// If the name was filled and the short name is still empty, suggest the first name as short name.
		String name = academic.getName();
		String shortName = academic.getShortName();
		if ((name != null) && ((shortName == null) || (shortName.length() == 0))) {
			int idx = name.indexOf(" ");
			academic.setShortName((idx == -1) ? name : name.substring(0, idx).trim());
			logger.log(Level.FINE, "Suggested \"{0}\" as short name for \"{1}\"", new Object[] { academic.getShortName(), name });
		}
		else logger.log(Level.FINEST, "Short name not suggested: empty name or short name already filled (name is \"{0}\", short name is \"{1}\")", new Object[] { name, shortName });
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
		if (((repeatPassword != null) && (!repeatPassword.equals(academic.getPassword()))) || ((repeatPassword == null) && (academic.getPassword() != null))) {
			logger.log(Level.INFO, "Password and repeated password are not the same");
			addGlobalI18nMessage("msgsCore", FacesMessage.SEVERITY_WARN, "installSystem.error.passwordsDontMatch.summary", "installSystem.error.passwordsDontMatch.detail");
			return false;
		}
		return true;
	}

	/**
	 * Begins the registration process.
	 */
	public void begin() {
		logger.log(Level.FINEST, "Beginning conversation. Current conversation transient? -> {0}", new Object[] { conversation.isTransient() });

		// Begins the conversation, dropping any previous conversation, if existing.
		if (!conversation.isTransient()) conversation.end();
		conversation.begin();
	}

	/**
	 * Ends the registration process.
	 */
	public void end() {
		logger.log(Level.FINEST, "Ending conversation. Current conversation transient? -> {0}", new Object[] { conversation.isTransient() });

		// Ends the conversation.
		if (!conversation.isTransient()) conversation.end();
	}

	/**
	 * Registers the new academic.
	 * 
	 * @return The path to the web page that shows that the operation completed successfully.
	 */
	public String register() {
		logger.log(Level.FINEST, "Received input data:\n\t- academic.name = {0}\n\t- academic.email = {1}", new Object[] { academic.getName(), academic.getEmail() });

		// Check if passwords don't match. Add an error in that case.
		if (!checkPasswords()) return null;

		// Register the new academic, checking if the e-mail is already in use.
		try {
			createAccountService.createAccount(academic);
		}
		catch (EmailAlreadyInUseException e) {
			addGlobalI18nMessage("msgsCore", FacesMessage.SEVERITY_ERROR, "createAccount.error.emailAlreadyInUse.summary", new Object[] {}, "createAccount.error.emailAlreadyInUse.detail", new Object[] { academic.getEmail() });
			return null;
		}

		// Proceeds to the final view.
		return VIEW_PATH + "done.xhtml?faces-redirect=true";
	}
}
