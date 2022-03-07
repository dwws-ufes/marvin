package br.ufes.informatica.marvin.academicControl.application;

import java.util.List;
import javax.ejb.Local;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.PersistentObjectConverterFromId;
import br.ufes.informatica.marvin.academicControl.domain.SchoolSubject;
import br.ufes.informatica.marvin.core.domain.Role;

@Local
public interface SchoolSubjectService extends CrudService<SchoolSubject> {
  PersistentObjectConverterFromId<Role> getRoleConverter();
  List<Role> findRoleByName(String name);
}
