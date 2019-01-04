package br.ufes.informatica.marvin.core.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity
public class EducationInstituition extends PersistentObjectSupport implements Comparable<EducationInstituition> {

	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The educational instituition where the Education has been concluded. */
	@Basic
	@NotNull
	@Size(max = 100)
	private String name;

	/** Acronym of the institution that is using Marvin. */
	@Basic
	@Size(max = 10)
	private String institutionAcronym;

	/** The state where the Education has been concluded. */
	@Basic
	@NotNull
	@Size(max = 20)
	private String state;

	/** The country where the Education has been concluded. */
	@Basic
	@NotNull
	@Size(max = 30)
	private String country;

	/** Getter for name */
	public String getName() {
		return name;
	}

	/** Setter for name */
	public void setName(String name) {
		this.name = name;
	}

	/** Getter for Institution Acronym */
	public String getInstitutionAcronym() {
		return institutionAcronym;
	}

	/** Setter for Institution Acronym */
	public void setInstitutionAcronym(String institutionAcronym) {
		this.institutionAcronym = institutionAcronym;
	}

	/** Getter for state. */
	public String getState() {
		return state;
	}

	/** Setter for state. */
	public void setState(String state) {
		this.state = state;
	}

	/** Getter for country. */
	public String getCountry() {
		return country;
	}

	/** Setter for country. */
	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int compareTo(EducationInstituition e) {
		return name.compareTo(e.getName());
	}

}
