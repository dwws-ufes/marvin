package br.ufes.informatica.marvin.research.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity
public class Venue extends PersistentObjectSupport implements Comparable<Venue> {

	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	private String acronym;

	private String issn;

	@NotNull
	private String name;

	@Enumerated(EnumType.STRING)
	@NotNull
	private VenueCategory category;

	protected Venue() {}

	public Venue(String name, String category) {
		// TODO Auto-generated constructor stub
		this.name = name;
		setCategory(category);
	}

	public Venue(String name) {
		this.name = name;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/** Getter for category. */
	public VenueCategory getCategory() {
		return category;
	}

	/** Setter for category. */
	public void setCategory(VenueCategory category) {
		this.category = category;
	}

	/** Setter for category. */
	public void setCategory(String category) {
		if (category.equals("Conference")) this.category = VenueCategory.CONFERENCE;
		else this.category = VenueCategory.JOURNAL;
	}

	@Override
	public int compareTo(Venue o) {
		int cmp = 0;

		// Compares by name.
		cmp = name.compareTo(o.name);
		return cmp;
	}

}
