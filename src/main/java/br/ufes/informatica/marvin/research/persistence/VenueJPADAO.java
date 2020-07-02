package br.ufes.informatica.marvin.research.persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.informatica.marvin.research.domain.Venue;
import br.ufes.informatica.marvin.research.domain.VenueCategory;
import br.ufes.informatica.marvin.research.domain.Venue_;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Stateless
public class VenueJPADAO extends BaseJPADAO<Venue> implements VenueDAO {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** Logger for this class. */
  private static final Logger logger = Logger.getLogger(VenueJPADAO.class.getCanonicalName());

  /** The application's persistent context provided by the application server. */
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public List<Venue> retrieveByCategory(VenueCategory category) {
    logger.log(Level.FINE, "Retrieving the venues of category \"{0}\".",
        new Object[] {category.getName()});

    // Constructs the query over the Publication class.
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Venue> cq = cb.createQuery(Venue.class);
    Root<Venue> root = cq.from(Venue.class);

    // Filters the query with the academic.
    cq.where(cb.equal(root.get(Venue_.category), category));
    List<Venue> result = entityManager.createQuery(cq).getResultList();
    logger.log(Level.INFO, "Retrieve venues of category \"{0}\" returned {1} results.",
        new Object[] {category.getName(), result.size()});
    return result;
  }

  @Override
  public List<Venue> findByNameOrAcronym(String param) {
    logger.log(Level.FINE, "Finding venues whose name/acronym contain \"{0}\"...", param);

    // Constructs the query over the Venue class.
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Venue> cq = cb.createQuery(Venue.class);
    Root<Venue> root = cq.from(Venue.class);

    // Filters the query with the name or acronym.
    param = "%" + param + "%";
    cq.where(
        cb.or(cb.like(root.get(Venue_.name), param), cb.like(root.get(Venue_.acronym), param)));
    List<Venue> result = entityManager.createQuery(cq).getResultList();
    logger.log(Level.INFO, "Found {0} venues whose name/acronym contains \"{1}\".",
        new Object[] {result.size(), param});
    return result;
  }
}
