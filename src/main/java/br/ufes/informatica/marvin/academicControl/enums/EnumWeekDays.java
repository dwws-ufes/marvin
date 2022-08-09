package br.ufes.informatica.marvin.academicControl.enums;

public enum EnumWeekDays {
	MONDAY("Monday"), //
	TUESDAY("Tueday"), //
	WEDNESDEY("Wednesday"), //
	THURSDAY("Tursday"), //
	FRIDAY("Friday"), //
	SATURDAY("Saturday"), //
	SUNDAY("Sunday");

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	EnumWeekDays(String code) {
		this.code = code;
	}
}