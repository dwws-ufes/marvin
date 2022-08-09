package br.ufes.informatica.marvin.academicControl.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.utils.MarvinFunctions;

@Entity
public class SubjectOffer extends PersistentObjectSupport implements Comparable<SubjectOffer> {

	private static final long serialVersionUID = 1L;

	@NotNull
	@OneToOne
	private Academic professor;

	@Basic
	@NotNull
	@Positive
	private Long numMaxStudents;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date creationDate;

	@NotNull
	@OneToOne
	private SchoolSubject schoolSubject;

	@NotNull
	@OneToOne
	private Period period;

	@NotNull
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ClassTime> classTime;

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

	public List<ClassTime> getClassTime() {
		return classTime;
	}

	public void setClassTime(List<ClassTime> classTime) {
		this.classTime = classTime;
	}

	@PrePersist
	void setCreationDate() {
		this.creationDate = MarvinFunctions.sysdate();
	}

	@Override
	public int compareTo(SubjectOffer o) {
		return uuid.compareTo(o.uuid);
	}

}
