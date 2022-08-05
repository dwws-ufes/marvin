package br.ufes.informatica.marvin.academicControl.enums;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.marvin.academicControl.domain.EnrollmentRequest;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;
import br.ufes.informatica.marvin.core.domain.Academic;

@Generated(value = "Dali", date = "2022-08-05T17:50:43.414-0300")
@StaticMetamodel(EnrollmentRequest.class)
public class EnrollmentRequest_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<EnrollmentRequest, Academic> requester;
	public static volatile SingularAttribute<EnrollmentRequest, SubjectOffer> subjectOffer;
	public static volatile SingularAttribute<EnrollmentRequest, Date> requestDate;
	public static volatile SingularAttribute<EnrollmentRequest, Date> completionDate;
	public static volatile SingularAttribute<EnrollmentRequest, EnumEnrollmentRequestSituation> enrollmentRequestSituation;
	public static volatile SingularAttribute<EnrollmentRequest, String> requestResponseDetailing;
	public static volatile SingularAttribute<EnrollmentRequest, BigDecimal> note;
}
