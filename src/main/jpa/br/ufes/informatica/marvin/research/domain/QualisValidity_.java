package br.ufes.informatica.marvin.research.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.marvin.core.domain.PPG;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-06-27T22:36:33.079-0300")
@StaticMetamodel(QualisValidity.class)
public class QualisValidity_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<QualisValidity, Date> dtStart;
	public static volatile SingularAttribute<QualisValidity, Date> dtEnd;
	public static volatile SingularAttribute<QualisValidity, PPG> ppg;
	public static volatile ListAttribute<QualisValidity, Qualis> qualis;
}
