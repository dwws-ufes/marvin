package br.ufes.informatica.marvin.academicControl.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import br.ufes.informatica.marvin.core.domain.Academic;

@Entity
public class Solicitation extends PersistentObjectSupport {
	private static final long serialVersionUID = 1L;

	@OneToOne
	private Academic requester;

	@OneToOne
	private SubjectOffer subjectOffer;

	@Enumerated(EnumType.STRING)
	private SolicitationType solicitationType;

	@Temporal(TemporalType.TIMESTAMP)
	private Date requestDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date completionDate;

	@Enumerated(EnumType.STRING)
	private SolicitationSituation SolicitationSituation;

	private String requestResponseDetailing;

	public Academic getRequester() {
		return requester;
	}

	public void setRequester(Academic requester) {
		this.requester = requester;
	}

	public SubjectOffer getSubjectOffer() {
		return subjectOffer;
	}

	public void setSubjectOffer(SubjectOffer subjectOffer) {
		this.subjectOffer = subjectOffer;
	}

	public SolicitationType getSolicitationType() {
		return solicitationType;
	}

	public void setSolicitationType(SolicitationType solicitationType) {
		this.solicitationType = solicitationType;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public SolicitationSituation getSolicitationSituation() {
		return SolicitationSituation;
	}

	public void setSolicitationSituation(SolicitationSituation solicitationSituation) {
		SolicitationSituation = solicitationSituation;
	}

	public String getRequestResponseDetailing() {
		return requestResponseDetailing;
	}

	public void setRequestResponseDetailing(String requestResponseDetailing) {
		this.requestResponseDetailing = requestResponseDetailing;
	}
}
