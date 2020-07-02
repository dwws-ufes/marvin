package br.ufes.informatica.marvin.research.persistence;

import java.util.List;
import javax.ejb.Local;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.research.domain.Venue;
import br.ufes.informatica.marvin.research.domain.VenueCategory;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Local
public interface VenueDAO extends BaseDAO<Venue> {
  /**
   * @param category
   * @return
   */
  List<Venue> retrieveByCategory(VenueCategory category);

  /**
   * TODO: document this method.
   * 
   * @param param
   * @return
   */
  List<Venue> findByNameOrAcronym(String param);
}
