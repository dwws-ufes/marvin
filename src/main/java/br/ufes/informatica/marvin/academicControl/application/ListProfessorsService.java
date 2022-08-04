package br.ufes.informatica.marvin.academicControl.application;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import br.ufes.informatica.marvin.core.domain.Academic;

@Local
public interface ListProfessorsService extends Serializable {
	List<Academic> listProfessors();
}