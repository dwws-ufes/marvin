package br.ufes.informatica.marvin.core.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

/**
 * TODO: document this type.
 *
 * @author Gabriel Martins Miranda (garielmartinsmiranda@gmail.com)
 * @version 1.0
 */
@Entity
public class CourseCoordination extends PersistentObjectSupport implements Comparable<CourseCoordination> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The coordination start date. */
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	/** The coordination end date. */
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	/** The course of coordination. */
	@NotNull
	@OneToOne
	private Course course;

	/** The course coordinator. */
	@NotNull
	@OneToOne
	private Academic academic;

	/** Getter for start date. */
	public Date getStartDate() {
		return startDate;
	}

	/** Setter for start date. */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/** Getter for end date. */
	public Date getEndDate() {
		return endDate;
	}

	/** Setter for end date. */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/** Getter for course. */
	public Course getCourse() {
		return course;
	}

	/** Setter for course. */
	public void setCourse(Course course) {
		this.course = course;
	}

	/** Getter for academic. */
	public Academic getAcademic() {
		return academic;
	}

	/** Setter for academic. */
	public void setAcademic(Academic academic) {
		this.academic = academic;
	}

	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(CourseCoordination o) {
		// Check if it's the same entity.
		return uuid.compareTo(o.uuid);
	}

	/** Returns a string containing: Course's name / Academic's name. */
	@Override
	public String toString() {
		return course.getName() + " / " + academic.getName();
	}

}
