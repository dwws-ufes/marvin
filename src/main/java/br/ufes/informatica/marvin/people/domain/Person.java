package br.ufes.informatica.marvin.people.domain;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

/**
 * Domain class that represents people and their most basic attributes, such as name, birthdate and
 * gender.
 * 
 * <i>This class is part of a "Legal Entity" mini framework for EJB3.</i>
 * 
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@MappedSuperclass
public class Person extends PersistentObjectSupport implements Comparable<Person> {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** The person's name. */
  @Basic
  @NotNull
  @Size(max = 100)
  protected String name;

  /** The person's birth date. */
  @Temporal(TemporalType.DATE)
  protected Date birthDate;

  /** The person's gender: 'M' (male) or 'F' (female). */
  @Basic
  protected Character gender;

  /** The person's CPF. It is a unique number that define a person as Brazilian citizen. */
  @Basic
  @NotNull
  @Size(max = 17)
  @Column(unique = true)
  protected String cpf;

  /** The person's RG. It is a unique number used to identify a person. */
  @Basic
  @Size(max = 20)
  @Column(unique = true)
  protected String identityCard;

  /** The person's birth city. */
  @Basic
  @Size(max = 30)
  protected String birthCity;

  /** The country where the person was born. */
  @Basic
  @Size(max = 30)
  protected String nationality;

  /** Getter for cpf. */
  public String getCpf() {
    return cpf;
  }

  /** Setter for cpf. */
  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  /** Getter for identityCard. */
  public String getIdentityCard() {
    return identityCard;
  }

  /** Setter for identityCard. */
  public void setIdentityCard(String identityCard) {
    this.identityCard = identityCard;
  }

  /** Getter for birthCity. */
  public String getBirthCity() {
    return birthCity;
  }

  /** Setter for birthCity. */
  public void setBirthCity(String birthCity) {
    this.birthCity = birthCity;
  }

  /** Getter for nationality. */
  public String getNationality() {
    return nationality;
  }

  /** Setter for nationality. */
  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  /** Getter for name. */
  public String getName() {
    return name;
  }

  /** Setter for name. */
  public void setName(String name) {
    this.name = name;
  }

  /** Getter for birthDate. */
  public Date getBirthDate() {
    return birthDate;
  }

  /** Setter for birthDate. */
  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  /** Getter for gender. */
  public Character getGender() {
    return gender;
  }

  /** Setter for gender. */
  public void setGender(Character gender) {
    this.gender = gender;
  }

  @Override
  public int compareTo(Person o) {
    // Compare the persons' names
    if (name == null)
      return 1;
    if (o.name == null)
      return -1;
    int cmp = name.compareTo(o.name);
    if (cmp != 0)
      return cmp;

    // If it's the same name, check if it's the same entity.
    return uuid.compareTo(o.uuid);
  }

  @Override
  public String toString() {
    return name;
  }
}
