package br.ufes.informatica.marvin.research.domain;

/** Venue category */
public enum VenueCategory {
	CONFERENCE("Conference"), JOURNAL("Journal");

	String name;

	/** Getter for category name. */
	public String getName() {
		return name;
	}

	/** Setter for category name. */
	public void setName(String name) {
		this.name = name;
	}

	VenueCategory(String name) {
		this.name = name;
	}
}
