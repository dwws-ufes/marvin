package br.ufes.informatica.marvin.academicControl.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import br.ufes.informatica.marvin.academicControl.enums.EnumSolicitationType;

@Entity
public class Deadline extends PersistentObjectSupport implements Comparable<Deadline> {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(max = 50)
	private String name;

	@Size(max = 255)
	private String description;

	@Enumerated(EnumType.STRING)
	private EnumSolicitationType solicitationType;

	@NotNull
	private Long daysToReply;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getDaysToReply() {
		return daysToReply;
	}

	public void setDaysToReply(Long daysToReply) {
		this.daysToReply = daysToReply;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public EnumSolicitationType getSolicitationType() {
		return solicitationType;
	}

	public void setSolicitationType(EnumSolicitationType solicitationType) {
		this.solicitationType = solicitationType;
	}

	@Override
	public int compareTo(Deadline o) {
		return uuid.compareTo(o.uuid);
	}
}
