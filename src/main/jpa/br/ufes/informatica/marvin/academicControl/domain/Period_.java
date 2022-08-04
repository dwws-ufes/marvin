package br.ufes.informatica.marvin.academicControl.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-08-04T00:46:30.010-0300")
@StaticMetamodel(Period.class)
public class Period_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<Period, String> name;
	public static volatile SingularAttribute<Period, Date> periodStartDate;
	public static volatile SingularAttribute<Period, Date> periodFinalDate;
	public static volatile SingularAttribute<Period, Date> enrollmentStartDate;
	public static volatile SingularAttribute<Period, Date> enrollmentFinalDate;
	public static volatile SingularAttribute<Period, Date> offerStartDate;
	public static volatile SingularAttribute<Period, Date> offerFinalDate;
}
