package br.ufes.informatica.marvin.academicControl.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.marvin.academicControl.domain.Request;
import br.ufes.informatica.marvin.core.domain.Academic;

@Local
public interface RequestService extends CrudService<Request> {

	void responseRequest(Academic currentUser, Request request);

	void refuseRequest(Academic currentUser, Request request);

	void changeStatus(Academic currentUser, Request request);

	void revokeStatus(Academic currentUser, Request request);

	List<Request> retrieveRequestsByUser(Academic currentUser) throws Exception;

	boolean requestAlreadyExist(Request request);

}
