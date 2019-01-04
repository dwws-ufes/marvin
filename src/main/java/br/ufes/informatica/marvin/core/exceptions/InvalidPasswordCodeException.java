package br.ufes.informatica.marvin.core.exceptions;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
public class InvalidPasswordCodeException extends Exception {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** TODO: document this field. */
	private String passwordCode;

	/** Constructor. */
	public InvalidPasswordCodeException(String passwordCode) {
		this.passwordCode = passwordCode;
	}

	/** Constructor. */
	public InvalidPasswordCodeException(Throwable cause, String passwordCode) {
		super(cause);
		this.passwordCode = passwordCode;
	}

	/** Getter for passwordCode. */
	public String getPasswordCode() {
		return passwordCode;
	}
}
