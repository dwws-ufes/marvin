package br.ufes.informatica.marvin.core.application;

import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import br.ufes.informatica.marvin.core.domain.MarvinConfiguration;
import br.ufes.informatica.marvin.core.exceptions.MailerException;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Singleton
public class Mailer implements Serializable {
	/** TODO: document this field. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(Mailer.class.getCanonicalName());

	// FIXME: implement i18n for this.
	/** The path (in the EJB module) for the e-mail templates. */
	private static final String TEMPLATE_FOLDER_PATH = "marvin/mailer/en_US/";

	/** The file extension used by templates. */
	private static final String TEMPLATE_FILE_EXTENSION = ".twig";

	/** TODO: document this field. */
	private static final int PORT_NUMBER_SSL = 465;

	/** TODO: document this field. */
	private static final int PORT_NUMBER_TLS = 587;

	/** TODO: document this field. */
	@EJB
	private CoreInformation coreInfo;

	/**
	 * TODO: document this method.
	 * 
	 * @throws MailerException
	 */
	@Inject
	private void init() throws MailerException {
		try {
			// Ignores certificates when sending e-mail. Code obtained at
			// http://respostas.guj.com.br/17707-envio-de-e-mail-esta-gerando-excecoes.
			MarvinConfiguration config = coreInfo.getCurrentConfig();
			int port = config.getSmtpServerPort();
			if (port == PORT_NUMBER_SSL || port == PORT_NUMBER_TLS) {
				final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
					@Override
					public void checkClientTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {}

					@Override
					public void checkServerTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {}

					@Override
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}
				} };
				final SSLContext sc = SSLContext.getInstance(port == PORT_NUMBER_SSL ? "SSL" : "TLS");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				SSLContext.setDefault(sc);
			}
		}
		catch (Exception e) {
			throw new MailerException(e);
		}
	}

	/**
	 * TODO: document this method.
	 * 
	 * @param mailEvent
	 */
	@Asynchronous
	public void sendEmail(@Observes MailEvent mailEvent) throws MailerException {
		sendEmail(mailEvent.getEmailAddress(), mailEvent.getMailerTemplate(), mailEvent.getDataModel());
	}

	/**
	 * TODO: document this method.
	 * 
	 * @param emailAddress
	 * @param templateName
	 * @param dataModel
	 * @throws MailerException
	 */
	public void sendEmail(String emailAddress, MailerTemplate mailerTemplate, Map<String, Object> dataModel) throws MailerException {
		try {
			// Loads the e-mail template file.
			JtwigTemplate template = JtwigTemplate.classpathTemplate(TEMPLATE_FOLDER_PATH + mailerTemplate + TEMPLATE_FILE_EXTENSION);
      JtwigModel model = JtwigModel.newModel(dataModel);

			// Merges the template with the data model and writes the result to a string.
			String emailMsg = template.render(model);

			// Uses the first line as subject, if possible.
			int idx = emailMsg.indexOf('\n');
			String subject = "";
			if (idx != -1) {
				subject = emailMsg.substring(0, idx);
				emailMsg = emailMsg.substring(idx + 1);
			}

			// Obtains Marvin's configuration.
			MarvinConfiguration config = coreInfo.getCurrentConfig();
			int port = config.getSmtpServerPort();

			// Sends the message.
			logger.log(Level.INFO, "Sending message using template {0} to e-mail {1}. Subject: {2}", new Object[] { mailerTemplate + TEMPLATE_FILE_EXTENSION, emailAddress, subject });
			Email email = new SimpleEmail();
			email.setHostName(config.getSmtpServerAddress());
			email.setSmtpPort(port);
			email.setSSLCheckServerIdentity(false);
			email.setSSLOnConnect(port == PORT_NUMBER_SSL);
			email.setStartTLSEnabled(port == PORT_NUMBER_TLS);
			email.setAuthenticator(new DefaultAuthenticator(config.getSmtpUsername(), config.getSmtpPassword()));
			email.setFrom(config.getSmtpUsername());
			email.setSubject(subject);
			email.setMsg(emailMsg);
			email.addTo(emailAddress);
			email.send();
			logger.log(Level.INFO, "Successfuly sent message using template {0} to e-mail {1}. Subject: {2}", new Object[] { mailerTemplate + TEMPLATE_FILE_EXTENSION, emailAddress, subject });
		}
		catch (Exception e) {
			throw new MailerException(e);
		}
	}
}
