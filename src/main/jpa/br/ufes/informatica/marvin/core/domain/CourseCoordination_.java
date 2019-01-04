package br.ufes.informatica.marvin.core.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-01-04T17:10:30.096-0200")
@StaticMetamodel(CourseCoordination.class)
public class CourseCoordination_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<CourseCoordination, Date> startDate;
	public static volatile SingularAttribute<CourseCoordination, Date> endDate;
	public static volatile SingularAttribute<CourseCoordination, Course> course;
	public static volatile SingularAttribute<CourseCoordination, Academic> academic;
}
