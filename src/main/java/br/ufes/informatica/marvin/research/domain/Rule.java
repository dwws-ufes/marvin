package br.ufes.informatica.marvin.research.domain;

import java.util.Date;

import javax.persistence.Entity;
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
public class Rule extends PersistentObjectSupport implements Comparable<Rule> {
	/** The unique identifier for a serializable class. */
	private static final long serialVersionUID = 1L;

	/** TODO: document this field. */

	/** TODO: document this field. */
	@NotNull
	private int qtdPassYears;

	/** TODO: document this field. */
	@NotNull
	private Float total;

	/** TODO: document this field. */
	private Float scoreJournal;

	/** TODO: document this field. */
	private Float scoreRestricted;

	/** TODO: document this field. */
	private Float scoreJournalRestricted;

	@NotNull
	private Date dtStart;

	private Date dtEnd;

	private boolean doctoral;

	@NotNull
	@ManyToOne
	private PPG ppg;

	public int getQtdPassYears() {
		return qtdPassYears;
	}

	public void setQtdPassYears(int qtdPassYears) {
		this.qtdPassYears = qtdPassYears;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public Float getScoreJournal() {
		return scoreJournal;
	}

	public void setScoreJournal(Float scoreJournal) {
		this.scoreJournal = scoreJournal;
	}

	public Float getScoreRestricted() {
		return scoreRestricted;
	}

	public void setScoreRestricted(Float scoreRestricted) {
		this.scoreRestricted = scoreRestricted;
	}

	public Float getScoreJournalRestricted() {
		return scoreJournalRestricted;
	}

	public void setScoreJournalRestricted(Float scoreJournalRestricted) {
		this.scoreJournalRestricted = scoreJournalRestricted;
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

	public boolean isDoctoral() {
		return doctoral;
	}

	public void setDoctoral(boolean doctoral) {
		this.doctoral = doctoral;
	}

	public PPG getPpg() {
		return ppg;
	}

	public void setPpg(PPG ppg) {
		this.ppg = ppg;
	}

	@Override
	public int compareTo(Rule o) {
		int cmp = (int) (getId() - o.getId());
		return cmp;
	}

}
