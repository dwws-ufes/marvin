package br.ufes.informatica.marvin.research.application;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.research.domain.Qualis;
import br.ufes.informatica.marvin.research.domain.QualisValidity;
import br.ufes.informatica.marvin.research.persistence.QualisDAO;

@Stateless
@PermitAll
public class ManageQualisServiceBean extends CrudServiceBean<Qualis> implements ManageQualisService {
	private static final long serialVersionUID = 1L;

	@EJB
	private QualisDAO qualisDAO;

	@Override
	public BaseDAO<Qualis> getDAO() {
		return qualisDAO;
	}

	@Override
	public List<Qualis> findByQualisValidityId(Long id) {

		try {
			return this.qualisDAO.retrieveByQualisValidityId(id);
		} catch (PersistentObjectNotFoundException e) {
			return null;
		}
	}

	@Override
	public Qualis findByNameQualisValidity(String qualisName, Date dtStart, Date dtEnd, Long idPPG) {
		try {
			return this.qualisDAO.retriveByNameQualisValidity(qualisName, dtStart, dtEnd, idPPG);
		} catch (PersistentObjectNotFoundException e) {
			return null;
		} catch (MultiplePersistentObjectsFoundException e) {
			return null;
		}
	}

	@Override
	public Map<String, Qualis> findByQualisValidity(Long idPPG) {
		try {
			Map<String, Qualis> map = new HashMap<String, Qualis>();

			List<Qualis> qualis = this.qualisDAO.retrieveByQualisValidity(idPPG);

			for (Qualis elemt : qualis) {
				map.put(elemt.getName(), elemt);
			}

			return map;
		} catch (PersistentObjectNotFoundException e) {
			return null;
		}
	}

	@Override
	public Qualis findByNameValidity(String name, QualisValidity qualisValidity) {
		try {
			return this.qualisDAO.retriveByNameValidity(name, qualisValidity);
		} catch (PersistentObjectNotFoundException e) {
			return null;
		} catch (MultiplePersistentObjectsFoundException e) {
			return null;
		}
	}
}
