package br.ufes.informatica.marvin.research.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import br.ufes.informatica.marvin.core.domain.PPG;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Entity
public class QualisValidity extends PersistentObjectSupport implements Comparable<QualisValidity> {
	/** The unique identifier for a serializable class. */
	private static final long serialVersionUID = 1L;

	@NotNull
	private Date dtStart;

	private Date dtEnd;

	@NotNull
	private PPG ppg;

	@OneToMany
	private List<Qualis> qualis;

	public QualisValidity() {

	}

	public Date getDtStart() {
		return dtStart;
	}

	public void setDtStart(Date dtStart) {
		this.dtStart = dtStart;
	}

	public Date getDtEnd() {
		return dtEnd;
	}

	public void setDtEnd(Date dtEnd) {
		this.dtEnd = dtEnd;
	}

	public PPG getPpg() {
		return ppg;
	}

	public void setPpg(PPG ppg) {
		this.ppg = ppg;
	}

	public List<Qualis> getQualis() {
		return qualis;
	}

	public void setQualis(List<Qualis> qualis) {
		this.qualis = qualis;
	}

	@Override
	public int compareTo(QualisValidity o) {
		if (o.getPpg().equals(ppg)) {
			if (dtStart.equals(o.getDtStart())) {
				if (dtEnd != null && o.getDtEnd() != null) {
					if (dtEnd.equals(o.getDtEnd())) {
						return 0;
					}
				} else if (dtEnd == null && o.getDtEnd() == null) {
					return 0;
				}
			}
		}
		return 1;
	}

}
