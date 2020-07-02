package br.ufes.informatica.marvin.core.exceptions;

/**
 * Exception thrown by service classes when there is an attempt to register an academic with an
 * e-mail address already in use by an existing academic.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
public class EmailAlreadyInUseException extends Exception {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** The e-mail that is already in use. */
  private String email;

  /** Constructor. */
  public EmailAlreadyInUseException(String email) {
    this.email = email;
  }

  /** Getter for email. */
  public String getEmail() {
    return email;
  }
}
