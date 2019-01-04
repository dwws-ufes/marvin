package br.ufes.informatica.marvin.core.application;

import java.io.Serializable;

import javax.ejb.Local;

import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.exceptions.InvalidPasswordCodeException;
import br.ufes.informatica.marvin.core.exceptions.OperationFailedException;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Local
public interface ChangePasswordService extends Serializable {
	/**
	 * TODO: document this method.
	 * 
	 * @param passwordCode
	 * @return
	 * @throws InvalidPasswordCodeException
	 */
	Academic retrieveAcademicByPasswordCode(String passwordCode) throws InvalidPasswordCodeException;

	/**
	 * TODO: document this method.
	 * 
	 * @param passwordCode
	 * @param password
	 * @throws InvalidPasswordCodeException
	 * @throws OperationFailedException
	 */
	void setNewPassword(String passwordCode, String password) throws InvalidPasswordCodeException, OperationFailedException;

	/**
	 * TODO: document this method.
	 * 
	 * @param email
	 */
	void resetPassword(String email);
}
