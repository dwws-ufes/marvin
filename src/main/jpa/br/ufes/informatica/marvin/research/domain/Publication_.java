package br.ufes.informatica.marvin.research.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.marvin.core.domain.Academic;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-04-07T09:49:08.548-0300")
@StaticMetamodel(Publication.class)
public class Publication_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<Publication, String> title;
	public static volatile SingularAttribute<Publication, Integer> year;
	public static volatile SingularAttribute<Publication, String> pages;
	public static volatile SingularAttribute<Publication, String> doi;
	public static volatile SingularAttribute<Publication, String> publisher;
	public static volatile SingularAttribute<Publication, Academic> owner;
	public static volatile SingularAttribute<Publication, Venue> venue;
	public static volatile ListAttribute<Publication, String> authors;
}
