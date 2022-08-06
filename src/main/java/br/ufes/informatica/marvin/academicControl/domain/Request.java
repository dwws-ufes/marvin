package br.ufes.informatica.marvin.academicControl.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.primefaces.model.file.UploadedFile;

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

	@Temporal(TemporalType.TIMESTAMP)
	private Date replyDate;

	@NotNull
	@Enumerated(EnumType.STRING)
	private EnumRequestSituation requestSituation;

	private String requestResponseDetailing;

	private String observation;

	@Transient
	private UploadedFile fileUniversityDegree;

	@Transient
	private UploadedFile fileUseOfCredits;

	private String localfileUniversityDegree;

	private String localfileUseOfCredits;

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

	public EnumRequestSituation getRequestSituation() {
		return requestSituation;
	}

	public void setRequestSituation(EnumRequestSituation requestSituation) {
		this.requestSituation = requestSituation;
	}

	public String getLocalfileUniversityDegree() {
		return localfileUniversityDegree;
	}

	public void setLocalfileUniversityDegree(String localfileUniversityDegree) {
		this.localfileUniversityDegree = localfileUniversityDegree;
	}

	public String getLocalfileUseOfCredits() {
		return localfileUseOfCredits;
	}

	public void setLocalfileUseOfCredits(String localfileUseOfCredits) {
		this.localfileUseOfCredits = localfileUseOfCredits;
	}

	public UploadedFile getFileUniversityDegree() {
		return fileUniversityDegree;
	}

	public void setFileUniversityDegree(UploadedFile fileUniversityDegree) {
		this.fileUniversityDegree = fileUniversityDegree;
	}

	public UploadedFile getFileUseOfCredits() {
		return fileUseOfCredits;
	}

	public void setFileUseOfCredits(UploadedFile fileUseOfCredits) {
		this.fileUseOfCredits = fileUseOfCredits;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	@PrePersist
	void setDefaultValues() {
		this.requestDate = new Date(System.currentTimeMillis());
		this.requestSituation = EnumRequestSituation.WAITING;
	}
}
