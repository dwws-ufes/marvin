package br.ufes.informatica.marvin.core.exceptions;

/**
 * Application exception that represents the fact that the system installation process has failed to
 * complete.
 * 
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
public class OperationFailedException extends Exception {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** Constructor. */
  public OperationFailedException(Throwable t) {
    super(t);
  }
}
