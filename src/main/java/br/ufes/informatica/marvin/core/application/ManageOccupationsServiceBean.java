package br.ufes.informatica.marvin.core.application;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.controller.PersistentObjectConverterFromId;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Occupation;
import br.ufes.informatica.marvin.core.persistence.AcademicDAO;
import br.ufes.informatica.marvin.core.persistence.OccupationDAO;

@Stateless
@PermitAll
public class ManageOccupationsServiceBean extends CrudServiceBean<Occupation> implements ManageOccupationsService {
	private static final long serialVersionUID = 1L;

	@EJB
	private OccupationDAO OccupationDAO;

	@EJB
	private AcademicDAO academicDAO;

	/** TODO: document this field. */
	private PersistentObjectConverterFromId<Academic> academicConverter;

	@Override
	public BaseDAO<Occupation> getDAO() {
		return OccupationDAO;
	}

	@Override
	public PersistentObjectConverterFromId<Academic> getAcademicConverter() {
		if (academicConverter == null)
			academicConverter = new PersistentObjectConverterFromId<Academic>(academicDAO);
		return academicConverter;
	}

	@Override
	public List<Academic> findAcademicByNameEmail(String search) {
		try {
			return this.OccupationDAO.retriveByNameEmail(search);
		} catch (PersistentObjectNotFoundException e) {
			return null;
		}
	}

	public Occupation findOccupationByAcademic(Long idAcademic) {
		try {
			return this.OccupationDAO.retriveByAcademic(idAcademic);
		} catch (PersistentObjectNotFoundException e) {
			return null;
		} catch (MultiplePersistentObjectsFoundException e) {
			return null;
		}
	}

	public List<Occupation> findOccupationsByPPG(Long idPPG) {
		try {
			return this.OccupationDAO.retriveOccupationsByPPG(idPPG);
		} catch (PersistentObjectNotFoundException e) {
			return null;
		}
	}

	public List<Academic> findAcademicsByPPG(Long idPPG) {
		try {
			List<Occupation> occupations = this.OccupationDAO.retriveOccupationsByPPG(idPPG);
			List<Academic> academics = new ArrayList<Academic>();

			for (Occupation occupation : occupations) {
				academics.add(occupation.getAcademic());
			}

			return academics;
		} catch (PersistentObjectNotFoundException e) {
			return null;
		}
	}

	public List<Occupation> findAcademicsByOccupation(String type) {
		try {
			List<Occupation> occupations = null;

			if (type != null && type.length() > 0) {
				occupations = this.OccupationDAO.retriveOccupationsByType(type);
			}

			return occupations;
		} catch (PersistentObjectNotFoundException e) {
			return null;
		}
	}

}
