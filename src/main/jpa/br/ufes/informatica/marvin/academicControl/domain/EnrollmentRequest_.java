package br.ufes.informatica.marvin.academicControl.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.marvin.academicControl.enums.EnumEnrollmentRequestSituation;
import br.ufes.informatica.marvin.core.domain.Academic;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-08-05T18:01:54.457-0300")
@StaticMetamodel(EnrollmentRequest.class)
public class EnrollmentRequest_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<EnrollmentRequest, Academic> requester;
	public static volatile SingularAttribute<EnrollmentRequest, SubjectOffer> subjectOffer;
	public static volatile SingularAttribute<EnrollmentRequest, Date> requestDate;
	public static volatile SingularAttribute<EnrollmentRequest, EnumEnrollmentRequestSituation> enrollmentRequestSituation;
	public static volatile SingularAttribute<EnrollmentRequest, String> requestResponseDetailing;
	public static volatile SingularAttribute<EnrollmentRequest, BigDecimal> note;
	public static volatile SingularAttribute<EnrollmentRequest, Date> dateSituation;
}
