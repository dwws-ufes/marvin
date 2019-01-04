package br.ufes.informatica.marvin.core.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class CourseAttendance extends PersistentObjectSupport implements Comparable<CourseAttendance> {

	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The course attendance's start date. */
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	/** The course attendance's end date. */
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	/** The course attendance's situation in the course. */
	@NotNull
	@Enumerated(EnumType.STRING)
	private Situation situation;

	/** The course of academic. */
	@NotNull
	@OneToOne
	private Course course;

	/** The academic enrolled in course. */
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

	/** Getter for situation. */
	public Situation getSituation() {
		return situation;
	}

	/** Setter for situation. */
	public void setSituation(Situation situation) {
		this.situation = situation;
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

	/** The academic situation in the course */
	public enum Situation {
		ACTIVE("Active"), GRADUATED("Graduated"), TERMINATED("Terminated");

		/** TODO: document this field. */
		String name;

		/** Constructor. */
		Situation(String name) {
			this.name = name;
		}

		/** @see java.lang.Enum#toString() */
		@Override
		public String toString() {
			return name;
		}

		/** Returns an enum item, searching by name */
		public static Situation getByName(String name) {
			switch (name) {
				case "Graduated": {
					return Situation.GRADUATED;
				}
				case "Terminated": {
					return Situation.TERMINATED;
				}
				default: {
					return Situation.ACTIVE;
				}
			}
		}
	}

	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(CourseAttendance o) {
		// Check if it's the same entity.
		return uuid.compareTo(o.uuid);
	}

	/** Returns a string containing: Course's name / Academic's name. */
	@Override
	public String toString() {
		return course.getName() + " / " + academic.getName();
	}

	/** Returns only the start year of the course attendance */
	public String getStartYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		return String.valueOf(calendar.get(Calendar.YEAR));
	}

	/** Returns only the end year of the course attendance */
	public String getEndYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		return String.valueOf(calendar.get(Calendar.YEAR));
	}
}
