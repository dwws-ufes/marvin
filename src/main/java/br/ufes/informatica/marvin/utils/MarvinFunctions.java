package br.ufes.informatica.marvin.utils;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

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

}
