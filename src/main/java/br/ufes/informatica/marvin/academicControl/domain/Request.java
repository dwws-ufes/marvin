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
public class Request extends PersistentObjectSupport implements Comparable<Request> {
	private static final long serialVersionUID = 1L;

	@NotNull
	@OneToOne
	private Academic requester;

	@OneToOne
	private Academic userSituation;

	@OneToOne
	private Academic grantor;

	@NotNull
	@OneToOne
	private Deadline deadline;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date requestDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date userSituationDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date responseDate;

	@NotNull
	@Enumerated(EnumType.STRING)
	private EnumRequestSituation requestSituation;

	private String requestResponseDetailing;

	@NotNull
	private String observation;

	private String localfileUniversityDegree;

	private String localfileUseOfCredits;

	@Transient
	private UploadedFile fileUniversityDegree;

	@Transient
	private UploadedFile fileUseOfCredits;

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

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
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

	public Academic getUserSituation() {
		return userSituation;
	}

	public void setUserSituation(Academic userSituation) {
		this.userSituation = userSituation;
	}

	public Date getUserSituationDate() {
		return userSituationDate;
	}

	public void setUserSituationDate(Date userSituationDate) {
		this.userSituationDate = userSituationDate;
	}

	@PrePersist
	void setDefaultValues() {
		this.requestDate = new Date(System.currentTimeMillis());
		this.requestSituation = EnumRequestSituation.WAITING;
	}

	@Override
	public int compareTo(Request o) {
		return uuid.compareTo(o.uuid);
	}
}
