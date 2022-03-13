package br.ufes.informatica.marvin.core.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Entity
public class Role extends PersistentObjectSupport {
	/** The unique identifier for a serializable class. */
	private static final long serialVersionUID = 1L;

	/** Name of the System Administrator role. */
	public static final String SYSADMIN_ROLE_NAME = "SysAdmin";

	/** Name of the Professor role. */
	public static final String PROFESSOR_ROLE_NAME = "Professor";

	/** Name of the Staff role. */
	public static final String STAFF_ROLE_NAME = "Staff";

	/** Name of the Student role. */
	public static final String STUDENT_ROLE_NAME = "Student";

	/** Name of the Visitor role. */
	public static final String VISITOR_ROLE_NAME = "Visitor";

	/** Name of the Visitor role. */
	public static final String MASTER_ROLE_NAME = "Master";

	/** Name of the Visitor role. */
	public static final String DOCTORAL_ROLE_NAME = "Doctoral";

	/** The name that identifies the role across the system. */
	@Basic
	@Size(max = 11)
	private String name;

	/** Resource bundle key to the human-readable description of the role. */
	@Basic
	private String descriptionKey;

	/** Default constructor for JPA. */
	public Role() {
	}

	/** Constructor. */
	public Role(String name, String descriptionKey) {
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

	@Override
	public String toString() {
		return name;
	}
}
