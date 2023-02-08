package br.ufes.informatica.marvin.academicControl.persistence;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.Deadline;
import br.ufes.informatica.marvin.academicControl.domain.Request;
import br.ufes.informatica.marvin.core.domain.Academic;

@Local
public interface RequestDAO extends BaseDAO<Request> {

	List<Request> retrieveRequestsByUser(Academic currentUser) throws Exception;

	boolean requestAlreadyExist(Request request);

	List<Request> requestWithoutAnswer();

	boolean deadlineIsLinkedInRequest(Deadline deadline);

}
