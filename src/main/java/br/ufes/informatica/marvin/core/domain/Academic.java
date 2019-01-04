package br.ufes.informatica.marvin.core.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.informatica.marvin.people.domain.Person;
import br.ufes.informatica.marvin.people.domain.Telephone;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Entity
public class Academic extends Person {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Short name to use when there isn't much space. */
	@Basic
	@NotNull
	@Size(max = 15)
	private String shortName;

	/** Electronic address, which also serves as username for identification. */
	@Basic
	@Size(max = 100)
	private String email;

	/** The password, which identifies the user. */
	@Basic
	@Size(max = 32)
	private String password;

	/** The number that identifies the academic in the Lattes platform, if she's a researcher. */
	@Basic
	private Long lattesId;

	/** The code that has to be provided in order to reset an academic's password. */
	@Basic
	@Size(max = 40)
	private String passwordCode;

	/** Phone numbers. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Telephone> telephones;

	/** Roles for this user. */
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;

	/** Academic Roles for this user. */
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<AcademicRole> academicRoles;

	/** The timestamp of the moment this academic was created. */
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date creationDate;

	/** The last time the data about the user was updated. */
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date lastUpdateDate;

	/** The last time the user logged in the system. */
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginDate;

	/** Getter for shortName. */
	public String getShortName() {
		return shortName;
	}

	/** Setter for shortName. */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/** Getter for email. */
	public String getEmail() {
		return email;
	}

	/** Setter for email. */
	public void setEmail(String email) {
		this.email = email;
	}

	/** Getter for password. */
	public String getPassword() {
		return password;
	}

	/** Setter for password. */
	public void setPassword(String password) {
		this.password = password;
	}

	/** Getter for lattesId. */
	public Long getLattesId() {
		return lattesId;
	}

	/** Setter for lattesId. */
	public void setLattesId(Long lattesId) {
		this.lattesId = lattesId;
	}

	/** Getter for passwordCode. */
	public String getPasswordCode() {
		return passwordCode;
	}

	/** Setter for passwordCode. */
	public void setPasswordCode(String passwordCode) {
		this.passwordCode = passwordCode;
	}

	/** Getter for telephones. */
	public Set<Telephone> getTelephones() {
		return telephones;
	}

	/** Setter for telephones. */
	public void setTelephones(Set<Telephone> telephones) {
		this.telephones = telephones;
	}

	/** Getter for roles. */
	public Set<Role> getRoles() {
		return roles;
	}

	/** Setter for roles. */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/** Getter for academic roles. */
	public Set<AcademicRole> getAcademicRoles() {
		return academicRoles;
	}

	/** Setter for academic roles. */
	public void setAcademicRoles(Set<AcademicRole> academicRoles) {
		this.academicRoles = academicRoles;
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

	/** Getter for lastLoginDate. */
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	/** Setter for lastLoginDate. */
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * Assigns a role to an academic, i.e., adds the role to the set of roles.
	 * 
	 * @param role
	 *          The role to assign.
	 */
	public void assignRole(Role role) {
		if (roles == null) roles = new HashSet<>();
		roles.add(role);
	}

	/**
	 * Assigns a academic role to an academic, i.e., adds the academic role to the set of academic roles.
	 * 
	 * @param academicRole
	 *          The academic role to assign.
	 */
	public void assignAcademicRole(AcademicRole academicRole) {
		if (academicRoles == null) academicRoles = new HashSet<>();
		academicRoles.add(academicRole);
	}

	public void unassignRole(Role role) {
		if (roles.contains(role)) roles.remove(role);
	}

	public void unassignAcademicRole(AcademicRole academicRole) {
		if (academicRoles.contains(academicRole)) academicRoles.remove(academicRole);
	}
}
