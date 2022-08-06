package br.ufes.informatica.marvin.utils;

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

}
