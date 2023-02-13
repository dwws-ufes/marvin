package br.ufes.informatica.marvin.academicControl.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.ObjectUtils;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudException;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.inf.nemo.jbutler.ejb.controller.PersistentObjectConverterFromId;
import br.ufes.informatica.marvin.academicControl.application.EnrollmentRequestService;
import br.ufes.informatica.marvin.academicControl.application.SubjectOfferService;
import br.ufes.informatica.marvin.academicControl.domain.EnrollmentRequest;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;
import br.ufes.informatica.marvin.academicControl.enums.EnumEnrollmentRequestSituation;
import br.ufes.informatica.marvin.core.application.LoginService;

@Named
@SessionScoped
public class EnrollmentRequestController extends CrudController<EnrollmentRequest> {
	private static final long serialVersionUID = 1L;

	private static final String VIEW_PATH = "/academicControl/enrollmentRequest/";

	private static final Logger logger = Logger.getLogger(EnrollmentRequestController.class.getCanonicalName());

	@EJB
	private EnrollmentRequestService enrollmentRequestService;

	@EJB
	private SubjectOfferService subjectOfferService;

	@EJB
	private LoginService loginService;

	private SubjectOffer selectOffer;

	private List<SubjectOffer> listSubjectOfferSelected;

	private List<SubjectOffer> listSubjectOffer;

	private PersistentObjectConverterFromId<SubjectOffer> subjectOfferConverter;

	@Inject
	@ViewScoped
	public void init() throws Exception {
		setListSubjectOffer(subjectOfferService.retrieveSubjectsOfferByActualPeriod());
		setSubjectOfferConverter(subjectOfferService.getSubjectOfferConverter());
	}

	public void onLoad() throws Exception {
		init();
		listSubjectOfferSelected = new ArrayList<SubjectOffer>();
	}

	public void addSubjectOffer() {
		logger.log(Level.FINE, "Adding the selected SubjectOffer (if not null) to the configuration: {0}", selectOffer);
		if (ObjectUtils.allNotNull(selectOffer, listSubjectOfferSelected) && //
				!listSubjectOfferSelected.contains(selectOffer)) {
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

	public List<EnrollmentRequest> getEnrollmentRequests() throws Exception {
		return enrollmentRequestService.retrieveEnrollmentRequests(loginService.getCurrentUser());
	}

	@Override
	protected CrudService<EnrollmentRequest> getCrudService() {
		return enrollmentRequestService;
	}

	@Override
	protected void initFilters() {

	}

	public String generate() {
		logger.log(Level.FINE, "Requesting enrollment...");
		enrollmentRequestService.createEnrollmentRequestSubject(loginService.getCurrentUser(),
				listSubjectOfferSelected);
		listSubjectOfferSelected.clear();
		return list();
	}

	public String startRequest() {
		return VIEW_PATH + "enrollmentRequest.xhtml?faces-redirect=true";
	}

	public EnumEnrollmentRequestSituation[] getValues() {
		return EnumEnrollmentRequestSituation.values();
	}

	public String registeredOnSappg() throws CrudException {
		enrollmentRequestService.registeredOnSappg(this.getSelectedEntity());
		return list();
	}

	public boolean isAllowedChangeSappg() {
		if (this.getSelectedEntity() != null) {
			return enrollmentRequestService.isAllowedChangeSappg(this.getSelectedEntity());
		}
		return false;
	}
}
