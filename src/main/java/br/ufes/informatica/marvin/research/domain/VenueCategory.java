package br.ufes.informatica.marvin.research.domain;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
public enum VenueCategory {
  CONFERENCE("Conference"), 
  JOURNAL("Journal");

  /** TODO: document this field. */
  String name;

  /** Getter for category name. */
  public String getName() {
    return name;
  }

  /** Setter for category name. */
  public void setName(String name) {
    this.name = name;
  }

  /** Constructor. */
  VenueCategory(String name) {
    this.name = name;
  }
}
