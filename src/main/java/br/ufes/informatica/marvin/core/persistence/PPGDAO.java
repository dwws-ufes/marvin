package br.ufes.informatica.marvin.core.persistence;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.core.domain.PPG;

@Local
public interface PPGDAO extends BaseDAO<PPG> {

}
