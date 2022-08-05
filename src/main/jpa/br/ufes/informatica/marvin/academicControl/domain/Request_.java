package br.ufes.informatica.marvin.academicControl.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.marvin.core.domain.Academic;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-08-05T18:38:04.152-0300")
@StaticMetamodel(Request.class)
public class Request_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<Request, Academic> requester;
	public static volatile SingularAttribute<Request, Date> requestDate;
	public static volatile SingularAttribute<Request, Date> dateSituation;
	public static volatile SingularAttribute<Request, String> requestResponseDetailing;
	public static volatile SingularAttribute<Request, Academic> grantor;
	public static volatile SingularAttribute<Request, byte[]> fileUniversityDegree;
	public static volatile SingularAttribute<Request, byte[]> fileUseOfCredits;
}
