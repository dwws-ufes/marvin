package br.ufes.informatica.marvin.academicControl.persistence;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.Deadline;

@Local
public interface DeadlineDAO extends BaseDAO<Deadline> {

}
