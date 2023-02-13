package br.ufes.informatica.marvin.academicControl.enums;

public enum EnumRequestSituation {
	WAITING("Waiting", "Aguardando"), //
	UNDER_ANALYSIS("Under Analysis", "Em Análise"), //
	REFUSED("Refused", "Recusada"), //
	FINALIZED("Finalized", "Finalizada");

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

	EnumRequestSituation(String code, String description) {
		this.code = code;
		this.description = description;
	}
}