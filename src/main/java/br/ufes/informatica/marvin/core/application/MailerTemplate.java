package br.ufes.informatica.marvin.core.application;

public enum MailerTemplate {
	NEW_ACADEMIC_REGISTERED("NewAcademicRegistered"), RESET_PASSWORD("ResetPassword");

	/** TODO: document this field. */
	private String fileName;

	/** Constructor. */
	private MailerTemplate(String fileName) {
		this.fileName = fileName;
	}

	/** @see java.lang.Enum#toString() */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return fileName;
	}
}
