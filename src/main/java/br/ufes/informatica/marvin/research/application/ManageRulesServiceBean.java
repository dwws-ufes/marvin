package br.ufes.informatica.marvin.research.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.research.domain.Rule;
import br.ufes.informatica.marvin.research.persistence.RuleDAO;

@Stateless
@PermitAll
public class ManageRulesServiceBean extends CrudServiceBean<Rule> implements ManageRulesService {
	private static final long serialVersionUID = 1L;

	@EJB
	private RuleDAO ruleDAO;

	@Override
	public BaseDAO<Rule> getDAO() {
		return ruleDAO;
	}
}
