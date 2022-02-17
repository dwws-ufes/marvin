package br.ufes.informatica.marvin.research.application;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.research.domain.Qualis;
import br.ufes.informatica.marvin.research.persistence.QualisDAO;

@Stateless
@PermitAll
public class ManageQualisServiceBean extends CrudServiceBean<Qualis> implements ManageQualisService {
	private static final long serialVersionUID = 1L;

	@EJB
	private QualisDAO qualisDAO;

	@Override
	public BaseDAO<Qualis> getDAO() {
		return qualisDAO;
	}

	@Override
	public void uploadQualisCSV(InputStream inputStream) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		String line = reader.readLine();
		while (line != null) {
			String[] info = line.split(";");
			Qualis obj = new Qualis(info[0], Float.parseFloat(info[1]));
			obj.setDtStart(new Date());
			qualisDAO.save(obj);
			line = reader.readLine();
		}
	}
}
