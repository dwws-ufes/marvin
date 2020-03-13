package br.ufes.informatica.marvin.core.application;

import java.io.Serializable;

import javax.ejb.Local;

import br.ufes.informatica.marvin.core.domain.Academic;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Local
public interface EditProfileService extends Serializable {
	/**
	 * TODO: document this method.
	 * @param academic
	 * @return
	 */
	Academic update(Academic academic);
}
