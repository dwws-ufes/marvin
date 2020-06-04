package br.ufes.informatica.core.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import javax.enterprise.context.Conversation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.ufes.informatica.marvin.core.application.ChangePasswordService;
import br.ufes.informatica.marvin.core.controller.ChangePasswordController;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.exceptions.InvalidPasswordCodeException;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class ChangePasswordControllerTest {
	
	@Mock
	private ChangePasswordService changepasswordservice;
	
	@Mock
	private Conversation conversation;
	
	@InjectMocks
	private ChangePasswordController changepasswordcontroller;
	
	private Academic academic;
	private String passwordCode;
	private String expectedPwdCode;
	private Field field;

	@Before
	public void setup() throws NoSuchFieldException, SecurityException {
		academic = new Academic();
		passwordCode = "a4bn6-sd5465-br4fd";
		changepasswordcontroller.setPasswordCode(passwordCode);
		// Using reflection, we can get the value of "validatedPasswordCode", that is a private field.
		field = ChangePasswordController.class.getDeclaredField("validatedPasswordCode");
		field.setAccessible(true);
	}
	
	@Test
	public void checkCodeSuccesfully() throws InvalidPasswordCodeException, IllegalArgumentException, IllegalAccessException {
		
		// Behavior of methods
		when(changepasswordservice.retrieveAcademicByPasswordCode(passwordCode)).thenReturn(academic);
		when(conversation.isTransient()).thenReturn(true);
		
		// Calling our testing method
		changepasswordcontroller.checkCode();
		
		// getting the value of "validatedPasswordCode", should be after the testing method
		expectedPwdCode = (String) field.get(changepasswordcontroller);
		
		// Evaluation
		assertEquals(passwordCode, expectedPwdCode);
		verify(changepasswordservice, times(1)).retrieveAcademicByPasswordCode(passwordCode);
		verify(conversation, times(1)).begin();		
	}
	
	@Test
	public void checkCodeFailed() throws InvalidPasswordCodeException, IllegalArgumentException, IllegalAccessException  {
		
		// Behavior of methods
		when(changepasswordservice.retrieveAcademicByPasswordCode(passwordCode)).thenReturn(academic);
		when(conversation.isTransient()).thenReturn(false);
		
		// Calling our testing method
		changepasswordcontroller.checkCode();
		
		// getting the value of "validatedPasswordCode", should be after the testing method
		expectedPwdCode = (String) field.get(changepasswordcontroller);
		
		// Evaluation
		assertEquals(passwordCode, expectedPwdCode);
		verify(changepasswordservice, times(1)).retrieveAcademicByPasswordCode(passwordCode);
		verify(conversation, times(0)).begin();		
	}

}
