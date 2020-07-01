package br.ufes.informatica.marvin.core.application;

import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;

import br.ufes.inf.nemo.jbutler.ResourceUtil;
import br.ufes.inf.nemo.jbutler.TextUtils;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.MarvinConfiguration;
import br.ufes.informatica.marvin.core.domain.Role;
import br.ufes.informatica.marvin.core.exceptions.OperationFailedException;
import br.ufes.informatica.marvin.core.persistence.AcademicDAO;
import br.ufes.informatica.marvin.core.persistence.MarvinConfigurationDAO;
import br.ufes.informatica.marvin.core.persistence.RoleDAO;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Stateless
public class InstallSystemServiceBean implements InstallSystemService {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** Logger for this class. */
  private static final Logger logger =
      Logger.getLogger(InstallSystemServiceBean.class.getCanonicalName());

  /**
   * The path to the folder that contains the data to be added to the database upon system
   * installation.
   */
  private static final String INIT_DATA_PATH = "marvin/installSystem/";

  /** The name of the file that contains the roles to be added upon system installation. */
  private static final String INIT_DATA_ROLE_FILE_NAME = "Role.json";

  /** The DAO for Academic objects. */
  @EJB
  private AcademicDAO academicDAO;

  /** The DAO for MarvinConfiguration objects. */
  @EJB
  private MarvinConfigurationDAO marvinConfigurationDAO;

  /** The DAO for Role objects. */
  @EJB
  private RoleDAO roleDAO;

  /** Global information about the application. */
  @EJB
  private CoreInformation coreInformation;

  /** Call Qualis Registration */
  @Inject
  private Event<InstallEvent> installEvent;

  @Override
  public void installSystem(MarvinConfiguration config, Academic admin)
      throws OperationFailedException {
    logger.log(Level.FINER, "Installing system...");

    try {
      // Creates the roles in the database from a JSON file located in META-INF/installSystem.
      InputStream jsonFile =
          ResourceUtil.getResourceAsStream(INIT_DATA_PATH + INIT_DATA_ROLE_FILE_NAME);
      try (Scanner scanner = new Scanner(jsonFile)) {
        // Reads the content of the entire file, which contains a JSON array.
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine())
          builder.append(scanner.nextLine());

        // Instantiates the JSON array and reads the objects.
        JSONArray array = new JSONArray(builder.toString());
        for (int i = 0; i < array.length(); i++) {
          JSONObject obj = array.getJSONObject(i);
          Role role = new Role(obj.getString("name"), obj.getString("descriptionKey"));
          logger.log(Level.FINE, "Persisting role: {0}", role.getName());
          roleDAO.save(role);
        }
      }
    } catch (Exception e) {
      // Logs and rethrows the exception for the controller to display the error to the user.
      logger.log(Level.SEVERE, "Could not read initial data for roles.", e);
      throw new OperationFailedException(e);
    }

    try {
      // Assigns the system administrator role to the user that installed Marvin.
      Role adminRole = roleDAO.retrieveByName(Role.SYSADMIN_ROLE_NAME);
      admin.assignRole(adminRole);

      // Encodes the admin's password.
      admin.setPassword(TextUtils.produceBase64EncodedMd5Hash(admin.getPassword()));

      // Register the last update date / creation date.
      Date now = new Date(System.currentTimeMillis());
      admin.setLastUpdateDate(now);
      admin.setCreationDate(now);
      config.setCreationDate(now);
      logger.log(Level.FINE, "Admin's last update date have been set as: {0}", new Object[] {now});

      // Saves the administrator.
      logger.log(Level.FINER,
          "Persisting admin data...\n\t- Short name = {0}\n\t- Last update date = {1}",
          new Object[] {admin.getShortName(), admin.getLastUpdateDate()});
      academicDAO.save(admin);
      logger.log(Level.FINE, "The administrator has been saved: {0} ({1})",
          new Object[] {admin.getName(), admin.getEmail()});

      // Saves Marvin's configuration.
      logger.log(Level.FINER, "Persisting configuration data...\n\t- Date = {0}\n\t- Acronym = {1}",
          new Object[] {config.getCreationDate(), config.getInstitutionAcronym()});
      marvinConfigurationDAO.save(config);
      logger.log(Level.FINE, "The configuration has been saved");

      // Reloads the bean that holds the configuration and determines if the system is installed.
      logger.log(Level.FINER, "Reloading core information...");
      coreInformation.init();

      installEvent.fire(new InstallEvent());
    } catch (NoSuchAlgorithmException e) {
      // Logs and rethrows the exception for the controller to display the error to the user.
      logger.log(Level.SEVERE, "Could not find MD5 algorithm for password encription!", e);
      throw new OperationFailedException(e);
    } catch (Exception e) {
      // Logs and rethrows the exception for the controller to display the error to the user.
      logger.log(Level.SEVERE, "Exception during system installation!", e);
      throw new OperationFailedException(e);
    }
  }
}
