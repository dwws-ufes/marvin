package br.ufes.informatica.marvin.academicControl.persistence;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.Request;

@Local
public interface RequestDAO extends BaseDAO<Request> {

}
