package br.ufes.informatica.marvin.academicControl.enums;

public enum EnumSchoolSubjectType {
	REGULAR_DISCIPLINE("Regular Discipline"), //
	OCCASIONAL_DISCIPLINE("Occasional Discipline"), //
	SPECIAL_TOPIC("Special Topic"), //
	TEACHING_INTERNSHIP("Teaching Internship"), //
	DIRECTED_STUDY("Directed Study"), //
	THEMATIC_SEMINAR("Thematic Seminar"), //
	MASTERS_DISSERTATION("Masters dissertation"), //
	DOCT_QUALI_EXAMINATION("Doctorate Qualifying Examination"), //
	DOCT_THESIS_PROP_EXAMINATION("Doctoral Thesis Proposal Examination"), //
	DOCTORAL_THESIS("Doctoral thesis");

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	EnumSchoolSubjectType(String code) {
		this.code = code;
	}
}
