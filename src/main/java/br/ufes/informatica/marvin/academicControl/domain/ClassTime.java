package br.ufes.informatica.marvin.academicControl.domain;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.sun.istack.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import br.ufes.informatica.marvin.academicControl.enums.EnumWeekDays;

@Entity
public class ClassTime extends PersistentObjectSupport implements Comparable<ClassTime> {
	private static final long serialVersionUID = 1L;

	@NotNull
	private LocalTime startTime;

	@NotNull
	private LocalTime endTime;

	@NotNull
	@Enumerated(EnumType.STRING)
	private EnumWeekDays weekDay;

	@Override
	public int compareTo(ClassTime o) {
		return 0;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public EnumWeekDays getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(EnumWeekDays weekDay) {
		this.weekDay = weekDay;
	}
}
