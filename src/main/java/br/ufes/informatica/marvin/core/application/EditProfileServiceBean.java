package br.ufes.informatica.marvin.core.application;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.persistence.AcademicDAO;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Stateless
public class EditProfileServiceBean implements EditProfileService {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** Logger for this class. */
  private static final Logger logger =
      Logger.getLogger(EditProfileServiceBean.class.getCanonicalName());

  /** TODO: document this field. */
  @EJB
  private AcademicDAO academicDAO;

  @Override
  public Academic update(Academic academic) {
    logger.log(Level.INFO, "Persisting academic: {0}", new Object[] {academic});

    // Saves the object, then merges with the current session to return the updated object.
    academicDAO.save(academic);
    return academicDAO.merge(academic);
  }
}
