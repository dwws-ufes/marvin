package br.ufes.informatica.marvin.core.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Academic_;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class AcademicJPADAOTest {
	
	@Mock
	private EntityManager entityManager;
	@Mock
	private CriteriaBuilder cb;
	@Mock
	private CriteriaQuery<Academic> cq;
	@Mock
	private Root<Academic> root;
	@Mock
	private TypedQuery<Academic> query;
	
	@InjectMocks
	AcademicJPADAO academicjpadao;
	
	@Test
	public void retrieveByPasswordCodeTest() throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		
		// Setup
		String pwdcode = "83997689-22b6-4a7e-a801";
		Academic academic = new Academic();
		academic.setPasswordCode(pwdcode);
		
		// Tells the query behavior
		when(entityManager.getCriteriaBuilder()).thenReturn(cb);
		when(cb.createQuery(Academic.class)).thenReturn(cq);
		when(cq.from(Academic.class)).thenReturn(root);
		when(cq.where(cb.equal(root.get(Academic_.passwordCode), pwdcode))).thenReturn(cq);
		when(entityManager.createQuery(cq)).thenReturn((query));
		when(query.getSingleResult()).thenReturn(academic);
	
		// Call the testing function
		academicjpadao.retrieveByPasswordCode(pwdcode);
		
		// Evaluation
		verify(entityManager, times(1)).getCriteriaBuilder();
		verify(cb, times(1)).createQuery(Academic.class);
		verify(cq, times(1)).from(Academic.class);
		verify(cq, times(1)).where(cb.isNotNull(root.get(Academic_.lattesId)));
		verify(entityManager, times(1)).createQuery(cq);
		verify(query, times(1)).getSingleResult();
		assertEquals(pwdcode, academic.getPasswordCode());
		assertTrue(academic instanceof Academic);
	}
	
	@Test(expected = PersistentObjectNotFoundException.class) 
	public void getSingleResultFailTest() throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		
		String pwdcode = "83997689-22b6-4a7e-a801";
		
		when(entityManager.getCriteriaBuilder()).thenReturn(cb);
		when(cb.createQuery(Academic.class)).thenReturn(cq);
		when(cq.from(Academic.class)).thenReturn(root);
		when(cq.where(cb.equal(root.get(Academic_.passwordCode), pwdcode))).thenReturn(cq);
		when(entityManager.createQuery(cq)).thenReturn((query));
		
		doThrow(new NoResultException()).when(query).getSingleResult();
		
		academicjpadao.retrieveByPasswordCode(pwdcode);
	
	}
}