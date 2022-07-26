package br.ufes.informatica.marvin.research.application;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.marvin.core.domain.PPG;
import br.ufes.informatica.marvin.research.domain.Venue;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Local
public interface ManageVenuesService extends CrudService<Venue> {

	List<Venue> findVenueByName(String name);

	void uploadVenueCV(InputStream inputStream, Date dtStart, Date dtEnd, PPG ppg) throws Exception;
}
