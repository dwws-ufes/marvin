package br.ufes.informatica.marvin.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Faces;
import org.primefaces.model.file.UploadedFile;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudException;
import br.ufes.informatica.marvin.academicControl.domain.Request;
import br.ufes.informatica.marvin.academicControl.enums.EnumRequestSituation;
import br.ufes.informatica.marvin.core.domain.Role;

public class MarvinFunctions {
	public static void showMessageInScreen(Severity severity, String str1, String str2) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(severity, str1, str2);
		context.addMessage(null, message);
	}

	public static void showMessageInScreen(Severity severity, String str1) {
		showMessageInScreen(severity, str1, null);
	}

	public static Date sysdate() {
		return new Date(System.currentTimeMillis());
	}

	public static <T> T nvl(T mayBeNull, T alternative) {
		return null == mayBeNull ? alternative : mayBeNull;
	}

	public static <T> T selectByExp(boolean exp, T value1, T value2) {
		return exp ? value1 : value2;
	}

	public static boolean isStaffOrAdmin() {
		return Faces.isUserInRole(Role.STAFF_ROLE_NAME) || Faces.isUserInRole(Role.SYSADMIN_ROLE_NAME);
	}

	public static String saveFileInServer(UploadedFile file) {
		try {
			if (Objects.nonNull(file) && Objects.nonNull(file.getFileName()) && file.getSize() > 0) {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
				String name = System.getProperty("java.io.tmpdir") + fmt.format(new Date()) + file.getFileName();
				File fileToSave = new File(name);
				InputStream is = file.getInputStream();
				OutputStream out = new FileOutputStream(fileToSave);
				byte buf[] = new byte[1024];
				int len;
				while ((len = is.read(buf)) > 0)
					out.write(buf, 0, len);
				is.close();
				out.close();
				return name;
			}
		} catch (Exception e) {
			MarvinFunctions.showMessageInScreen(FacesMessage.SEVERITY_ERROR, "Failed to save file to server!",
					e.getMessage());
		}

		return null;
	}

	public static String sendMail(String addressee, String subject, String text) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("marvinufes@gmail.com", "axqaxkmbbwvmqxvf");
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("marvinufes@gmail.com"));
			Address[] toUser = InternetAddress.parse(addressee);
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(subject);
			message.setContent(text, "text/html");
			Transport.send(message);
		} catch (MessagingException e) {
			return StringUtils.abbreviate(e.getMessage(), 4000);
		}
		return null;
	}

	public static void verifyAndThrowCrudException(CrudException crudException) throws CrudException {
		if (crudException != null)
			throw crudException;
	}

	public static Long dateDifferenceInDays(Date dateIni, Long daysOfRequest) {
		Long dif = MarvinFunctions.sysdate().getTime() - dateIni.getTime();
		Long differenceInDays = (dif / (1000 * 60 * 60 * 24)) % 365;
		differenceInDays = daysOfRequest - differenceInDays;
		return differenceInDays < 0L ? 0L : differenceInDays;
	}

	public static String generateTextRequestMail(Request request, EnumRequestSituation oldSituation, EnumRequestSituation newSituation) {	
		String email = "<html>\r\n" + 
				"   <head>\r\n" + 
				"      <meta charset=\"UTF-8\"/>\r\n" + 
				"   </head> \r\n" + 
				"   <p> " + request.getRequester().getName() + ", a sua solicitação teve a situação alterada. </p>\r\n" + 
				"   <body>\r\n" + 
				"      <table style = \"border-collapse:collapse;border-spacing:0;\">\r\n" + 
				"         <thead>\r\n" + 
				"            <tr>\r\n" + 
				"               <th style = \"background-color:#3166ff;border-color:inherit;position:-webkit-sticky;position:sticky;text-align:center;top:-1px; vertical-align:top;will-change:transform\" colspan=\"2\">Alteração da situação da Solicitação</th>\r\n" + 
				"            </tr>\r\n" + 
				"         </thead>\r\n" + 
				"         <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"               <td style = \"background-color:#D2E4FC;border-color:inherit;text-align:center;vertical-align:top\">Tipo de solicitação</td>\r\n" + 
				"               <td style = \"background-color:#D2E4FC;border-color:inherit;text-align:center;vertical-align:top\">" + request.getDeadline().getName() + "</td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"               <td style = \"border-color:inherit;text-align:center;vertical-align:top\">Antiga situação</td>\r\n" + 
				"               <td style = \"border-color:inherit;text-align:center;vertical-align:top\">"+  oldSituation.getDescription() + "</td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"               <td style = \"background-color:#D2E4FC;border-color:inherit;text-align:center;vertical-align:top\">Nova Situação</td>\r\n" + 
				"               <td style = \"background-color:#D2E4FC;border-color:inherit;text-align:center;vertical-align:top\">" + newSituation.getDescription() + "</td>\r\n" +
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"               <td style = \"border-color:inherit;text-align:center;vertical-align:top\">Prazo máximo de resposta (em dias)</td>\r\n" + 
				"               <td style = \"border-color:inherit;text-align:center;vertical-align:top\">" + request.getDeadline().getDaysToReply() + "</td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"               <td style = \"background-color:#D2E4FC;border-color:inherit;text-align:center;vertical-align:top\">Tempo restante para a reposta (em dias)</td>\r\n" + 
				"               <td style = \"background-color:#D2E4FC;border-color:inherit;text-align:center;vertical-align:top\">" + dateDifferenceInDays(request.getRequestDate(), request.getDeadline().getDaysToReply()) + "</td>\r\n" + 
				"            </tr>\r\n" + 
				"         </tbody>\r\n" + 
				"      </table>\r\n" + 
				"   </body>\r\n" + 
				"</html>";
		return email;
	}

}
