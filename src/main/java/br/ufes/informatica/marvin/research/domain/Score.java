package br.ufes.informatica.marvin.research.domain;

public class Score {

	private String name;

	private Float total;

	/** TODO: document this field. */
	private Float scoreJournal;

	/** TODO: document this field. */
	private Float scoreRestricted;

	/** TODO: document this field. */
	private Float scoreJournalRestricted;

	private boolean aproved;

	private String type;

	public Score(String name, String type, Float total, Float scoreJournal, Float scoreRestricted,
			Float scoreJournalRestricted, boolean aproved) {
		this.name = name;
		this.type = type;
		this.total = total;
		this.scoreJournal = scoreJournal;
		this.scoreRestricted = scoreRestricted;
		this.scoreJournalRestricted = scoreJournalRestricted;
		this.aproved = aproved;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public boolean isAproved() {
		return aproved;
	}

	public void setAproved(boolean aproved) {
		this.aproved = aproved;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
