package br.ufes.informatica.marvin.academicControl.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2023-02-02T22:53:34.727-0200")
@StaticMetamodel(Period.class)
public class Period_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<Period, String> name;
	public static volatile SingularAttribute<Period, Date> enrollmentStartDate;
	public static volatile SingularAttribute<Period, Date> enrollmentFinalDate;
	public static volatile SingularAttribute<Period, Date> offerStartDate;
	public static volatile SingularAttribute<Period, Date> offerFinalDate;
	public static volatile SingularAttribute<Period, Date> enrollmentProcessingStartDate;
	public static volatile SingularAttribute<Period, Date> enrollmentProcessingFinalDate;
	public static volatile SingularAttribute<Period, Date> periodStartDate;
	public static volatile SingularAttribute<Period, Date> periodFinalDate;
}
