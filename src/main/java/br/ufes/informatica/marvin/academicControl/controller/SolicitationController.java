package br.ufes.informatica.marvin.academicControl.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.marvin.academicControl.application.SolicitationService;
import br.ufes.informatica.marvin.academicControl.application.SubjectOfferService;
import br.ufes.informatica.marvin.academicControl.domain.Solicitation;
import br.ufes.informatica.marvin.academicControl.domain.SubjectOffer;

@Named
@SessionScoped
public class SolicitationController extends CrudController<Solicitation> {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(SolicitationController.class.getCanonicalName());

	@EJB
	private SolicitationService solicitationService;

	@EJB
	private SubjectOfferService subjectOfferService;

	private List<SubjectOffer> listSubjectOffer;

	private SubjectOffer selectedSubjectOffer;

	@Inject
	public void init() {
		setListSubjectOffer(subjectOfferService.retrieveSubjectsOffer());
	}

	@Override
	protected void initFilters() {
	}

	public void addSubjectOffer() {
		logger.log(Level.FINE, "Adding the selected SubjectOffer (if not null) to the configuration: {0}",
				selectedSubjectOffer);
		if (selectedSubjectOffer != null) {

		}
		// configuration.addResearcher(selectedResearcher, null, null);
	}

	@Override
	protected CrudService<Solicitation> getCrudService() {
		return solicitationService;
	}

	public List<SubjectOffer> getListSubjectOffer() {
		return listSubjectOffer;
	}

	public void setListSubjectOffer(List<SubjectOffer> listSubjectOffer) {
		this.listSubjectOffer = listSubjectOffer;
	}

}
