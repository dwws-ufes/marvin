package br.ufes.informatica.marvin.academicControl.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import br.ufes.informatica.marvin.academicControl.enums.EnumSchoolSubjectType;

@Entity
public class SchoolSubject extends PersistentObjectSupport implements Comparable<SchoolSubject> {
	private static final long serialVersionUID = 1L;

	@Basic
	@NotNull
	@Size(max = 15)
	private String code;

	@Basic
	@NotNull
	@Size(max = 255)
	private String name;

	@Basic
	@Size(max = 4000)
	private String description;

	@NotNull
	@Enumerated(EnumType.STRING)
	private EnumSchoolSubjectType type;

	@Basic
	@Size(max = 4000)
	private String summary;

	@Basic
	@Size(max = 4000)
	private String bibliography;

	@Basic
	@NotNull
	@Positive
	private Long credits;

	@Basic
	@NotNull
	@Positive
	private Long workload;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCredits() {
		return credits;
	}

	public void setCredits(Long credits) {
		this.credits = credits;
	}

	public Long getWorkload() {
		return workload;
	}

	public void setWorkload(Long workload) {
		this.workload = workload;
	}

	public EnumSchoolSubjectType getType() {
		return type;
	}

	public void setType(EnumSchoolSubjectType type) {
		this.type = type;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getBibliography() {
		return bibliography;
	}

	public void setBibliography(String bibliography) {
		this.bibliography = bibliography;
	}

	@Override
	public int compareTo(SchoolSubject o) {
		return uuid.compareTo(o.uuid);
	}
}
