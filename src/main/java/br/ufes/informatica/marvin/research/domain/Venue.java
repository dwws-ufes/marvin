package br.ufes.informatica.marvin.research.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Entity
public class Venue extends PersistentObjectSupport implements Comparable<Venue> {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** TODO: document this field. */
  private String acronym;

  /** TODO: document this field. */
  private String issn;

  /** TODO: document this field. */
  @NotNull
  private String name;

  /** TODO: document this field. */
  @Enumerated(EnumType.STRING)
  @NotNull
  private VenueCategory category;

  /** Constructor. */
  protected Venue() {}

  /** Constructor. */
  public Venue(String name, String category) {
    // TODO Auto-generated constructor stub
    this.name = name;
    setCategory(category);
  }

  /** Constructor. */
  public Venue(String name) {
    this.name = name;
  }

  /** Getter for acronym. */
  public String getAcronym() {
    return acronym;
  }

  /** Setter for acronym. */
  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  /** Getter for issn. */
  public String getIssn() {
    return issn;
  }

  /** Setter for issn. */
  public void setIssn(String issn) {
    this.issn = issn;
  }

  /** Getter for name. */
  public String getName() {
    return name;
  }

  /** Setter for name. */
  public void setName(String name) {
    this.name = name;
  }

  /** Getter for category. */
  public VenueCategory getCategory() {
    return category;
  }

  /** Setter for category. */
  public void setCategory(VenueCategory category) {
    this.category = category;
  }

  /** Setter for category. */
  public void setCategory(String category) {
    if (category.equals("Conference"))
      this.category = VenueCategory.CONFERENCE;
    else
      this.category = VenueCategory.JOURNAL;
  }

  @Override
  public int compareTo(Venue o) {
    int cmp = 0;

    // Compares by name.
    cmp = name.compareTo(o.name);
    return cmp;
  }
}
