package br.ufes.informatica.marvin.academicControl.enums;

public enum EnumEnrollmentRequestSituation {
	WAITING("Waiting"), //
	REGISTRED("Registred"), //
	REFUSED("Refused"), //
	APROVED("Aproved"), //
	REPROVED_BY_NOTE("Reproved by note"), //
	REPROVED_BY_LACK("Reproved by lack");

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	EnumEnrollmentRequestSituation(String code) {
		this.code = code;
	}
}
