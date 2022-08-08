package br.ufes.informatica.marvin.academicControl.enums;

public enum EnumDeadlineType {
	DIPLOMA_REQUEST("Diploma Request"), //
	REUSE_OF_CREDITS("Reuse of Credits"), //
	REQUEST_REGISTRATION_NUMBER("Request Registration Number");

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	EnumDeadlineType(String code) {
		this.code = code;
	}
}