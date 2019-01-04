package br.ufes.informatica.marvin.core.application;

import java.io.Serializable;

import javax.ejb.Local;

import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.exceptions.LoginFailedException;

/**
 * Local EJB interface for the session information component. This bean provides an authentication method for the
 * controller, identifying users of the system.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Local
public interface LoginService extends Serializable {
	/**
	 * Obtains the currently logged in user.
	 * 
	 * @return The Academic object that represents the user that is currently logged in.
	 */
	Academic getCurrentUser();

	/**
	 * Authenticates a user given her username and password. If the user is correctly authenticated, she should be
	 * available as a Academic object through the getCurrentUser() method.
	 * 
	 * @param username
	 *          The username that identifies the user in the system.
	 * @param password
	 *          The password that authenticates the user.
	 * 
	 * @throws LoginFailedException
	 *           If the username is unknown or the password is incorrect.
	 */
	void login(String username, String password) throws LoginFailedException;
}
