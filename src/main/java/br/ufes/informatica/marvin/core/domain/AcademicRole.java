package br.ufes.informatica.marvin.core.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

/**
 * TODO: document this type.
 *
 * @author Gabriel Martins Miranda (garielmartinsmiranda@gmail.com)
 * @version 1.0
 */
@Entity
public class AcademicRole extends PersistentObjectSupport {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Name of the Course Coordinator academic role. */
	public static final String COURSECOORDINATOR_ROLE_NAME = "CourseCord";

	/** Name of the Enrolled Student academic role. */
	public static final String ENROLLED_STUDENT_ROLE_NAME = "EnrStudent";

	/** Name of the Alumni academic role. */
	public static final String ALUMNI_ROLE_NAME = "Alumni";

	/** The name that identifies the academic role across the system. */
	@Basic
	@Size(max = 10)
	private String name;

	/** Resource bundle key to the human-readable description of the academic role. */
	@Basic
	private String descriptionKey;

	/** Default constructor for JPA. */
	protected AcademicRole() {}

	/** Constructor. */
	public AcademicRole(String name, String descriptionKey) {
		this.name = name;
		this.descriptionKey = descriptionKey;
	}

	/** Getter for name. */
	public String getName() {
		return name;
	}

	/** Setter for name. */
	public void setName(String name) {
		this.name = name;
	}

	/** Getter for description. */
	public String getDescriptionKey() {
		return descriptionKey;
	}

	/** Setter for description. */
	public void setDescriptionKey(String descriptionKey) {
		this.descriptionKey = descriptionKey;
	}

	/** @see br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport#toString() */
	@Override
	public String toString() {
		return name;
	}
}
