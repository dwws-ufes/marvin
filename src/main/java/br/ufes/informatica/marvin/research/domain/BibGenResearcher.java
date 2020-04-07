package br.ufes.informatica.marvin.research.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import br.ufes.informatica.marvin.core.domain.Academic;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Entity
public class BibGenResearcher extends PersistentObjectSupport implements Comparable<BibGenResearcher> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** TODO: document this field. */
	@ManyToOne
	private Academic researcher;

	/** TODO: document this field. */
	private Integer startYear;

	/** TODO: document this field. */
	private Integer endYear;

	/** Constructor. */
	protected BibGenResearcher() {}

	/** Constructor. */
	public BibGenResearcher(Academic researcher, Integer startYear, Integer endYear) {
		this.researcher = researcher;
		this.startYear = startYear;
		this.endYear = endYear;
	}

	/** Getter for researcher. */
	public Academic getResearcher() {
		return researcher;
	}

	/** Setter for researcher. */
	public void setResearcher(Academic researcher) {
		this.researcher = researcher;
	}

	/** Getter for startYear. */
	public Integer getStartYear() {
		return startYear;
	}

	/** Setter for startYear. */
	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	/** Getter for endYear. */
	public Integer getEndYear() {
		return endYear;
	}

	/** Setter for endYear. */
	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}

	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(BibGenResearcher o) {
		return researcher.compareTo(o.researcher);
	}

	/** @see br.ufes.inf.nemo.jbutler.ejb.domain.DomainObjectSupport#equals(java.lang.Object) */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BibGenResearcher)) return false;
		return researcher.equals(((BibGenResearcher) obj).researcher);
	}

	/** @see br.ufes.inf.nemo.jbutler.ejb.domain.DomainObjectSupport#hashCode() */
	@Override
	public int hashCode() {
		return researcher.hashCode();
	}

}
