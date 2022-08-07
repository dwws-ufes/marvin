package br.ufes.informatica.marvin.academicControl.enums;

public enum EnumRequestSituation {
	WAITING("Waiting"), //
	UNDER_ANALYSIS("Under Analysis"), //
	REFUSED("Refused"), //
	FINALIZED("Finalized");

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	EnumRequestSituation(String code) {
		this.code = code;
	}
}