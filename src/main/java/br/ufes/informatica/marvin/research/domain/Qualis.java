package br.ufes.informatica.marvin.research.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

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
	private boolean restrito;

	@ManyToOne(fetch = FetchType.EAGER)
	private QualisValidity qualisValidity;

	/** Getter for name. */
	public String getName() {
		return name;
	}

	/** Setter for name. */
	public void setName(String name) {
		this.name = name;
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

	public QualisValidity getQualisValidity() {
		return qualisValidity;
	}

	public void setQualisValidity(QualisValidity qualisValidity) {
		this.qualisValidity = qualisValidity;
	}

	@Override
	public int compareTo(Qualis o) {
		int cmp = 0;
		cmp = o.name.compareTo(name);
		return cmp;
	}

}
