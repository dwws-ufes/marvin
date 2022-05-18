package br.ufes.informatica.marvin.research.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Entity
public class Qualis extends PersistentObjectSupport implements Comparable<Qualis> {
	/** The unique identifier for a serializable class. */
	private static final long serialVersionUID = 1L;

	/** TODO: document this field. */
	@NotNull
	private String name;

	/** TODO: document this field. */
	@NotNull
	private Float scoreConference;

	@NotNull
	private Float scoreJournal;

	@NotNull
	private Date dtStart;

	@NotNull
	private boolean restrito;

	private Date dtEnd;

	public Qualis() {

	}

	/** Constructor. */
	public Qualis(String name, Float scoreConference, Float scoreJournal, boolean restrito) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.scoreConference = scoreConference;
		this.scoreJournal = scoreJournal;
		this.restrito = restrito;
	}

	/** Getter for name. */
	public String getName() {
		return name;
	}

	/** Setter for name. */
	public void setName(String name) {
		this.name = name;
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

	public Float getScoreConference() {
		return scoreConference;
	}

	public void setScoreConference(Float scoreConference) {
		this.scoreConference = scoreConference;
	}

	public Float getScoreJournal() {
		return scoreJournal;
	}

	public void setScoreJournal(Float scoreJournal) {
		this.scoreJournal = scoreJournal;
	}

	public boolean isRestrito() {
		return restrito;
	}

	public void setRestrito(boolean restrito) {
		this.restrito = restrito;
	}

	@Override
	public int compareTo(Qualis o) {
		int cmp = 0;
		cmp = o.name.compareTo(name);
		return cmp;
	}

}
