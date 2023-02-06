package br.ufes.informatica.marvin.academicControl.application;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.ufes.informatica.marvin.academicControl.domain.MailSender;
import br.ufes.informatica.marvin.academicControl.domain.Request;
import br.ufes.informatica.marvin.academicControl.enums.EnumStatusMail;
import br.ufes.informatica.marvin.utils.MarvinFunctions;

@SuppressWarnings("deprecation")
@ManagedBean(eager = true)
@ApplicationScoped
public class BackgroundJobManager {

	private ScheduledExecutorService scheduler;

	@EJB
	private MailSenderService mailSenderService;

	@EJB
	private RequestService requestService;

	private static final Logger logger = Logger.getLogger(BackgroundJobManager.class.getCanonicalName());

	@PostConstruct
	public void init() {
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new MailJob(), 0, 1, TimeUnit.MINUTES);
		scheduler.scheduleAtFixedRate(new CreateMailRequestWithoutAnswerJob(), 0, 1, TimeUnit.DAYS);
	}

	@PreDestroy
	public void destroy() {
		scheduler.shutdownNow();
	}

	private static String generateEmailByListOfRequests(List<Request> requests) {
		int line = 1;
		String styleOddLine = "<td style = \"background-color:#D2E4FC;border-color:inherit;text-align:center;vertical-align:top\">";
		String styleEvenLine = "<td style = \"border-color:inherit;text-align:center;vertical-align:top\">";
		StringBuilder email = new StringBuilder();
		email.append("<html>")//
				.append("<head>")//
				.append("<meta charset=\"UTF-8\"/>")//
				.append("</head>")//
				.append("<body>")//
				.append("<table style = \"border-collapse:collapse;border-spacing:0;\">")//
				.append("<thead>")//
				.append("<tr>")//
				.append("<th style = \"background-color:#3166ff;border-color:inherit;position:-webkit-sticky;position:sticky;text-align:center;top:-1px; vertical-align:top;will-change:transform\">Nome do Aluno</th>")//
				.append("<th style = \"background-color:#3166ff;border-color:inherit;position:-webkit-sticky;position:sticky;text-align:center;top:-1px; vertical-align:top;will-change:transform\">Tipo de solicitação</th>")//
				.append("<th style = \"background-color:#3166ff;border-color:inherit;position:-webkit-sticky;position:sticky;text-align:center;top:-1px; vertical-align:top;will-change:transform\">Tempo restante</th>")//
				.append("</tr>")//
				.append("</thead>")//
				.append("<tbody>");

		for (Request request : requests) {
			String style = line % 2 == 0 ? styleEvenLine : styleOddLine;
			email.append("<tr>")//
					.append(style + request.getRequester().getName() + "</td>")//
					.append(style + request.getDeadline().getDeadlineType().getDescription() + "</td>")//
					.append(style + MarvinFunctions.dateDifferenceInDays(request.getRequestDate(),
							request.getDeadline().getDaysToReply()) + "</td>")//
					.append("</tr>");
			line++;
		}

		email.append("</tbody>")//
				.append("</table>")//
				.append("</body>")//
				.append("</html>");

		return email.toString();
	}

	private class CreateMailRequestWithoutAnswerJob implements Runnable {
		@Override
		public void run() {
			logger.log(Level.INFO, "Listing emails to sent...");

			List<Request> requests = requestService.requestWithoutAnswer().stream()//
					.filter(c -> c.calculateLeftDaysInRequest() <= c.getDeadline().getMaxDaysToSendMail())//
					.sorted(Comparator.comparingLong(Request::calculateLeftDaysInRequest))//
					.collect(Collectors.toList());

			if (!requests.isEmpty()) {
				/* TODO trocar e-mail destino para o e-mail cadastrado da secretaria */
				mailSenderService.createMailSender("bruno.n.oliveira@edu.ufes.br",
						"Marvin - Solicitações com prazo perto de expirar", generateEmailByListOfRequests(requests));
			}

		}
	}

	private class MailJob implements Runnable {
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