package br.ufes.informatica.marvin.academicControl.domain;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.marvin.academicControl.enums.EnumStatusMail;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2023-02-02T09:25:32.213-0200")
@StaticMetamodel(MailSender.class)
public class MailSender_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<MailSender, String> subject;
	public static volatile SingularAttribute<MailSender, String> addressee;
	public static volatile SingularAttribute<MailSender, String> text;
	public static volatile SingularAttribute<MailSender, String> error;
	public static volatile SingularAttribute<MailSender, EnumStatusMail> statusMail;
	public static volatile SingularAttribute<MailSender, Date> creationDate;
	public static volatile SingularAttribute<MailSender, Date> sentDate;
}
