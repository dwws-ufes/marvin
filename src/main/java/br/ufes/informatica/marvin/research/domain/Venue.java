package br.ufes.informatica.marvin.research.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import br.ufes.informatica.marvin.core.domain.PPG;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Entity
public class Venue extends PersistentObjectSupport implements Comparable<Venue> {
	/** The unique identifier for a serializable class. */
	private static final long serialVersionUID = 1L;

	/** TODO: document this field. */
	private String acronym;

	/** TODO: document this field. */
	private String issn;

	/** TODO: document this field. */
	@NotNull
	private String name;

	/** TODO: document this field. */
	@Enumerated(EnumType.STRING)
	@NotNull
	private VenueCategory category;

	@NotNull
	private Date dtStart;

	private Date dtEnd;

	@NotNull
	@ManyToOne
	private PPG ppg;

	@NotNull
	@ManyToOne
	private Qualis qualis;

	/** Constructor. */
	public Venue(String acronym, String issn, String name, String category, Date dtStart, Date dtEnd) {
		// TODO Auto-generated constructor stub
		this.acronym = acronym;
		this.issn = issn;
		this.name = name;
		setCategory(category);
		this.dtStart = dtStart;
		this.dtEnd = dtEnd;
	}

	public Venue() {

	}

	/** Getter for acronym. */
	public String getAcronym() {
		return acronym;
	}

	/** Setter for acronym. */
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	/** Getter for issn. */
	public String getIssn() {
		return issn;
	}

	/** Setter for issn. */
	public void setIssn(String issn) {
		this.issn = issn;
	}

	/** Getter for name. */
	public String getName() {
		return name;
	}

	/** Setter for name. */
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
		if (category.equals("Conference"))
			this.category = VenueCategory.CONFERENCE;
		else
			this.category = VenueCategory.JOURNAL;
	}

	public Date getDtStart() {
		return dtStart;
	}

	public void setDtStart(Date dtStart) {
		this.dtStart = dtStart;
	}

	public Date getDtEnd() {
		return dtEnd;
	}

	public void setDtEnd(Date dtEnd) {
		this.dtEnd = dtEnd;
	}

	public PPG getPpg() {
		return ppg;
	}

	public void setPpg(PPG ppg) {
		this.ppg = ppg;
	}

	public Qualis getQualis() {
		return qualis;
	}

	public void setQualis(Qualis qualis) {
		this.qualis = qualis;
	}

	@Override
	public int compareTo(Venue o) {
		int cmp = 0;

		// Compares by name.
		cmp = name.compareTo(o.name);
		return cmp;
	}
}
