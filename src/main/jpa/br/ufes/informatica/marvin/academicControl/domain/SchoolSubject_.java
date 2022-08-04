package br.ufes.informatica.marvin.academicControl.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-08-04T00:46:30.031-0300")
@StaticMetamodel(SchoolSubject.class)
public class SchoolSubject_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<SchoolSubject, String> code;
	public static volatile SingularAttribute<SchoolSubject, String> name;
	public static volatile SingularAttribute<SchoolSubject, String> description;
	public static volatile SingularAttribute<SchoolSubject, Long> credits;
	public static volatile SingularAttribute<SchoolSubject, Long> workload;
}
