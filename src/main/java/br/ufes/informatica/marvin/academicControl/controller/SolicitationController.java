package br.ufes.informatica.marvin.academicControl.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.inf.nemo.jbutler.ejb.controller.PersistentObjectConverterFromId;
import br.ufes.informatica.marvin.academicControl.application.SolicitationService;
import br.ufes.informatica.marvin.academicControl.application.SubjectOfferService;
import br.ufes.informatica.marvin.academicControl.domain.Solicitation;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;
import br.ufes.informatica.marvin.core.application.LoginService;

@Named
@SessionScoped
public class SolicitationController extends CrudController<Solicitation> {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(SolicitationController.class.getCanonicalName());

	@EJB
	private SolicitationService solicitationService;

	@EJB
	private SubjectOfferService subjectOfferService;

	@EJB
	private LoginService loginService;

	private SubjectOffer selectOffer;

	private List<SubjectOffer> listSubjectOfferSelected;

	private List<SubjectOffer> listSubjectOffer;

	private PersistentObjectConverterFromId<SubjectOffer> subjectOfferConverter;

	@PostConstruct
	public void init() {
		setListSubjectOffer(subjectOfferService.retrieveSubjectsOffer());
		setSubjectOfferConverter(subjectOfferService.getSubjectOfferConverter());
	}

	public void onLoad() {
		listSubjectOfferSelected = new ArrayList<SubjectOffer>();
	}

	public void addSubjectOffer() {
		logger.log(Level.FINE, "Adding the selected SubjectOffer (if not null) to the configuration: {0}", selectOffer);
		if (selectOffer != null) {
			listSubjectOfferSelected.add(selectOffer);
		}
	}

	public List<SubjectOffer> getListSubjectOffer() {
		return listSubjectOffer;
	}

	public void setListSubjectOffer(List<SubjectOffer> listSubjectOffer) {
		this.listSubjectOffer = listSubjectOffer;
	}

	public PersistentObjectConverterFromId<SubjectOffer> getSubjectOfferConverter() {
		return subjectOfferConverter;
	}

	public void setSubjectOfferConverter(PersistentObjectConverterFromId<SubjectOffer> subjectOfferConverter) {
		this.subjectOfferConverter = subjectOfferConverter;
	}

	public SubjectOfferService getSubjectOfferService() {
		return subjectOfferService;
	}

	public void setSubjectOfferService(SubjectOfferService subjectOfferService) {
		this.subjectOfferService = subjectOfferService;
	}

	public List<SubjectOffer> getListSubjectOfferSelected() {
		return listSubjectOfferSelected;
	}

	public void setListSubjectOfferSelected(List<SubjectOffer> listSubjectOfferSelected) {
		this.listSubjectOfferSelected = listSubjectOfferSelected;
	}

	public SubjectOffer getSelectOffer() {
		return selectOffer;
	}

	public void setSelectOffer(SubjectOffer selectOffer) {
		this.selectOffer = selectOffer;
	}

	@Override
	protected CrudService<Solicitation> getCrudService() {
		return solicitationService;
	}

	@Override
	protected void initFilters() {

	}

	public void generate() {
		logger.log(Level.FINE, "Requesting enrollment...");
		solicitationService.solicitationSubject(loginService.getCurrentUser(), listSubjectOfferSelected);
		listSubjectOfferSelected.clear();
	}
}
