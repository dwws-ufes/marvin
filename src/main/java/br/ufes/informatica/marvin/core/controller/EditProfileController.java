package br.ufes.informatica.marvin.core.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import br.ufes.inf.nemo.jbutler.ejb.controller.JSFController;
import br.ufes.informatica.marvin.core.application.EditProfileService;
import br.ufes.informatica.marvin.core.domain.Academic;

/**
 * Controller for the "Edit Profile" use case.
 * 
 * This use case allows any Academic to edit their own profile information. Upon request, Marvin
 * will show forms with the user's information and by submitting new data back to Marvin it will be
 * updated. Forms should be divided in the following sections:
 * 
 * <ul>
 * <li>Authentication information: e-mail and password;</li>
 * <li>Basic information: name, short name, birth date, CPF, birth city and nationality;</li>
 * <li>Contact information: telephone numbers;</li>
 * <li>Academic information: Lattes ID.</li>
 * </ul>
 * 
 * Changes in e-mail address must be confirmed by sending a code to the new e-mail address and
 * effecting the change only after the Academic confirms the code.
 * 
 * This controller is session scoped, as it deals with the information from the authenticated
 * Academic.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Named
@SessionScoped
public class EditProfileController extends JSFController {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** Logger for this class. */
  private static final Logger logger =
      Logger.getLogger(EditProfileController.class.getCanonicalName());

  /** Path to the folder where the view files (web pages) for this action are placed. */
  private static final String VIEW_PATH = "/core/editProfile/";

  /** The "Edit Profile" service. */
  @EJB
  private EditProfileService editProfileService;

  /** The session controller, with information on the authenticated user. */
  @Inject
  private SessionController sessionController;

  /** Input: the academic whose profile is being edited. */
  private Academic academic;

  /** Getter for academic. Returns the currently authenticated user. */
  public Academic getAcademic() {
    if (academic == null)
      academic = sessionController.getCurrentUser();
    return academic;
  }

  /**
   * TODO: document this method.
   */
  public void save() {
    // Updates the academic's data.
    logger.log(Level.INFO, "Saving academic: {0}", new Object[] {academic});
    academic = editProfileService.update(academic);

    // Shows a confirmation message.
    addGlobalI18nMessage("msgsCore", FacesMessage.SEVERITY_INFO,
        "editProfile.info.updateSuccess.summary", "editProfile.info.updateSuccess.detail");
  }
}
