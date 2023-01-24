package br.ufes.informatica.marvin.academicControl.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.marvin.academicControl.domain.EnrollmentRequest;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;
import br.ufes.informatica.marvin.core.domain.Academic;

@Local
public interface EnrollmentRequestService extends CrudService<EnrollmentRequest> {

	void createEnrollmentRequestSubject(Academic currentUser, List<SubjectOffer> listSubjectOffer);

	List<EnrollmentRequest> retrieveEnrollmentRequests(Academic currentUser) throws Exception;

	void registeredOnSappg(EnrollmentRequest enrollmentRequest);

}
