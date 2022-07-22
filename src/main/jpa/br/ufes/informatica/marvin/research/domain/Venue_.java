package br.ufes.informatica.marvin.research.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-07-21T20:24:53.943-0300")
@StaticMetamodel(Venue.class)
public class Venue_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<Venue, String> acronym;
	public static volatile SingularAttribute<Venue, String> issn;
	public static volatile SingularAttribute<Venue, String> name;
	public static volatile SingularAttribute<Venue, VenueCategory> category;
	public static volatile SingularAttribute<Venue, Date> dtEnd;
	public static volatile SingularAttribute<Venue, Date> dtStart;
}
