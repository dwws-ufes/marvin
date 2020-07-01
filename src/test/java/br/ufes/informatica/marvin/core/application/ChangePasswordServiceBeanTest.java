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

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class ChangePasswordServiceBeanTest {
	
	@Mock
	private AcademicDAO academicDAO;
	
	@InjectMocks
	private ChangePasswordServiceBean changepasswordservicebean;
	
	private String passwd = "senha";
	private String passwdcode = "83997689-22b6-4a7e-a801";

	@Test
	public void testSuccessfullyChangePassword() throws InvalidPasswordCodeException, OperationFailedException, 
	PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException, NoSuchAlgorithmException, UnsupportedEncodingException {
		
		// Create a simulated academic on database
		Academic academic = new Academic();
		academic.setPassword("oldpasswd");
		academic.setPasswordCode(passwdcode);
		
		// Expected password
		String expectedPwd;
		expectedPwd = TextUtils.produceBase64EncodedMd5Hash(passwd);
		
		// Tells how "retrieveByPasswordCode should behave itself
		when(academicDAO.retrieveByPasswordCode(passwdcode)).thenReturn(academic);
		
		// Calls testing method
		changepasswordservicebean.setNewPassword(passwdcode, passwd);
		
		// Evaluation
		assertEquals(expectedPwd, academic.getPassword());
		assertEquals(null, academic.getPasswordCode());	
		verify(academicDAO, times(1)).save(academic);
	}
}