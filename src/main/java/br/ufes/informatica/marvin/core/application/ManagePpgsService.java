package br.ufes.informatica.marvin.core.application;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.marvin.core.domain.Ppg;

@Local
public interface ManagePpgsService extends CrudService<Ppg> {

}
