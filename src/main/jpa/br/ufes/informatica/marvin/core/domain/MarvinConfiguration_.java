package br.ufes.informatica.marvin.core.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-05-28T09:07:32.320-0300")
@StaticMetamodel(MarvinConfiguration.class)
public class MarvinConfiguration_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<MarvinConfiguration, Date> creationDate;
	public static volatile SingularAttribute<MarvinConfiguration, String> institutionAcronym;
	public static volatile SingularAttribute<MarvinConfiguration, String> smtpServerAddress;
	public static volatile SingularAttribute<MarvinConfiguration, Integer> smtpServerPort;
	public static volatile SingularAttribute<MarvinConfiguration, String> smtpUsername;
	public static volatile SingularAttribute<MarvinConfiguration, String> smtpPassword;
	public static volatile SingularAttribute<MarvinConfiguration, String> baseURL;
}
