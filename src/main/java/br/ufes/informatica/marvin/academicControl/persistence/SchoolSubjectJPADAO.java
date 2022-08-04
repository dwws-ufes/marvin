package br.ufes.informatica.marvin.academicControl.persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.informatica.marvin.academicControl.domain.SchoolSubject;

@Stateless
public class SchoolSubjectJPADAO extends BaseJPADAO<SchoolSubject> implements SchoolSubjectDAO {
	private static final long serialVersionUID = 1L;

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(SchoolSubjectJPADAO.class.getCanonicalName());

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public List<SchoolSubject> retrieveSchoolSubjects() {
		logger.log(Level.FINE, "Retrieving all School Subjects in the system");
		List<SchoolSubject> result = this.retrieveAll();
		logger.log(Level.INFO, "Retrieving School Subjects returned {0} results", result.size());
		return result;
	}

	@Override
	public SchoolSubject retrieveSubjectById(Long id) {
		return this.retrieveById(id);
	}
}