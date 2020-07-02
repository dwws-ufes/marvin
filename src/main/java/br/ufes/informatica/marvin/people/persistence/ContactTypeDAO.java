package br.ufes.informatica.marvin.people.persistence;

import javax.ejb.Local;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.people.domain.ContactType;

/**
 * Interface for a DAO for objects of the ContactType domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the
 * superclass, whereas operations that are specific to the managed domain class (if any) are
 * specified in this class.
 * 
 * <i>This class is part of a "Legal Entity" mini framework for EJB3.</i>
 * 
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Local
public interface ContactTypeDAO extends BaseDAO<ContactType> {
}
