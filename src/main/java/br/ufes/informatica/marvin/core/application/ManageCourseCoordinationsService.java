package br.ufes.informatica.marvin.core.application;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Course;
import br.ufes.informatica.marvin.core.domain.CourseCoordination;
import br.ufes.informatica.marvin.core.domain.Role;

/**
 * TODO: document this type.
 *
 * @author Gabriel Martins Miranda (gabrielmartinsmiranda@gmail.com)
 * @version 1.0
 */
@Local
public interface ManageCourseCoordinationsService extends CrudService<CourseCoordination> {

	public List<Role> findRoleByName(String name);

	public List<Academic> retrieveAcademicbyRole(String roleName);

	public Map<String, Course> retrieveCourses(boolean hasCoordinator);

	public Map<String, Academic> retrieveAcademics(boolean isCoordinator);

	public void disable(CourseCoordination entity);
}
