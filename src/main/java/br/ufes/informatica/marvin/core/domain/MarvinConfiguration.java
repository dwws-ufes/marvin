package br.ufes.informatica.marvin.core.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Entity
public class MarvinConfiguration extends PersistentObjectSupport {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The timestamp of the moment this configuration came in effect. */
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	/** Acronym of the institution that is using Marvin. */
	@Basic
	@Size(max = 10)
	private String institutionAcronym;

	/** Address for the SMTP server that sends e-mail. */
	@NotNull
	private String smtpServerAddress;

	/** Port for the SMTP server that sends e-mail. */
	@NotNull
	private Integer smtpServerPort;

	/** Username to connect to the SMTP server that sends email. */
	@NotNull
	private String smtpUsername;

	/** Password to connect to the SMTP server that sends email. */
	@NotNull
	private String smtpPassword;

	/** The URL for this Marvin installation. */
	@NotNull
	private String baseURL;

	/** Constructor. */
	public MarvinConfiguration() {}

	/** Getter for creationDate. */
	public Date getCreationDate() {
		return creationDate;
	}

	/** Getter for institutionAcronym. */
	public String getInstitutionAcronym() {
		return institutionAcronym;
	}

	/** Setter for creationDate. */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/** Setter for institutionAcronym. */
	public void setInstitutionAcronym(String institutionAcronym) {
		this.institutionAcronym = institutionAcronym;
	}

	/** Getter for smtpServerAddress. */
	public String getSmtpServerAddress() {
		return smtpServerAddress;
	}

	/** Setter for smtpServerAddress. */
	public void setSmtpServerAddress(String smtpServerAddress) {
		this.smtpServerAddress = smtpServerAddress;
	}

	/** Getter for smtpServerPort. */
	public Integer getSmtpServerPort() {
		return smtpServerPort;
	}

	/** Setter for smtpServerPort. */
	public void setSmtpServerPort(Integer smtpServerPort) {
		this.smtpServerPort = smtpServerPort;
	}

	/** Getter for smtpUsername. */
	public String getSmtpUsername() {
		return smtpUsername;
	}

	/** Setter for smtpUsername. */
	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}

	/** Getter for smtpPassword. */
	public String getSmtpPassword() {
		return smtpPassword;
	}

	/** Setter for smtpPassword. */
	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	/** Getter for baseURL. */
	public String getBaseURL() {
		return baseURL;
	}

	/** Setter for baseURL. */
	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}
}
