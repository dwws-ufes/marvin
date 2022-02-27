package br.ufes.informatica.marvin.research.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.marvin.core.domain.Role;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-02-21T23:36:34.613-0300")
@StaticMetamodel(Rule.class)
public class Rule_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<Rule, Integer> qtdPassYears;
	public static volatile SingularAttribute<Rule, Float> total;
	public static volatile SingularAttribute<Rule, Float> scoreJournal;
	public static volatile SingularAttribute<Rule, Float> scoreRestricted;
	public static volatile SingularAttribute<Rule, Float> scoreJournalRestricted;
	public static volatile SingularAttribute<Rule, Date> dtStart;
	public static volatile SingularAttribute<Rule, Date> dtEnd;
	public static volatile SingularAttribute<Rule, Role> role;
}
