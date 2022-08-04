package br.ufes.informatica.marvin.academicControl.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.marvin.core.domain.Academic;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-08-04T18:22:29.166-0300")
@StaticMetamodel(SubjectOffer.class)
public class SubjectOffer_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<SubjectOffer, Academic> professor;
	public static volatile SingularAttribute<SubjectOffer, Long> numMaxStudents;
	public static volatile SingularAttribute<SubjectOffer, Date> creationDate;
	public static volatile SingularAttribute<SubjectOffer, SchoolSubject> schoolSubject;
	public static volatile SingularAttribute<SubjectOffer, Period> period;
}
