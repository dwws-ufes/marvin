package br.ufes.informatica.marvin.academicControl.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.marvin.academicControl.enums.EnumSolicitationType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-08-07T20:34:58.771-0300")
@StaticMetamodel(Deadline.class)
public class Deadline_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<Deadline, String> name;
	public static volatile SingularAttribute<Deadline, String> description;
	public static volatile SingularAttribute<Deadline, Long> daysToReply;
	public static volatile SingularAttribute<Deadline, EnumSolicitationType> solicitationType;
}
