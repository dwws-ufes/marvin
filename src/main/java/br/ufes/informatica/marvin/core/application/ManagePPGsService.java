package br.ufes.informatica.marvin.core.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudException;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.PPG;

@Local
public interface ManagePPGsService extends CrudService<PPG> {

	public void validateCreate(PPG entity, List<Academic> coordinator) throws CrudException;

}
