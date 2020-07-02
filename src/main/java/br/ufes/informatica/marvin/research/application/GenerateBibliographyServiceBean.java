package br.ufes.informatica.marvin.research.application;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.research.domain.BibGenConfiguration;
import br.ufes.informatica.marvin.research.domain.BibGenResearcher;
import br.ufes.informatica.marvin.research.domain.Publication;
import br.ufes.informatica.marvin.research.persistence.PublicationDAO;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Stateless
@RolesAllowed({"SysAdmin", "Professor"})
public class GenerateBibliographyServiceBean implements GenerateBibliographyService {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** Logger for this class. */
  private static final Logger logger =
      Logger.getLogger(GenerateBibliographyServiceBean.class.getCanonicalName());

  /** TODO: document this field. */
  @EJB
  private PublicationDAO publicationDAO;

  @Override
  public StringBuilder generateBibliography(BibGenConfiguration config) {
    StringBuilder builder = new StringBuilder();

    // Goes through all the researchers, adding their publications to a single sorted set.
    SortedSet<Publication> publications = new TreeSet<>();
    for (BibGenResearcher researcherCfg : config.getResearchers()) {
      Academic researcher = researcherCfg.getResearcher();
      logger.log(Level.INFO, "Adding publications of \"{0}\" ({1}), start: {2}, end: {3}",
          new Object[] {researcher.getName(), researcher.getEmail(), researcherCfg.getStartYear(),
              researcherCfg.getEndYear()});

      // Retrieves the publications within the year range and add them to the set.
      List<Publication> researcherPublications = publicationDAO.retrieveByAcademicAndYearRange(
          researcher, researcherCfg.getStartYear(), researcherCfg.getEndYear());
      publications.addAll(researcherPublications);
    }

    // Prints all publications in the sorted set to the string output.
    for (Publication publication : publications)
      builder.append(publication.toBibTeX()).append("\n\n");

    // Returns the string builder with the entire bibliography.
    return builder;
  }
}
