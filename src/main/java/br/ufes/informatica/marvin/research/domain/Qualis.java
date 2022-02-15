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
	private Float value;

	@NotNull
	private Date dtStart;

	private Date dtEnd;

	public Qualis() {

	}

	/** Constructor. */
	public Qualis(String name, Float value) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.value = value;
	}

	/** Getter for name. */
	public String getName() {
		return name;
	}

	/** Setter for name. */
	public void setName(String name) {
		this.name = name;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
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

	@Override
	public int compareTo(Qualis o) {
		int cmp = 0;
		cmp = o.name.compareTo(name);
		return cmp;
	}

}
