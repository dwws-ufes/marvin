package br.ufes.informatica.marvin.academicControl.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.marvin.academicControl.domain.MailSender;

@Local
public interface MailSenderService extends CrudService<MailSender> {

	void createMailSender(String addressee, String subject, String text);

	List<MailSender> retrieveMailsNotSent();
}
