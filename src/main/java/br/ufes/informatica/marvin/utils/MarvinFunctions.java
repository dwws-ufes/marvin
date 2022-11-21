package br.ufes.informatica.marvin.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.primefaces.model.file.UploadedFile;

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

}
