package br.ufes.informatica.marvin.academicControl.enums;

public enum EnumSolicitationType {
	DIPLOMA_REQUEST(1), //
	REUSE_OF_CREDITS(2);

	private Long code;

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	EnumSolicitationType(int i) {
		this.code = Long.valueOf(i);
	}
}
