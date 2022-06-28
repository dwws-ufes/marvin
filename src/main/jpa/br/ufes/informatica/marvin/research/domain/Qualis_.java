package br.ufes.informatica.marvin.research.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-06-27T22:36:53.934-0300")
@StaticMetamodel(Qualis.class)
public class Qualis_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<Qualis, String> name;
	public static volatile SingularAttribute<Qualis, Float> scoreConference;
	public static volatile SingularAttribute<Qualis, Float> scoreJournal;
	public static volatile SingularAttribute<Qualis, Boolean> restrito;
	public static volatile SingularAttribute<Qualis, QualisValidity> qualisValidity;
}
