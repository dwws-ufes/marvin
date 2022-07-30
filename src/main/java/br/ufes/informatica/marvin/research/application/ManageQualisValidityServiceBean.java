package br.ufes.informatica.marvin.research.application;

import java.util.Date;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.research.domain.QualisValidity;
import br.ufes.informatica.marvin.research.persistence.QualisValidityDAO;

@Stateless
@PermitAll
public class ManageQualisValidityServiceBean extends CrudServiceBean<QualisValidity>
		implements ManageQualisValidityService {
	private static final long serialVersionUID = 1L;

	@EJB
	private QualisValidityDAO qualisValidityDAO;

	@Override
	public BaseDAO<QualisValidity> getDAO() {
		return qualisValidityDAO;
	}

	public QualisValidity findByDates(Date dtStart, Date DtEnd) {
		try {
			return this.qualisValidityDAO.retriveByDates(dtStart, DtEnd);
		} catch (PersistentObjectNotFoundException e) {
			return null;
		} catch (MultiplePersistentObjectsFoundException e) {
			return null;
		} catch (EJBTransactionRolledbackException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}
}
