package br.ufes.informatica.marvin.core.exceptions;

/**
 * Application exception that represents the fact that the user could not be authenticated.
 * 
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
public class LoginFailedException extends Exception {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** Reason for the failed login. */
  private LoginFailedReason reason;

  /** Constructor. */
  public LoginFailedException(LoginFailedReason reason) {
    this.reason = reason;
  }

  /** Constructor. */
  public LoginFailedException(Throwable t, LoginFailedReason reason) {
    super(t);
    this.reason = reason;
  }

  /** Getter for reason. */
  public LoginFailedReason getReason() {
    return reason;
  }

  /**
   * Enumeration of the reasons that a spiritist's authentication might fail.
   *
   * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
   */
  public enum LoginFailedReason {
    /**
     * The provided username is unknown, i.e., there's no user with the given username in the
     * database.
     */
    UNKNOWN_USERNAME("unknownUsername"),

    /**
     * The provided password is incorrect. The user with the given username has a different
     * password.
     */
    INCORRECT_PASSWORD("incorrectPassword"),

    /** Multiple users with the given username exist in the database, which is an inconsistency! */
    MULTIPLE_USERS("multipleUsers"),

    /**
     * The application could not produce the MD5 hash of the given password to match with the user's
     * password.
     */
    MD5_ERROR("md5Error"),

    /**
     * Marvin itself is OK with the authentication, but the Java EE container is not, responding
     * with an exception.
     */
    CONTAINER_REJECTED("containerRejected"),

    /**
     * Marvin could not check with the container if the authentication is OK because the HTTP
     * request doesn't exist.
     */
    INFRASTRUCTURE_PROBLEMS("infrastructureProblems");

    /** A unique identifier for the reason. */
    private String id;

    /** Constructor. */
    private LoginFailedReason(String id) {
      this.id = id;
    }

    @Override
    public String toString() {
      return id;
    }
  }
}
