package br.ufes.informatica.marvin.academicControl.application;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.ufes.informatica.marvin.academicControl.domain.MailSender;
import br.ufes.informatica.marvin.academicControl.enums.EnumStatusMail;
import br.ufes.informatica.marvin.utils.MarvinFunctions;

@SuppressWarnings("deprecation")
@ManagedBean(eager = true)
@ApplicationScoped
public class BackgroundJobManager {

	private ScheduledExecutorService scheduler;

	@EJB
	private MailSenderService mailSenderService;

	private static final Logger logger = Logger.getLogger(BackgroundJobManager.class.getCanonicalName());

	@PostConstruct
	public void init() {
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new SomeDailyJob(), 0, 1, TimeUnit.MINUTES);
	}

	@PreDestroy
	public void destroy() {
		scheduler.shutdownNow();
	}

	public class SomeDailyJob implements Runnable {

		@Override
		public void run() {
			logger.log(Level.INFO, "Listing emails to sent...");
			List<MailSender> emails = mailSenderService.retrieveMailsNotSent();
			if (emails.isEmpty())
				logger.log(Level.INFO, "Not exist emails to sent...");
			for (MailSender mail : emails) {
				logger.log(Level.INFO, "Send email to " + mail.getAddressee());
				String error = MarvinFunctions.sendMail(mail.getAddressee(), mail.getSubject(), mail.getText());

				if (Objects.isNull(error))
					logger.log(Level.INFO, "Email successfully sent");
				else {
					logger.log(Level.INFO, "Error sending email");
					mail.setError(error);
				}
				mail.setSentDate(MarvinFunctions.sysdate());
				mail.setStatusMail(Objects.isNull(error) ? EnumStatusMail.SENT : EnumStatusMail.ERROR);
				mailSenderService.getDAO().save(mail);
			}
		}

	}

}