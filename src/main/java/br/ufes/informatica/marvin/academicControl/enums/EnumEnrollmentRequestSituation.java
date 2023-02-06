package br.ufes.informatica.marvin.academicControl.enums;

public enum EnumEnrollmentRequestSituation {
	WAITING("Waiting"), //
	REFUSED("Refused"), //
	APROVED("Aproved");

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
