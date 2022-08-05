package br.ufes.informatica.marvin.academicControl.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.marvin.core.domain.Academic;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-08-04T23:27:36.683-0300")
@StaticMetamodel(Solicitation.class)
public class Solicitation_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<Solicitation, SubjectOffer> subjectOffer;
	public static volatile SingularAttribute<Solicitation, SolicitationType> solicitationType;
	public static volatile SingularAttribute<Solicitation, Date> requestDate;
	public static volatile SingularAttribute<Solicitation, Date> completionDate;
	public static volatile SingularAttribute<Solicitation, SolicitationSituation> SolicitationSituation;
	public static volatile SingularAttribute<Solicitation, String> requestResponseDetailing;
	public static volatile SingularAttribute<Solicitation, Academic> requester;
}
