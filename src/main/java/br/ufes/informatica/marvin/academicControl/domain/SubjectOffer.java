package br.ufes.informatica.marvin.academicControl.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import br.ufes.informatica.marvin.core.domain.Academic;

@Entity
public class SubjectOffer extends PersistentObjectSupport {

	private static final long serialVersionUID = 1L;

	@NotNull
	@OneToOne
	private Academic professor;

	@NotNull
	@Positive
	private Long numMaxStudents;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@NotNull
	@OneToOne
	private SchoolSubject schoolSubject;

	@OneToOne
	private Period period;

	public Long getNumMaxStudents() {
		return numMaxStudents;
	}

	public void setNumMaxStudents(Long numMaxStudents) {
		this.numMaxStudents = numMaxStudents;
	}
	
	public Academic getProfessor() {
		return professor;
	}

	public void setProfessor(Academic professor) {
		this.professor = professor;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public SchoolSubject getSchoolSubject() {
		return schoolSubject;
	}

	public void setSchoolSubject(SchoolSubject schoolSubject) {
		this.schoolSubject = schoolSubject;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}
}
