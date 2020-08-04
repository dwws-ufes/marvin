package br.ufes.informatica.marvin.core.application;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import br.ufes.inf.nemo.jbutler.TextUtils;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.exceptions.InvalidPasswordCodeException;
import br.ufes.informatica.marvin.core.exceptions.OperationFailedException;
import br.ufes.informatica.marvin.core.persistence.AcademicDAO;

/**
 * TODO: document this type.
 *
 * @author Luan Thome (https://github.com/luanripax/)
 */
@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class ChangePasswordServiceBeanTest {
  /** TODO: document this field. */
  @Mock
  private AcademicDAO academicDAO;

  /** TODO: document this field. */
  @InjectMocks
  private ChangePasswordServiceBean changePasswordServiceBean;

  /** TODO: document this field. */
  private String password = "senha";

  /** TODO: document this field. */
  private String passwordCode = "83997689-22b6-4a7e-a801";

  /**
   * TODO: document this method.
   * 
   * @throws InvalidPasswordCodeException
   * @throws OperationFailedException
   * @throws PersistentObjectNotFoundException
   * @throws MultiplePersistentObjectsFoundException
   * @throws NoSuchAlgorithmException
   * @throws UnsupportedEncodingException
   */
  @Test
  public void testSetNewPasswordSuccess()
      throws InvalidPasswordCodeException, OperationFailedException,
      PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException,
      NoSuchAlgorithmException, UnsupportedEncodingException {

    // Create a simulated academic on database
    Academic academic = new Academic();
    academic.setPassword("oldpasswd");
    academic.setPasswordCode(passwordCode);

    // Expected password
    String expectedPassword = TextUtils.produceBase64EncodedMd5Hash(password);

    // Tells how "retrieveByPasswordCode should behave itself
    when(academicDAO.retrieveByPasswordCode(passwordCode)).thenReturn(academic);

    // Calls testing method
    changePasswordServiceBean.setNewPassword(passwordCode, password);

    // Evaluation
    assertEquals(expectedPassword, academic.getPassword());
    assertEquals(null, academic.getPasswordCode());
    verify(academicDAO, times(1)).save(academic);
  }
}
