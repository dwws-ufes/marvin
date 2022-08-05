package br.ufes.informatica.marvin.academicControl.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.PersistentObjectConverterFromId;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;

@Local
public interface SubjectOfferService extends CrudService<SubjectOffer> {
	List<SubjectOffer> retrieveSubjectsOffer();

	SubjectOffer retrieveSubjectOfferById(Long id);

	PersistentObjectConverterFromId<SubjectOffer> getSubjectOfferConverter();
}