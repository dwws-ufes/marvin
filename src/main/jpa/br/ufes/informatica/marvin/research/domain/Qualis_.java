package br.ufes.informatica.marvin.research.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-02-13T19:59:35.639-0300")
@StaticMetamodel(Qualis.class)
public class Qualis_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<Qualis, String> name;
	public static volatile SingularAttribute<Qualis, Float> value;
	public static volatile SingularAttribute<Qualis, Date> dtStart;
	public static volatile SingularAttribute<Qualis, Date> dtEnd;
}
