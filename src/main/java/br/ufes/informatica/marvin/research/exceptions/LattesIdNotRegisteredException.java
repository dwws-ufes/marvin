package br.ufes.informatica.marvin.research.exceptions;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
public class LattesIdNotRegisteredException extends Exception {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** TODO: document this field. */
  private Long lattesId;

  /** TODO: document this field. */
  private String researcherName;

  /** Constructor. */
  public LattesIdNotRegisteredException(Long lattesId, String researcherName) {
    this.lattesId = lattesId;
    this.researcherName = researcherName;
  }

  /** Getter for lattesId. */
  public Long getLattesId() {
    return lattesId;
  }

  /** Getter for researcherName. */
  public String getResearcherName() {
    return researcherName;
  }
}
