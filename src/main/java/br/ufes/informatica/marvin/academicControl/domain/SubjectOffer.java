package br.ufes.informatica.marvin.academicControl.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity
public class SubjectOffer extends PersistentObjectSupport {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Positive
	private Long maxNumStudents;

	/* TODO create relation with the table Academic */
	@NotNull
	@Size(max = 50)
	private String professorName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@OneToOne
	private SchoolSubject schoolSubject;

	@OneToOne
	private Period period;

	public Long getMaxNumStudents() {
		return maxNumStudents;
	}

	public void setMaxNumStudents(Long maxNumStudents) {
		this.maxNumStudents = maxNumStudents;
	}

	public String getProfessorName() {
		return professorName;
	}

	public void setProfessorName(String professorName) {
		this.professorName = professorName;
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
