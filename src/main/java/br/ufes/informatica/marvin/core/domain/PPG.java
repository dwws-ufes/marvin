package br.ufes.informatica.marvin.core.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity
public class PPG extends PersistentObjectSupport implements Comparable<PPG> {
	private static final long serialVersionUID = 1L;

	@NotNull
	private String name;

	@Size(max = 10)
	private String acronym;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	@Override
	public int compareTo(PPG o) {
		int cmp = 0;
		cmp = o.name.compareTo(name);
		return cmp;
	}
}
