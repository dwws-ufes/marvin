package br.ufes.informatica.marvin.academicControl.persistence;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.MailSender;

@Local
public interface MailSenderDAO extends BaseDAO<MailSender> {
	List<MailSender> retrieveMailsNotSent();
}
