package br.ufes.informatica.marvin.academicControl.persistence;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;

@Local
public interface SubjectOfferDAO extends BaseDAO<SubjectOffer> {
	List<SubjectOffer> retrieveSubjectsOffer();

	SubjectOffer retrieveSubjectOfferById(Long id);
}
