package br.ufes.informatica.marvin.research.persistence;

import java.util.List;
import javax.ejb.Local;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.research.domain.Publication;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Local
public interface PublicationDAO extends BaseDAO<Publication> {
  /**
   * TODO: document this method.
   * 
   * @param academic
   * @return
   */
  long retrieveCountByAcademic(Academic academic);

  /**
   * TODO: document this method.
   * 
   * @param academic
   * @return
   */
  List<Publication> retrieveByAcademic(Academic academic);

  /**
   * TODO: document this method.
   * 
   * @param academic
   * @param startYear
   * @param endYear
   * @return
   */
  List<Publication> retrieveByAcademicAndYearRange(Academic academic, Integer startYear,
      Integer endYear);
}
