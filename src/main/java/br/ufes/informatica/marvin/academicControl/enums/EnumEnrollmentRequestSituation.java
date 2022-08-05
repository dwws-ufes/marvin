package br.ufes.informatica.marvin.academicControl.enums;

public enum EnumEnrollmentRequestSituation {
	WAITING(0), //
	REGISTRED(1), //
	REFUSED(2), //
	APROVED(3), //
	REPROVED_BY_NOTE(4), //
	REPROVED_BY_LACK(5);

	private Long code;

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	EnumEnrollmentRequestSituation(int i) {
		this.code = Long.valueOf(i);
	}
}
