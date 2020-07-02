package br.ufes.informatica.marvin.research.domain;

import java.util.Set;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import br.ufes.informatica.marvin.core.domain.Academic;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Entity
public class BibGenConfiguration extends PersistentObjectSupport {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** TODO: document this field. */
  @Size(max = 30)
  private String name;

  /** TODO: document this field. */
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<BibGenResearcher> researchers = new TreeSet<>();

  /** TODO: document this field. */
  @Min(0)
  private Integer startYear;

  /** TODO: document this field. */
  @Min(0)
  private Integer endYear;

  /** Getter for name. */
  public String getName() {
    return name;
  }

  /** Setter for name. */
  public void setName(String name) {
    this.name = name;
  }

  /** Getter for researchers. */
  public Set<BibGenResearcher> getResearchers() {
    return researchers;
  }

  /** Setter for researchers. */
  protected void setResearchers(Set<BibGenResearcher> researchers) {
    this.researchers = researchers;
  }

  /** Getter for startYear. */
  public Integer getStartYear() {
    return startYear;
  }

  /** Setter for startYear. */
  public void setStartYear(Integer startYear) {
    this.startYear = startYear;
  }

  /** Getter for endYear. */
  public Integer getEndYear() {
    return endYear;
  }

  /** Setter for endYear. */
  public void setEndYear(Integer endYear) {
    this.endYear = endYear;
  }

  /**
   * TODO: document this method.
   * 
   * @param researcher
   * @param startYear
   * @param endYear
   */
  public void addResearcher(Academic researcher, Integer startYear, Integer endYear) {
    researchers.add(new BibGenResearcher(researcher, startYear, endYear));
  }
}
