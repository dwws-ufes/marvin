package br.ufes.informatica.marvin.academicControl.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.marvin.academicControl.enums.EnumSchoolSubjectType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2023-02-06T12:39:51.421-0200")
@StaticMetamodel(SchoolSubject.class)
public class SchoolSubject_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<SchoolSubject, String> code;
	public static volatile SingularAttribute<SchoolSubject, String> name;
	public static volatile SingularAttribute<SchoolSubject, String> description;
	public static volatile SingularAttribute<SchoolSubject, EnumSchoolSubjectType> type;
	public static volatile SingularAttribute<SchoolSubject, String> summary;
	public static volatile SingularAttribute<SchoolSubject, String> bibliography;
	public static volatile SingularAttribute<SchoolSubject, Long> credits;
	public static volatile SingularAttribute<SchoolSubject, Long> workload;
	public static volatile SingularAttribute<SchoolSubject, String> subtitle;
}
