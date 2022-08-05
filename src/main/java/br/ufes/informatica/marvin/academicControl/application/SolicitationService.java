package br.ufes.informatica.marvin.academicControl.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.marvin.academicControl.domain.Solicitation;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;
import br.ufes.informatica.marvin.core.domain.Academic;

@Local
public interface SolicitationService extends CrudService<Solicitation> {

	void solicitationSubject(Academic currentUser, List<SubjectOffer> listSubjectOffer);

}
