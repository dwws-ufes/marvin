package br.ufes.informatica.marvin.academicControl.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.marvin.academicControl.enums.EnumRequestSituation;
import br.ufes.informatica.marvin.core.domain.Academic;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-08-07T00:03:02.519-0300")
@StaticMetamodel(Request.class)
public class Request_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<Request, Academic> requester;
	public static volatile SingularAttribute<Request, Academic> userSituation;
	public static volatile SingularAttribute<Request, Academic> grantor;
	public static volatile SingularAttribute<Request, Deadline> deadline;
	public static volatile SingularAttribute<Request, Date> requestDate;
	public static volatile SingularAttribute<Request, Date> userSituationDate;
	public static volatile SingularAttribute<Request, Date> responseDate;
	public static volatile SingularAttribute<Request, EnumRequestSituation> requestSituation;
	public static volatile SingularAttribute<Request, String> requestResponseDetailing;
	public static volatile SingularAttribute<Request, String> observation;
	public static volatile SingularAttribute<Request, String> localfileUniversityDegree;
	public static volatile SingularAttribute<Request, String> localfileUseOfCredits;
}
