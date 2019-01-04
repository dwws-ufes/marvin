package br.ufes.informatica.marvin.core.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

/**
 * TODO: document this type.
 *
 * @author Gabriel Martins Miranda (garielmartinsmiranda@gmail.com)
 * @version 1.0
 */
@Entity
public class Course extends PersistentObjectSupport implements Comparable<Course> {

	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The course's name. */
	@Basic
	@NotNull
	@Size(max = 100)
	private String name;

	/** The course's code. */
	@Basic
	@NotNull
	private Long code;

	/** The academic level of a course. */
	@Enumerated(EnumType.STRING)
	@NotNull
	private AcademicLevel academicLevel;

	/** The timestamp of the moment this course was created. */
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date creationDate;

	/** The last time the data about the course was updated. */
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date lastUpdateDate;

	/** Getter for name. */
	public String getName() {
		return name;
	}

	/** Setter for name. */
	public void setName(String name) {
		this.name = name;
	}

	/** Getter for academicLevel. */
	public AcademicLevel getAcademicLevel() {
		return academicLevel;
	}

	/** Setter for academicLevel. */
	public void setAcademicLevel(AcademicLevel academicLevel) {
		this.academicLevel = academicLevel;
	}

	/** Setter for academicLevel. */
	public void setAcademicLevel(String academicLevel) {
		if (academicLevel.equals("Undergraduate")) this.academicLevel = AcademicLevel.UNDERGRADUATE;
		else this.academicLevel = AcademicLevel.GRADUATE;
	}

	/** Getter for creationDate. */
	public Date getCreationDate() {
		return creationDate;
	}

	/** Setter for creationDate. */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/** Getter for lastUpdateDate. */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/** Setter for lastUpdateDate. */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/** Getter for code. */
	public Long getCode() {
		return code;
	}

	/** Setter for code. */
	public void setCode(Long code) {
		this.code = code;
	}

	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(Course o) {
		// Compare the persons' names
		if (name == null) return 1;
		if (o.name == null) return -1;
		int cmp = name.compareTo(o.name);
		if (cmp != 0) return cmp;

		// If it's the same name, check if it's the same entity.
		return uuid.compareTo(o.uuid);
	}

	/** @see br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport#toString() */
	@Override
	public String toString() {
		return name;
	}

	/** Enum for Course Academic Level */
	public enum AcademicLevel {
		UNDERGRADUATE("Undergraduate"), GRADUATE("Graduate");

		String name;

		/** Getter for academicLevel name. */
		public String getName() {
			return name;
		}

		/** Setter for academicLevel name. */
		public void setName(String name) {
			this.name = name;
		}

		AcademicLevel(String name) {
			this.name = name;
		}
	}
}
