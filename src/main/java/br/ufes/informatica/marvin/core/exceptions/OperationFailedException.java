package br.ufes.informatica.marvin.core.exceptions;

/**
 * Application exception that represents the fact that the system installation process has failed to complete.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
public class OperationFailedException extends Exception {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Constructor from superclass. */
	public OperationFailedException(Throwable t) {
		super(t);
	}
}
