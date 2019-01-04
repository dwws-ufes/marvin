package br.ufes.informatica.marvin.core.application;

import java.io.Serializable;

import javax.ejb.Local;

import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.MarvinConfiguration;
import br.ufes.informatica.marvin.core.exceptions.OperationFailedException;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Local
public interface InstallSystemService extends Serializable {
	/**
	 * TODO: document this method.
	 * 
	 * @param config
	 * @param admin
	 * @throws OperationFailedException
	 */
	void installSystem(MarvinConfiguration config, Academic admin) throws OperationFailedException;
}
