package br.ufes.informatica.marvin.research.persistence;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.research.domain.Rule;

@Local
public interface RuleDAO extends BaseDAO<Rule> {

}
