package br.ufes.informatica.marvin.academicControl.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.marvin.academicControl.application.DeadlineService;
import br.ufes.informatica.marvin.academicControl.application.RequestService;
import br.ufes.informatica.marvin.academicControl.domain.Deadline;
import br.ufes.informatica.marvin.academicControl.domain.Request;
import br.ufes.informatica.marvin.core.application.LoginService;

@Named
@SessionScoped
public class RequestController extends CrudController<Request> {
	private static final long serialVersionUID = 1L;

	private static final String VIEW_PATH = "/academicControl/request/";

	@EJB
	private RequestService requestService;

	@EJB
	private DeadlineService deadlineService;

	@EJB
	private LoginService loginService;

	private List<Deadline> deadlines;

	private Request request;

	@Inject
	public void init() {
		setDeadlines(deadlineService.retrieveDeadline());
	}

	@Override
	protected CrudService<Request> getCrudService() {
		return requestService;
	}

	@Override
	protected void initFilters() {
	}

	public List<Deadline> getDeadlines() {
		return deadlines;
	}

	public void setDeadlines(List<Deadline> deadlines) {
		this.deadlines = deadlines;
	}

	public DeadlineService getDeadlineService() {
		return deadlineService;
	}

	public void setDeadlineService(DeadlineService deadlineService) {
		this.deadlineService = deadlineService;
	}

	public void onLoad() {
		request = new Request();
	}

	public String saveFileAndGenerateRequest() {
		requestService.createRequest(loginService.getCurrentUser(), request);
		return list();
	}

	public String startRequest() {
		return VIEW_PATH + "createRequest.xhtml?faces-redirect=true";
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}
}
