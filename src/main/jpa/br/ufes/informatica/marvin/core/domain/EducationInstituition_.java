package br.ufes.informatica.marvin.core.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-01-04T17:10:30.097-0200")
@StaticMetamodel(EducationInstituition.class)
public class EducationInstituition_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<EducationInstituition, String> name;
	public static volatile SingularAttribute<EducationInstituition, String> institutionAcronym;
	public static volatile SingularAttribute<EducationInstituition, String> state;
	public static volatile SingularAttribute<EducationInstituition, String> country;
}
