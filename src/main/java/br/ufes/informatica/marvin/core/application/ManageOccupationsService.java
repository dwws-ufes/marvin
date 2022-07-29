package br.ufes.informatica.marvin.core.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.PersistentObjectConverterFromId;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Occupation;

@Local
public interface ManageOccupationsService extends CrudService<Occupation> {
	public PersistentObjectConverterFromId<Academic> getAcademicConverter();

	public List<Academic> findAcademicByNameEmail(String query);

	public Occupation findOccupationByAcademic(Long idAcademic);

	public List<Occupation> findOccupationsByPPG(Long idPPG);

	public List<Academic> findAcademicsByPPG(Long idPPG);

	public List<Occupation> findAcademicsByOccupation(String type);

	public List<Occupation> findOccupationByDoctoralMaster(Long idPPG);
}
