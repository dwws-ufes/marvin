package br.ufes.informatica.marvin.research.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.marvin.research.application.ManageQualisService;
import br.ufes.informatica.marvin.research.domain.Qualis;
import br.ufes.informatica.marvin.research.domain.QualisValidity;

@Named("manageQualisController")
@SessionScoped
public class ManageQualisController extends CrudController<Qualis> {
	private static final long serialVersionUID = 1L;

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(ManageQualisController.class.getCanonicalName());

	@EJB
	private ManageQualisService manageQualisService;

	private QualisValidity newQualisValidity;

	private Qualis newQualis;

	private List<Qualis> qualis;

	@Override
	protected CrudService<Qualis> getCrudService() {
		return manageQualisService;
	}

	@Override
	protected void initFilters() {
	}

	public QualisValidity getNewQualisValidity() {
		return newQualisValidity;
	}

	public void setNewQualisValidity(QualisValidity newQualisValidity) {
		this.newQualisValidity = newQualisValidity;
	}

	public Qualis getNewQualis() {
		return newQualis;
	}

	public void setNewQualis(Qualis newQualis) {
		this.newQualis = newQualis;
	}

	public List<Qualis> getQualis() {
		return qualis;
	}

	public void setQualis(List<Qualis> qualis) {
		this.qualis = qualis;
	}

}
