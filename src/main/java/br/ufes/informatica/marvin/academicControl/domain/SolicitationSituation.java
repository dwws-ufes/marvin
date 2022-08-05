package br.ufes.informatica.marvin.academicControl.domain;

public enum SolicitationSituation {
	WAITING(0), //
	COMPLETED(1), //
	REFUSED(2);

	private Long code;

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	SolicitationSituation(int i) {
		this.code = Long.valueOf(i);
	}
}
