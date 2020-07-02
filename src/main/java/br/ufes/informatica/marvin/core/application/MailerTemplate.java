package br.ufes.informatica.marvin.core.application;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
public enum MailerTemplate {
  NEW_ACADEMIC_REGISTERED("NewAcademicRegistered"), 
  NEW_REGISTRATION("NewRegistration"), 
  RESET_PASSWORD("ResetPassword");

  /** TODO: document this field. */
  private String fileName;

  /** Constructor. */
  private MailerTemplate(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return fileName;
  }
}
