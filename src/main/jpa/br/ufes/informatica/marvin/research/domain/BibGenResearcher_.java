package br.ufes.informatica.marvin.research.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.marvin.core.domain.Academic;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-04-07T09:49:03.667-0300")
@StaticMetamodel(BibGenResearcher.class)
public class BibGenResearcher_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<BibGenResearcher, Academic> researcher;
	public static volatile SingularAttribute<BibGenResearcher, Integer> startYear;
	public static volatile SingularAttribute<BibGenResearcher, Integer> endYear;
}
