package br.ufes.informatica.marvin.academicControl.enums;

public enum EnumStatusMail {
	CREATED("Created"), //
	SENT("Sent"), //
	ERROR("Error");

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	EnumStatusMail(String code) {
		this.code = code;
	}
}
