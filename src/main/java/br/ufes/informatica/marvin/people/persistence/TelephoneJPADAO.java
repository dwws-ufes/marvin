package br.ufes.informatica.marvin.people.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.informatica.marvin.people.domain.Telephone;

/**
 * Stateless session bean implementing a DAO for objects of the Telephone domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the
 * superclass, whereas operations that are specific to the managed domain class (if any is defined
 * in the implementing DAO interface) have to be implemented in this class.
 * 
 * <i>This class is part of a "Legal Entity" mini framework for EJB3.</i>
 * 
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Stateless
public class TelephoneJPADAO extends BaseJPADAO<Telephone> implements TelephoneDAO {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** The application's persistent context provided by the application server. */
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }
}
