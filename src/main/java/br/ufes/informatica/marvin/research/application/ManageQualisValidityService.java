package br.ufes.informatica.marvin.research.application;

import java.util.Date;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.marvin.research.domain.QualisValidity;

@Local
public interface ManageQualisValidityService extends CrudService<QualisValidity> {

	QualisValidity findByDates(Date dtStart, Date DtEnd);
}
