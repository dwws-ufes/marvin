package br.ufes.informatica.marvin.academicControl.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-08-07T10:16:20.896-0300")
@StaticMetamodel(Deadline.class)
public class Deadline_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<Deadline, String> name;
	public static volatile SingularAttribute<Deadline, String> description;
	public static volatile SingularAttribute<Deadline, Long> daysToReply;
}
