package br.ufes.informatica.marvin.academicControl.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import br.ufes.informatica.marvin.academicControl.enums.EnumEnrollmentRequestSituation;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.utils.MarvinFunctions;

@Entity
public class EnrollmentRequest extends PersistentObjectSupport implements Comparable<EnrollmentRequest> {
	private static final long serialVersionUID = 1L;

	@OneToOne
	@NotNull
	private Academic requester;

	@OneToOne
	@NotNull
	private SubjectOffer subjectOffer;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date requestDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateSituation;

	@Enumerated(EnumType.STRING)
	@NotNull
	private EnumEnrollmentRequestSituation enrollmentRequestSituation;

	@Basic
	@Size(max = 255)
	private String requestResponseDetailing;

	@PositiveOrZero
	@Digits(integer = 2, fraction = 2)
	private BigDecimal note;

	@NotNull
	private Boolean registeredSappg;

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

	public BigDecimal getNote() {
		return note;
	}

	public void setNote(BigDecimal note) {
		this.note = note;
	}

	public Boolean getRegisteredSappg() {
		return registeredSappg;
	}

	public void setRegisteredSappg(Boolean registeredSappg) {
		this.registeredSappg = registeredSappg;
	}

	@Override
	public int compareTo(EnrollmentRequest o) {
		return uuid.compareTo(o.uuid);
	}

	@PrePersist
	public void setDefautValues() {
		this.dateSituation = MarvinFunctions.sysdate();
		this.requestDate = MarvinFunctions.sysdate();
		this.enrollmentRequestSituation = EnumEnrollmentRequestSituation.WAITING;
		this.registeredSappg = false;
	}
}
