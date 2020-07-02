package br.ufes.informatica.marvin.core.application;

import br.ufes.informatica.marvin.core.domain.Academic;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
public class LoginEvent {
  /** TODO: document this field. */
  private Academic academic;

  /** Constructor. */
  public LoginEvent(Academic academic) {
    this.academic = academic;
  }

  /** Getter for academic. */
  public Academic getAcademic() {
    return academic;
  }
}
