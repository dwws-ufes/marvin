package br.ufes.informatica.marvin.people.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-01-04T17:09:22.672-0200")
@StaticMetamodel(Telephone.class)
public class Telephone_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<Telephone, String> number;
	public static volatile SingularAttribute<Telephone, ContactType> type;
}
