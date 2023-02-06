package br.ufes.informatica.marvin.academicControl.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity
public class Period extends PersistentObjectSupport implements Comparable<Period> {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(max = 15)
	private String name;

	@Temporal(TemporalType.DATE)
	@NotNull
	private Date offerStartDate;

	@Temporal(TemporalType.DATE)
	@NotNull
	private Date offerFinalDate;

	@Temporal(TemporalType.DATE)
	@NotNull
	private Date enrollmentStartDate;

	@Temporal(TemporalType.DATE)
	@NotNull
	private Date enrollmentFinalDate;

	@Temporal(TemporalType.DATE)
	@NotNull
	private Date periodStartDate;

	@Temporal(TemporalType.DATE)
	@NotNull
	private Date periodFinalDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getPeriodStartDate() {
		return periodStartDate;
	}

	public void setPeriodStartDate(Date periodStartDate) {
		this.periodStartDate = periodStartDate;
	}

	public Date getPeriodFinalDate() {
		return periodFinalDate;
	}

	public void setPeriodFinalDate(Date periodFinalDate) {
		this.periodFinalDate = periodFinalDate;
	}

	public Date getEnrollmentStartDate() {
		return enrollmentStartDate;
	}

	public void setEnrollmentStartDate(Date enrollmentStartDate) {
		this.enrollmentStartDate = enrollmentStartDate;
	}

	public Date getEnrollmentFinalDate() {
		return enrollmentFinalDate;
	}

	public void setEnrollmentFinalDate(Date enrollmentFinalDate) {
		this.enrollmentFinalDate = enrollmentFinalDate;
	}

	public Date getOfferStartDate() {
		return offerStartDate;
	}

	public void setOfferStartDate(Date offerStartDate) {
		this.offerStartDate = offerStartDate;
	}

	public Date getOfferFinalDate() {
		return offerFinalDate;
	}

	public void setOfferFinalDate(Date offerFinalDate) {
		this.offerFinalDate = offerFinalDate;
	}

	@Override
	public int compareTo(Period o) {
		return uuid.compareTo(o.uuid);
	}
}
