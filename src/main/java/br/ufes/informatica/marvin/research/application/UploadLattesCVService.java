package br.ufes.informatica.marvin.research.application;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;
import br.ufes.inf.nemo.jbutler.ejb.controller.PersistentObjectConverterFromId;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.research.domain.Publication;
import br.ufes.informatica.marvin.research.domain.Venue;
import br.ufes.informatica.marvin.research.exceptions.LattesIdNotRegisteredException;
import br.ufes.informatica.marvin.research.exceptions.LattesParseException;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Local
public interface UploadLattesCVService extends Serializable {
  /**
   * TODO: document this method.
   * 
   * @return
   */
  PersistentObjectConverterFromId<Venue> getVenueConverter();

  /**
   * TODO: document this method.
   * 
   * @param query
   * @return
   */
  List<Venue> findVenueByName(String query);

  /**
   * TODO: document this method.
   * 
   * @param inputStream
   * @return
   * @throws LattesIdNotRegisteredException
   * @throws LattesParseException
   */
  PublicationInfo uploadLattesCV(InputStream inputStream)
      throws LattesIdNotRegisteredException, LattesParseException;

  /**
   * TODO: document this method.
   * 
   * @param publications
   * @param owner
   */
  void assignPublicationsToAcademic(Set<Publication> publications, Academic owner);

  /**
   * TODO: document this method.
   * 
   * @param publications
   */
  void saveMatches(Set<Publication> publications);
}
