package br.ufes.informatica.marvin.research.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import br.ufes.informatica.marvin.core.domain.Role;

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

	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	private Role role = new Role();

	public Rule() {

	}

	/** Constructor. */
	public Rule(Role role, int qtdPassYears, Float total) {
		// TODO Auto-generated constructor stub
		this.role = role;
		this.qtdPassYears = qtdPassYears;
		this.total = total;
	}

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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int compareTo(Rule o) {
		int cmp = (int) (getId() - o.getId());
		return cmp;
	}

}
