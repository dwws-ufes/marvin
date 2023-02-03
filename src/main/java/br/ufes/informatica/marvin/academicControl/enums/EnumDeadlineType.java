package br.ufes.informatica.marvin.academicControl.enums;

public enum EnumDeadlineType {
	DIPLOMA_REQUEST("Diploma Request", "Solicitação de Diploma"), //
	REUSE_OF_CREDITS("Reuse of Credits", "Aproveitamento de créditos"), //
	REQUEST_REGISTRATION_NUMBER("Request Registration Number", "Solicitação de número de matrícula");

	private String code;
	private String description;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	EnumDeadlineType(String code, String description) {
		this.code = code;
		this.description = description;
	}
}