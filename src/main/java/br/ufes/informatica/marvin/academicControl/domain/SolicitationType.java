package br.ufes.informatica.marvin.academicControl.domain;

public enum SolicitationType {
	ENROLLMENT_REQUEST(1), //
	DIPLOMA_REQUEST(2), //
	REUSE_OF_CREDITS(3);

	Long code;

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	private SolicitationType(int i) {
		this.code = Long.valueOf(i);
	}
}
