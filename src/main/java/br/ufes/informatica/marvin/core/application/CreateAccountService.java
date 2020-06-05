package br.ufes.informatica.marvin.core.application;

import java.io.Serializable;

import javax.ejb.Local;

import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.exceptions.EmailAlreadyInUseException;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Local
public interface CreateAccountService extends Serializable {
	/**
	 * TODO: document this method.
	 * 
	 * @param entity
	 */
	void createAccount(Academic entity) throws EmailAlreadyInUseException;
}
