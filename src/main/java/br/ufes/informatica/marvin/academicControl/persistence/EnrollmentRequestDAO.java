package br.ufes.informatica.marvin.academicControl.persistence;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.EnrollmentRequest;
import br.ufes.informatica.marvin.core.domain.Academic;

@Local
public interface EnrollmentRequestDAO extends BaseDAO<EnrollmentRequest> {

	List<EnrollmentRequest> getListEnrollmentRequestActualPeriodByUser(Academic currentUser) throws Exception;

}
