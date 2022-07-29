package br.ufes.informatica.marvin.research.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.marvin.research.domain.Rule;

@Local
public interface ManageRulesService extends CrudService<Rule> {

	public List<Rule> findValidityRules();
}
