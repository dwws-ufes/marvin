package br.ufes.informatica.marvin.research.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.file.UploadedFile;
import br.ufes.inf.nemo.jbutler.ejb.controller.JSFController;
import br.ufes.inf.nemo.jbutler.ejb.controller.PersistentObjectConverterFromId;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.research.application.PublicationInfo;
import br.ufes.informatica.marvin.research.application.UploadLattesCVService;
import br.ufes.informatica.marvin.research.domain.Publication;
import br.ufes.informatica.marvin.research.domain.Venue;
import br.ufes.informatica.marvin.research.exceptions.LattesIdNotRegisteredException;
import br.ufes.informatica.marvin.research.exceptions.LattesParseException;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Named
@ConversationScoped
public class UploadLattesCVController extends JSFController {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** Logger for this class. */
  private static final Logger logger =
      Logger.getLogger(UploadLattesCVController.class.getCanonicalName());

  /** Path to the folder where the view files (web pages) for this action are placed. */
  private static final String VIEW_PATH = "/research/uploadLattesCV/";

  /** TODO: document this field. */
  @Inject
  private Conversation conversation;

  /** TODO: document this field. */
  @EJB
  private UploadLattesCVService uploadLattesCVService;

  /** TODO: document this field. */
  private UploadedFile file;

  /** TODO: document this field. */
  private Set<Publication> publications;

  /** TODO: document this field. */
  private Set<Publication> papers;

  /** TODO: document this field. */
  private Publication selectedPublication;

  /** TODO: document this field. */
  private Academic researcher;

  /** TODO: document this field. */
  private long previousQuantity;

  /** Getter for file. */
  public UploadedFile getFile() {
    return file;
  }

  /** Setter for file. */
  public void setFile(UploadedFile file) {
    this.file = file;
  }

  /** Getter for publications. */
  public Set<Publication> getPublications() {
    return publications;
  }

  /** Getter for papers. */
  public Set<Publication> getPapers() {
    return papers;
  }

  /** Getter for selectedPublication. */
  public Publication getSelectedPublication() {
    return selectedPublication;
  }

  /** Setter for selectedPublication. */
  public void setSelectedPublication(Publication selectedPublication) {
    this.selectedPublication = selectedPublication;
  }

  /** Getter for researcher. */
  public Academic getResearcher() {
    return researcher;
  }

  /** Getter for previousQuantity. */
  public long getPreviousQuantity() {
    return previousQuantity;
  }

  /**
   * TODO: document this method.
   * 
   * @return
   */
  public PersistentObjectConverterFromId<Venue> getVenueConverter() {
    return uploadLattesCVService.getVenueConverter();
  }

  /**
   * TODO: document this method.
   * 
   * @param query
   * @return
   */
  public List<Venue> completeVenues(String query) {
    return uploadLattesCVService.findVenueByName(query);
  }

  /**
   * TODO: document this method.
   */
  public String upload() {
    // Manages the conversation.
    if (conversation.isTransient())
      conversation.begin();

    try {
      // Performs the upload.
      PublicationInfo info = uploadLattesCVService.uploadLattesCV(file.getInputStream());

      // Adds all the publications to a single set.
      publications = new TreeSet<>();
      publications.addAll(info.getJournalPapers());
      publications.addAll(info.getBooks());
      publications.addAll(info.getBookChapters());
      publications.addAll(info.getConferencePapers());

      // Retrieve information on the researcher.
      researcher = info.getResearcher();
      previousQuantity = info.getPreviousQuantity();
    } catch (LattesIdNotRegisteredException e) {
      logger.log(Level.INFO, "Lattes ID in the uploaded CV is not registered in the system");
      addGlobalI18nMessage("msgsResearch", FacesMessage.SEVERITY_WARN,
          "uploadLattesCV.error.lattesIdNotRegistered.summary", new Object[] {},
          "uploadLattesCV.error.lattesIdNotRegistered.detail",
          new Object[] {e.getLattesId(), e.getResearcherName()});
      return null;
    } catch (IOException | LattesParseException e) {
      logger.log(Level.INFO, "Lattes parser error.");
      addGlobalI18nMessage("msgsResearch", FacesMessage.SEVERITY_ERROR,
          "uploadLattesCV.error.lattesParseError.summary",
          "uploadLattesCV.error.lattesIdNotRegistered.detail");
      return null;
    }

    return VIEW_PATH + "list.xhtml?faces-redirect=true";
  }

  /**
   * TODO: document this method.
   * 
   * @return
   */
  public String cancel() {
    // Ends the conversation and redirects back to the beginning.
    if (!conversation.isTransient())
      conversation.end();
    return VIEW_PATH + "index.xhtml?faces-redirect=true";
  }

  /**
   * TODO: document this method.
   * 
   * @return
   */
  public String confirm() {
    // Assigns the publications to the researcher.
    uploadLattesCVService.assignPublicationsToAcademic(publications, researcher);

    // Separates papers (publications with venues, i.e., conference and journal papers).
    papers = new TreeSet<>();
    for (Publication publication : publications)
      if (!publication.getVenueString().isEmpty())
        papers.add(publication);

    // Renders the matching page.
    return VIEW_PATH + "match.xhtml?faces-redirect=true";
  }

  /**
   * TODO: document this method.
   * 
   * @return
   */
  public String match() {
    // Saves the matches.
    uploadLattesCVService.saveMatches(papers);

    // Ends the conversation.
    if (!conversation.isTransient())
      conversation.end();

    // Places the controller in the flash context and redirects to the final page.
    getFlash().put("uploadLattesCVController", this);
    return VIEW_PATH + "done.xhtml?faces-redirect=true";
  }
}
