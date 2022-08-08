package br.ufes.informatica.marvin.academicControl.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import br.ufes.informatica.marvin.academicControl.enums.EnumDeadlineType;

@Entity
public class Deadline extends PersistentObjectSupport implements Comparable<Deadline> {
	private static final long serialVersionUID = 1L;

	@Basic
	@NotNull
	@Size(max = 50)
	private String name;

	@Basic
	@Size(max = 255)
	private String description;

	@Basic
	@NotNull
	private Long daysToReply;

	@NotNull
	@Enumerated(EnumType.STRING)
	private EnumDeadlineType deadlineType;

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

	public EnumDeadlineType getDeadlineType() {
		return deadlineType;
	}

	public void setDeadlineType(EnumDeadlineType deadlineType) {
		this.deadlineType = deadlineType;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int compareTo(Deadline o) {
		return uuid.compareTo(o.uuid);
	}
}
