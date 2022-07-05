package br.ufes.informatica.marvin.research.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.marvin.research.domain.Qualis;

@Local
public interface ManageQualisService extends CrudService<Qualis> {

	List<Qualis> findByQualisValidity(Long id);

}
