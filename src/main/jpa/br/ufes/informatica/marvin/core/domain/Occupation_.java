package br.ufes.informatica.marvin.core.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-07-25T20:24:54.962-0300")
@StaticMetamodel(Occupation.class)
public class Occupation_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<Occupation, Boolean> coordinator;
	public static volatile SingularAttribute<Occupation, Boolean> secretary;
	public static volatile SingularAttribute<Occupation, Boolean> member;
	public static volatile SingularAttribute<Occupation, Boolean> doctoral_supervisor;
	public static volatile SingularAttribute<Occupation, Academic> academic;
	public static volatile SingularAttribute<Occupation, PPG> ppg;
}
