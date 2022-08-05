package br.ufes.informatica.marvin.academicControl.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.PositiveOrZero;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import br.ufes.informatica.marvin.academicControl.enums.EnumEnrollmentRequestSituation;
import br.ufes.informatica.marvin.core.domain.Academic;

@Entity
public class EnrollmentRequest extends PersistentObjectSupport {
	private static final long serialVersionUID = 1L;

	@OneToOne
	private Academic requester;

	@OneToOne
	private SubjectOffer subjectOffer;

	@Temporal(TemporalType.TIMESTAMP)
	private Date requestDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateSituation;

	@Enumerated(EnumType.STRING)
	private EnumEnrollmentRequestSituation enrollmentRequestSituation;

	private String requestResponseDetailing;

	@PositiveOrZero
	private BigDecimal note;

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

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getDateSituation() {
		return dateSituation;
	}

	public void setDateSituation(Date dateSituation) {
		this.dateSituation = dateSituation;
	}

	public EnumEnrollmentRequestSituation getEnrollmentRequestSituation() {
		return enrollmentRequestSituation;
	}

	public void setEnrollmentRequestSituation(EnumEnrollmentRequestSituation enrollmentRequestSituation) {
		this.enrollmentRequestSituation = enrollmentRequestSituation;
	}

	public String getRequestResponseDetailing() {
		return requestResponseDetailing;
	}

	public void setRequestResponseDetailing(String requestResponseDetailing) {
		this.requestResponseDetailing = requestResponseDetailing;
	}

	@PrePersist
	public void setDefautValues() {
		this.dateSituation = new Date(System.currentTimeMillis());
		this.requestDate = new Date(System.currentTimeMillis());
		this.enrollmentRequestSituation = EnumEnrollmentRequestSituation.WAITING;
	}

	public BigDecimal getNote() {
		return note;
	}

	public void setNote(BigDecimal note) {
		this.note = note;
	}
}
