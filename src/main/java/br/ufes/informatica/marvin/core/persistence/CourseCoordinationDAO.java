package br.ufes.informatica.marvin.core.persistence;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Course;
import br.ufes.informatica.marvin.core.domain.CourseCoordination;

/**
 * Interface for a DAO for objects of the Course Coordination domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any) are specified in this class.
 * 
 * @author Gabriel Martins Miranda (garielmartinsmiranda@gmail.com)
 * @version 1.0
 */
@Local
public interface CourseCoordinationDAO extends BaseDAO<CourseCoordination> {
	public boolean academicWasCoordinator(Academic academic);

	public boolean courseHasCoordinations(Course course);

	public boolean courseHasActiveCoordinations(Course course);
}
