package br.ufes.informatica.marvin.core.domain;

import br.ufes.informatica.marvin.people.domain.Person_;
import br.ufes.informatica.marvin.people.domain.Telephone;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-03-09T19:28:48.116-0300")
@StaticMetamodel(Academic.class)
public class Academic_ extends Person_ {
	public static volatile SingularAttribute<Academic, String> shortName;
	public static volatile SingularAttribute<Academic, String> email;
	public static volatile SingularAttribute<Academic, String> password;
	public static volatile SingularAttribute<Academic, Long> lattesId;
	public static volatile SingularAttribute<Academic, String> passwordCode;
	public static volatile SetAttribute<Academic, Telephone> telephones;
	public static volatile SetAttribute<Academic, Role> roles;
	public static volatile SingularAttribute<Academic, Date> creationDate;
	public static volatile SingularAttribute<Academic, Date> lastUpdateDate;
	public static volatile SingularAttribute<Academic, Date> lastLoginDate;
	public static volatile SingularAttribute<Academic, Ppg> ppg;
}
