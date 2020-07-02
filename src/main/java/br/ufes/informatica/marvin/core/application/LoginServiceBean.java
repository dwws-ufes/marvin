package br.ufes.informatica.marvin.core.application;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import br.ufes.inf.nemo.jbutler.TextUtils;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.exceptions.LoginFailedException;
import br.ufes.informatica.marvin.core.persistence.AcademicDAO;

/**
 * Stateless session bean implementing the login service. See the implemented interface
 * documentation for details.
 * 
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Stateless
public class LoginServiceBean implements LoginService {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** Logger for this class. */
  private static final Logger logger = Logger.getLogger(LoginServiceBean.class.getCanonicalName());

  /** TODO: document this field. */
  @EJB
  private AcademicDAO academicDAO;

  /** TODO: document this field. */
  @Inject
  private Event<LoginEvent> loginEvent;

  /** TODO: document this field. */
  @Resource
  private SessionContext sessionContext;

  @Override
  public void login(String username, String password) throws LoginFailedException {
    try {
      // Obtains the user given the e-mail address (that serves as username).
      logger.log(Level.FINER, "Authenticating user with username \"{0}\"...", username);
      Academic user = academicDAO.retrieveByEmail(username);

      // Creates the MD5 hash of the password for comparison.
      String md5pwd = TextUtils.produceBase64EncodedMd5Hash(password);

      // Checks if the passwords match.
      String pwd = user.getPassword();
      if ((pwd != null) && (pwd.equals(md5pwd))) {
        logger.log(Level.FINEST, "Passwords match for user \"{0}\".", username);

        // Login successful.
        logger.log(Level.FINE, "Academic \"{0}\" successfully logged in.", username);
        Academic currentUser = user;
        pwd = null;

        // Registers the user login.
        Date now = new Date(System.currentTimeMillis());
        logger.log(Level.FINER,
            "Setting last login date for academic with username \"{0}\" as \"{1}\"...",
            new Object[] {currentUser.getEmail(), now});
        currentUser.setLastLoginDate(now);
        academicDAO.save(currentUser);

        // Fires a login event.
        loginEvent.fire(new LoginEvent(currentUser));
      } else {
        // Passwords don't match.
        logger.log(Level.INFO, "Academic \"{0}\" not logged in: password didn't match.", username);
        throw new LoginFailedException(LoginFailedException.LoginFailedReason.INCORRECT_PASSWORD);
      }
    } catch (PersistentObjectNotFoundException e) {
      // No academic was found with the given username.
      logger.log(Level.INFO,
          "User \"{0}\" not logged in: no registered academic found with given username.",
          username);
      throw new LoginFailedException(e, LoginFailedException.LoginFailedReason.UNKNOWN_USERNAME);
    } catch (MultiplePersistentObjectsFoundException e) {
      // Multiple academics were found with the same username.
      logger.log(Level.WARNING,
          "User \"{0}\" not logged in: there are more than one registered academic with the given username.",
          username);
      throw new LoginFailedException(e, LoginFailedException.LoginFailedReason.MULTIPLE_USERS);
    } catch (EJBTransactionRolledbackException e) {
      // Unknown original cause. Throw the EJB exception.
      logger.log(Level.WARNING, "User \"" + username + "\" not logged in: unknown cause.", e);
      throw e;
    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
      // No MD5 hash generation algorithm found by the JVM.
      logger.log(Level.SEVERE,
          "Logging in user \"" + username + "\" triggered an exception during MD5 hash generation.",
          e);
      throw new LoginFailedException(LoginFailedException.LoginFailedReason.MD5_ERROR);
    }
  }

  @Override
  public Academic getCurrentUser() {
    try {
      return academicDAO.retrieveByEmail(sessionContext.getCallerPrincipal().getName());
    } catch (PersistentObjectNotFoundException | MultiplePersistentObjectsFoundException e) {
      return null;
    }
  }
}
