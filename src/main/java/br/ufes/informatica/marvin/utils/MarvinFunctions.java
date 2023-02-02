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

import org.omnifaces.util.Faces;
import org.primefaces.model.file.UploadedFile;

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

	public static void sendMail(String addressee, String subject, String text) {
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
			message.setText(text);
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
