package br.ufes.informatica.marvin.research.application;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.marvin.research.domain.Qualis;

@Local
public interface ManageQualisService extends CrudService<Qualis> {

	public List<Qualis> findByQualisValidity(Long id);

	public Qualis findByNameQualisValidity(String qualisName, Date dtStart, Date dtEnd, Long idPPG);

}
