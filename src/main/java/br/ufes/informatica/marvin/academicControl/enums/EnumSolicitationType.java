package br.ufes.informatica.marvin.academicControl.enums;

public enum EnumSolicitationType {
	DIPLOMA_REQUEST("Diploma Request"), //
	REUSE_OF_CREDITS("Reuse of Credits");

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	EnumSolicitationType(String string) {
		this.code = String.valueOf(string);
	}
}
