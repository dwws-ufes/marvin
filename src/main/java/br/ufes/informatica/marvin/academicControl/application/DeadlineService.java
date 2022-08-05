package br.ufes.informatica.marvin.academicControl.application;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.marvin.academicControl.domain.Deadline;

@Local
public interface DeadlineService extends CrudService<Deadline> {

}
