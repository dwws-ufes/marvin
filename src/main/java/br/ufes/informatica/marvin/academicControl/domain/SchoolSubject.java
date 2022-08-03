package br.ufes.informatica.marvin.academicControl.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity
public class SchoolSubject extends PersistentObjectSupport {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(max = 15)
	private String code;

	@NotNull
	@Size(max = 255)
	private String name;

	@Size(max = 4000)
	private String description;

	@NotNull
	@Positive
	private Long credits;

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
}
