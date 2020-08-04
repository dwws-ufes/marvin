package br.ufes.informatica.marvin.core.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.lang.reflect.Field;
import javax.enterprise.context.Conversation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import br.ufes.informatica.marvin.core.application.ChangePasswordService;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.exceptions.InvalidPasswordCodeException;

/**
 * TODO: document this type.
 *
 * @author Luan Thome (https://github.com/luanripax/)
 */
@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class ChangePasswordControllerTest {
  /** TODO: document this field. */
  @Mock
  private ChangePasswordService changePasswordService;

  /** TODO: document this field. */
  @Mock
  private Conversation conversation;

  /** TODO: document this field. */
  @InjectMocks
  private ChangePasswordController changePasswordController;

  /** TODO: document this field. */
  private Academic academic;

  /** TODO: document this field. */
  private String passwordCode;

  /** TODO: document this field. */
  private String expectedPasswordCode;

  /** TODO: document this field. */
  private Field field;

  /**
   * TODO: document this method.
   * 
   * @throws NoSuchFieldException
   * @throws SecurityException
   */
  @Before
  public void setup() throws NoSuchFieldException, SecurityException {
    academic = new Academic();
    passwordCode = "a4bn6-sd5465-br4fd";
    changePasswordController.setPasswordCode(passwordCode);

    // Using reflection, we can get the value of "validatedPasswordCode", that is a private field.
    field = ChangePasswordController.class.getDeclaredField("validatedPasswordCode");
    field.setAccessible(true);
  }

  /**
   * TODO: document this method.
   * 
   * @throws InvalidPasswordCodeException
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   */
  @Test
  public void testCheckCodeSuccessWithTransientConversation()
      throws InvalidPasswordCodeException, IllegalArgumentException, IllegalAccessException {

    // Behavior of methods
    when(changePasswordService.retrieveAcademicByPasswordCode(passwordCode)).thenReturn(academic);
    when(conversation.isTransient()).thenReturn(true);

    // Calling the method being tested
    changePasswordController.checkCode();

    // getting the value of "validatedPasswordCode", should be after the testing method
    expectedPasswordCode = (String) field.get(changePasswordController);

    // Evaluation
    assertEquals(passwordCode, expectedPasswordCode);
    assertTrue(changePasswordController.isValidCode());
    verify(changePasswordService, times(1)).retrieveAcademicByPasswordCode(passwordCode);
    verify(conversation, times(1)).begin();
  }

  /**
   * TODO: document this method.
   * 
   * @throws InvalidPasswordCodeException
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   */
  @Test
  public void testCheckCodeSuccessWithLongRunningConversation()
      throws InvalidPasswordCodeException, IllegalArgumentException, IllegalAccessException {

    // Behavior of methods
    when(changePasswordService.retrieveAcademicByPasswordCode(passwordCode)).thenReturn(academic);
    when(conversation.isTransient()).thenReturn(false);

    // Calling the method being tested
    changePasswordController.checkCode();

    // getting the value of "validatedPasswordCode", should be after the testing method
    expectedPasswordCode = (String) field.get(changePasswordController);

    // Evaluation
    assertEquals(passwordCode, expectedPasswordCode);
    assertTrue(changePasswordController.isValidCode());
    verify(changePasswordService, times(1)).retrieveAcademicByPasswordCode(passwordCode);
    verify(conversation, times(0)).begin();
  }

  /**
   * TODO: document this method.
   * 
   * @throws InvalidPasswordCodeException
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   */
  @Test
  public void testCheckCodeFailWithInvalidCode()
      throws InvalidPasswordCodeException, IllegalArgumentException, IllegalAccessException {

    // Behavior of methods
    when(changePasswordService.retrieveAcademicByPasswordCode(passwordCode))
        .thenThrow(new InvalidPasswordCodeException(passwordCode));

    // Calling the method being tested
    changePasswordController.checkCode();

    // getting the value of "validatedPasswordCode", should be after the testing method
    expectedPasswordCode = (String) field.get(changePasswordController);

    // Evaluation
    assertNull(expectedPasswordCode);
    assertFalse(changePasswordController.isValidCode());
    verify(changePasswordService, times(1)).retrieveAcademicByPasswordCode(passwordCode);
    verify(conversation, times(0)).begin();
  }
}
