package br.ufes.informatica.marvin.research.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.research.domain.Publication;
import br.ufes.informatica.marvin.research.domain.Publication_;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Stateless
public class PublicationJPADAO extends BaseJPADAO<Publication> implements PublicationDAO {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** Logger for this class. */
  private static final Logger logger = Logger.getLogger(PublicationJPADAO.class.getCanonicalName());

  /** The application's persistent context provided by the application server. */
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public long retrieveCountByAcademic(Academic academic) {
    logger.log(Level.FINE, "Retrieving the publication count of academic \"{0}\" ({1})...",
        new Object[] {academic.getName(), academic.getEmail()});

    // Constructs the query over the Publication class, with Long result.
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> cq = cb.createQuery(Long.class);
    Root<Publication> root = cq.from(Publication.class);
    cq.select(cb.count(root));

    // Filters the query with the academic.
    cq.where(cb.equal(root.get(Publication_.owner), academic));

    // Retrieve the value and return.
    TypedQuery<Long> query = entityManager.createQuery(cq);
    Long count = query.getSingleResult();
    logger.log(Level.INFO, "Retrieve publication count of academic \"{0}\" ({1}) returned: {2}",
        new Object[] {academic.getName(), academic.getEmail(), count});
    return count;
  }

  @Override
  public List<Publication> retrieveByAcademic(Academic academic) {
    logger.log(Level.FINE, "Retrieving the publications of academic \"{0}\" ({1})...",
        new Object[] {academic.getName(), academic.getEmail()});

    // Constructs the query over the Publication class.
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Publication> cq = cb.createQuery(Publication.class);
    Root<Publication> root = cq.from(Publication.class);

    // Filters the query with the academic.
    cq.where(cb.equal(root.get(Publication_.owner), academic));
    List<Publication> result = entityManager.createQuery(cq).getResultList();
    logger.log(Level.INFO, "Retrieve publications of academic \"{0}\" ({1}) returned {2} results.",
        new Object[] {academic.getName(), academic.getEmail(), result.size()});
    return result;
  }

  @Override
  public List<Publication> retrieveByAcademicAndYearRange(Academic academic, Integer startYear,
      Integer endYear) {
    logger.log(Level.FINE,
        "Retrieving the publications of academic \"{0}\" ({1}) within range [{2}--{3}]...",
        new Object[] {academic.getName(), academic.getEmail(), startYear, endYear});

    // Constructs the query over the Publication class.
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Publication> cq = cb.createQuery(Publication.class);
    Root<Publication> root = cq.from(Publication.class);

    // Filters the query with the academic and the year range.
    List<Predicate> constraints = new ArrayList<>();
    constraints.add(cb.equal(root.get(Publication_.owner), academic));
    if (startYear != null)
      constraints.add(cb.ge(root.get(Publication_.year), startYear));
    if (endYear != null)
      constraints.add(cb.le(root.get(Publication_.year), endYear));
    cq.where(cb.and(constraints.toArray(new Predicate[] {})));
    List<Publication> result = entityManager.createQuery(cq).getResultList();
    logger.log(Level.INFO,
        "Retrieve publications of academic \"{0}\" ({1}) within range [{2}--{3}] returned {4} results.",
        new Object[] {academic.getName(), academic.getEmail(), startYear, endYear, result.size()});
    return result;
  }
}
