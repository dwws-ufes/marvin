package br.ufes.informatica.marvin.academicControl.application;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.MailSender;
import br.ufes.informatica.marvin.academicControl.enums.EnumStatusMail;
import br.ufes.informatica.marvin.academicControl.persistence.MailSenderDAO;
import br.ufes.informatica.marvin.utils.MarvinFunctions;

@Stateless
@PermitAll
public class MailSenderServiceBean extends CrudServiceBean<MailSender> implements MailSenderService {
	private static final long serialVersionUID = 1L;

	@EJB
	private MailSenderDAO mailSenderDAO;

	@Override
	public BaseDAO<MailSender> getDAO() {
		return mailSenderDAO;
	}

	@Override
	public List<MailSender> retrieveMailsNotSent() {
		return mailSenderDAO.retrieveMailsNotSent();
	}

	@Override
	public void createMailSender(String addressee, String subject, String text) {
		MailSender mailSender = new MailSender();
		mailSender.setAddressee(addressee);
		mailSender.setSubject(subject);
		mailSender.setText(text);
		mailSender.setCreationDate(MarvinFunctions.sysdate());
		mailSender.setStatusMail(EnumStatusMail.CREATED);
		mailSenderDAO.save(mailSender);
	}

}
