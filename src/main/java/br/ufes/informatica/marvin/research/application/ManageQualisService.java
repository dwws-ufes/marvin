package br.ufes.informatica.marvin.research.application;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.marvin.research.domain.Qualis;
import br.ufes.informatica.marvin.research.domain.QualisValidity;

@Local
public interface ManageQualisService extends CrudService<Qualis> {

	public List<Qualis> findByQualisValidityId(Long id);

	public Qualis findByNameQualisValidity(String qualisName, Date dtStart, Date dtEnd, Long idPPG);

	public Map<String, Qualis> findByQualisValidity(Long idPPG);

	public Qualis findByNameValidity(String name, QualisValidity qualisValidity);

	public List<Qualis> findByNameValidity(Date dtStart, Date dtEnd, Long idPPG);

}
