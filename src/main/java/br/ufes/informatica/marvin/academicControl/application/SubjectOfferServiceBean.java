package br.ufes.informatica.marvin.academicControl.application;

import java.util.List;
import java.util.Objects;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.controller.PersistentObjectConverterFromId;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;
import br.ufes.informatica.marvin.academicControl.persistence.SubjectOfferDAO;

@Stateless
@PermitAll
public class SubjectOfferServiceBean extends CrudServiceBean<SubjectOffer> implements SubjectOfferService {
	private static final long serialVersionUID = 1L;

	@EJB
	private SubjectOfferDAO subjectOfferDAO;

	private PersistentObjectConverterFromId<SubjectOffer> subjectOfferConverter;

	@Override
	public BaseDAO<SubjectOffer> getDAO() {
		return subjectOfferDAO;
	}

	@Override
	public List<SubjectOffer> retrieveSubjectsOffer() {
		return subjectOfferDAO.retrieveSubjectsOffer();
	}

	@Override
	public PersistentObjectConverterFromId<SubjectOffer> getSubjectOfferConverter() {
		if (Objects.isNull(subjectOfferConverter))
			subjectOfferConverter = new PersistentObjectConverterFromId<SubjectOffer>(subjectOfferDAO);
		return subjectOfferConverter;
	}

	@Override
	public SubjectOffer retrieveSubjectOfferById(Long id) {
		return subjectOfferDAO.retrieveSubjectOfferById(id);
	}
}
