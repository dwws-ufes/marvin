package br.ufes.informatica.marvin.core.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity
public class Occupation extends PersistentObjectSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	private boolean coordinator;

	@NotNull
	private boolean secretary;

	@NotNull
	private boolean member;

	@NotNull
	private boolean doctoral_supervisor;

	@NotNull
	@OneToOne
	private Academic academic;

	@NotNull
	@ManyToOne
	private PPG ppg;

	public boolean isCoordinator() {
		return coordinator;
	}

	public void setCoordinator(boolean coordinator) {
		this.coordinator = coordinator;
	}

	public boolean isSecretary() {
		return secretary;
	}

	public void setSecretary(boolean secretary) {
		this.secretary = secretary;
	}

	public boolean isMember() {
		return member;
	}

	public void setMember(boolean member) {
		this.member = member;
	}

	public boolean isDoctoral_supervisor() {
		return doctoral_supervisor;
	}

	public void setDoctoral_supervisor(boolean doctoral_supervisor) {
		this.doctoral_supervisor = doctoral_supervisor;
	}

	public Academic getAcademic() {
		return academic;
	}

	public void setAcademic(Academic academic) {
		this.academic = academic;
	}

	public PPG getPpg() {
		return ppg;
	}

	public void setPpg(PPG ppg) {
		this.ppg = ppg;
	}

}
