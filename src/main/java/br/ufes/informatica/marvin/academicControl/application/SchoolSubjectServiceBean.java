package br.ufes.informatica.marvin.academicControl.application;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudException;
import br.ufes.inf.nemo.jbutler.ejb.application.filters.Filter;
import br.ufes.inf.nemo.jbutler.ejb.controller.PersistentObjectConverterFromId;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.marvin.academicControl.domain.SchoolSubject;
import br.ufes.informatica.marvin.core.domain.Role;

@Stateless
@RolesAllowed({"SysAdmin", "Professor"})
public class SchoolSubjectServiceBean implements SchoolSubjectService {
	private static final long serialVersionUID = 1L;
  
	@Override
	public void validateCreate(SchoolSubject entity) throws CrudException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void validateUpdate(SchoolSubject entity) throws CrudException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void validateDelete(SchoolSubject entity) throws CrudException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void create(SchoolSubject entity) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public SchoolSubject retrieve(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void update(SchoolSubject entity) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void delete(SchoolSubject entity) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public BaseDAO<SchoolSubject> getDAO() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void authorize() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public long countFiltered(Filter<?> filterType, String filter) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public List<SchoolSubject> list(int... interval) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<SchoolSubject> filter(Filter<?> filter, String filterParam, int... interval) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public SchoolSubject fetchLazy(SchoolSubject entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public PersistentObjectConverterFromId<Role> getRoleConverter() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Role> findRoleByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
