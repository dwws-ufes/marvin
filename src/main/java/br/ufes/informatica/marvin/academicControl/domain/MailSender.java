package br.ufes.informatica.marvin.academicControl.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import br.ufes.informatica.marvin.academicControl.enums.EnumStatusMail;

@Entity
public class MailSender extends PersistentObjectSupport {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Basic
	@Size(max = 100)
	private String subject;

	@NotNull
	@Basic
	@Size(max = 400)
	private String addressee;

	@NotNull
	@Basic
	@Size(max = 4000)
	private String text;

	@Basic
	@Size(max = 4000)
	private String error;

	@NotNull
	@Enumerated(EnumType.STRING)
	private EnumStatusMail statusMail;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date sentDate;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAddressee() {
		return addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public EnumStatusMail getStatusMail() {
		return statusMail;
	}

	public void setStatusMail(EnumStatusMail statusMail) {
		this.statusMail = statusMail;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
}
