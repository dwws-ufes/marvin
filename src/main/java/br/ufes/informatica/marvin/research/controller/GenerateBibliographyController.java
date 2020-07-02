package br.ufes.informatica.marvin.research.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import br.ufes.inf.nemo.jbutler.ejb.controller.JSFController;
import br.ufes.inf.nemo.jbutler.ejb.controller.PersistentObjectConverterFromId;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.persistence.AcademicDAO;
import br.ufes.informatica.marvin.research.application.GenerateBibliographyService;
import br.ufes.informatica.marvin.research.application.ListResearchersService;
import br.ufes.informatica.marvin.research.domain.BibGenConfiguration;
import br.ufes.informatica.marvin.research.domain.BibGenResearcher;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Named
@ConversationScoped
public class GenerateBibliographyController extends JSFController {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** Logger for this class. */
  private static final Logger logger =
      Logger.getLogger(GenerateBibliographyController.class.getCanonicalName());

  /** Path to the folder where the view files (web pages) for this action are placed. */
  private static final String VIEW_PATH = "/research/generateBibliography/";

  /** TODO: document this field. */
  @Inject
  private Conversation conversation;

  /** TODO: document this field. */
  @EJB
  private GenerateBibliographyService generateBibliographyService;

  /** TODO: document this field. */
  @EJB
  private ListResearchersService listResearchersService;

  /** TODO: document this field. */
  private PersistentObjectConverterFromId<Academic> academicConverter;

  /** TODO: document this field. */
  private BibGenConfiguration configuration = new BibGenConfiguration();

  /** TODO: document this field. */
  private List<Academic> researchers;

  /** TODO: document this field. */
  private Academic selectedResearcher;

  /** TODO: document this field. */
  private StringBuilder bibliography;

  /**
   * TODO: document this method.
   */
  @Inject
  public void init(AcademicDAO academicDAO) {
    logger.log(Level.FINE,
        "Initializing GenerateBibliographyController: loading researchers and creating the academic converter...");
    researchers = listResearchersService.listResearchers();
    academicConverter = new PersistentObjectConverterFromId<>(academicDAO);

    // Begin the conversation.
    if (conversation.isTransient())
      conversation.begin();
  }

  /** Getter for academicConverter. */
  public PersistentObjectConverterFromId<Academic> getAcademicConverter() {
    return academicConverter;
  }

  /** Getter for configuration. */
  public BibGenConfiguration getConfiguration() {
    return configuration;
  }

  /** Getter for researchers. */
  public List<Academic> getResearchers() {
    return researchers;
  }

  /** Getter for selectedResearcher. */
  public Academic getSelectedResearcher() {
    return selectedResearcher;
  }

  /** Setter for selectedResearcher. */
  public void setSelectedResearcher(Academic selectedResearcher) {
    this.selectedResearcher = selectedResearcher;
  }

  /** Getter for bibliography. */
  public StringBuilder getBibliography() {
    return bibliography;
  }

  /**
   * TODO: document this method.
   * 
   * @return
   */
  public List<BibGenResearcher> getBibGenResearchers() {
    logger.log(Level.FINE,
        "Converting the set of researchers in the bibgen configuration to a list. Size should be: {0}",
        configuration.getResearchers().size());
    return new ArrayList<>(configuration.getResearchers());
  }

  /**
   * TODO: document this method.
   */
  public void addResearcher() {
    logger.log(Level.FINE, "Adding the selected researcher (if not null) to the configuration: {0}",
        selectedResearcher);
    if (selectedResearcher != null)
      configuration.addResearcher(selectedResearcher, null, null);
  }

  /**
   * TODO: document this method.
   * 
   * @param researcher
   * @return
   */
  public long countPublications(Academic researcher) {
    logger.log(Level.FINE, "Counting the number of publications of researcher: {0}",
        researcher.getName());
    return listResearchersService.countPublications(researcher);
  }

  /**
   * TODO: document this method.
   * 
   * @return
   */
  public String startOver() {
    logger.log(Level.FINE, "Ending the conversation and starting over...");
    if (!conversation.isTransient())
      conversation.end();
    return VIEW_PATH + "index.xhtml?faces-redirect=true";
  }

  /**
   * TODO: document this method.
   * 
   * @return
   */
  public String generate() {
    // FIXME: check if the form was properly filled in.
    logger.log(Level.FINE, "Generating the configuration...");
    bibliography = generateBibliographyService.generateBibliography(configuration);

    return VIEW_PATH + "bibliography.xhtml?faces-redirect=true";
  }

  /**
   * TODO: document this method.
   * 
   * @return
   */
  public String back() {
    logger.log(Level.FINE, "Going back to the configuration screen...");
    return VIEW_PATH + "index.xhtml?faces-redirect=true";
  }

  /**
   * TODO: document this method.
   */
  public StreamedContent getBibliographyFile() {
    try (InputStream in = new ByteArrayInputStream(bibliography.toString().getBytes("UTF-8"))) {
      logger.log(Level.INFO, "Downloading: " + in.available());
      // Deprecated, replaced with the next line: return new DefaultStreamedContent(in,
      // "text/plain", "bibliography.bib");
      return DefaultStreamedContent.builder().stream(() -> in).contentType("text/plain")
          .name("bibliography.bib").build();
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
}
