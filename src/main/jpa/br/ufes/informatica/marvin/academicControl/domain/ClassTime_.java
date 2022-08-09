package br.ufes.informatica.marvin.academicControl.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.marvin.academicControl.enums.EnumWeekDays;
import java.time.LocalTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-08-08T20:00:46.885-0300")
@StaticMetamodel(ClassTime.class)
public class ClassTime_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<ClassTime, LocalTime> startTime;
	public static volatile SingularAttribute<ClassTime, LocalTime> endTime;
	public static volatile SingularAttribute<ClassTime, EnumWeekDays> weekDay;
}
