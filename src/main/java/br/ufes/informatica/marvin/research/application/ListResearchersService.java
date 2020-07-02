package br.ufes.informatica.marvin.research.application;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Local;
import br.ufes.informatica.marvin.core.domain.Academic;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Local
public interface ListResearchersService extends Serializable {
  /**
   * TODO: document this method.
   * 
   * @return
   */
  List<Academic> listResearchers();

  /**
   * TODO: document this method.
   * 
   * @param researcher
   * @return
   */
  Long countPublications(Academic researcher);
}
