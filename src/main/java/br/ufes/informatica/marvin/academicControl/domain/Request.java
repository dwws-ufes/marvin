package br.ufes.informatica.marvin.academicControl.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sun.istack.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import br.ufes.informatica.marvin.academicControl.enums.EnumRequestSituation;
import br.ufes.informatica.marvin.core.domain.Academic;

@Entity
public class Request extends PersistentObjectSupport {
	private static final long serialVersionUID = 1L;

	@NotNull
	@OneToOne
	private Academic requester;

	@OneToOne
	private Academic grantor;

	@NotNull
	@OneToOne
	private Deadline deadline;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date requestDate;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date replyDate;

	@Enumerated(EnumType.ORDINAL)
	private EnumRequestSituation requestSituation;

	private String requestResponseDetailing;

	private byte[] fileUniversityDegree;

	private byte[] fileUseOfCredits;

	public Academic getRequester() {
		return requester;
	}

	public void setRequester(Academic requester) {
		this.requester = requester;
	}

	public Academic getGrantor() {
		return grantor;
	}

	public void setGrantor(Academic grantor) {
		this.grantor = grantor;
	}

	public Deadline getDeadline() {
		return deadline;
	}

	public void setDeadline(Deadline deadline) {
		this.deadline = deadline;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

	public String getRequestResponseDetailing() {
		return requestResponseDetailing;
	}

	public void setRequestResponseDetailing(String requestResponseDetailing) {
		this.requestResponseDetailing = requestResponseDetailing;
	}

	public byte[] getFileUniversityDegree() {
		return fileUniversityDegree;
	}

	public void setFileUniversityDegree(byte[] fileUniversityDegree) {
		this.fileUniversityDegree = fileUniversityDegree;
	}

	public byte[] getFileUseOfCredits() {
		return fileUseOfCredits;
	}

	public void setFileUseOfCredits(byte[] fileUseOfCredits) {
		this.fileUseOfCredits = fileUseOfCredits;
	}

	public EnumRequestSituation getRequestSituation() {
		return requestSituation;
	}

	public void setRequestSituation(EnumRequestSituation requestSituation) {
		this.requestSituation = requestSituation;
	}
}
