package br.ufes.informatica.marvin.admin.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.application.filters.LikeFilter;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.inf.nemo.jbutler.ejb.controller.PersistentObjectConverterFromId;
import br.ufes.informatica.marvin.core.application.ManageAcademicsService;
import br.ufes.informatica.marvin.core.domain.Academic;
import br.ufes.informatica.marvin.core.domain.Role;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@Named
@SessionScoped
public class ManageAcademicsController extends CrudController<Academic> {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** Logger for this class. */
  private static final Logger logger =
      Logger.getLogger(ManageAcademicsController.class.getCanonicalName());

  /** TODO: document this field. */
  @EJB
  private ManageAcademicsService manageAcademicsService;

  @Override
  protected CrudService<Academic> getCrudService() {
    return manageAcademicsService;
  }

  @Override
  protected void initFilters() {
    addFilter(new LikeFilter("manageAcademics.filter.byName", "name",
        getI18nMessage("msgsCore", "manageAcademics.text.filter.byName")));
  }

  /**
   * Analyzes the name that was given to the administrator and, if the short name field is still
   * empty, suggests a value for it based on the given name.
   * 
   * This method is intended to be used with AJAX.
   */
  public void suggestShortName() {
    // If the name was filled and the short name is still empty, suggest the first name as short
    // name.
    String name = selectedEntity.getName();
    String shortName = selectedEntity.getShortName();
    if ((name != null) && ((shortName == null) || (shortName.length() == 0))) {
      int idx = name.indexOf(" ");
      selectedEntity.setShortName((idx == -1) ? name : name.substring(0, idx).trim());
      logger.log(Level.FINE, "Suggested \"{0}\" as short name for \"{1}\"",
          new Object[] {selectedEntity.getShortName(), name});
    } else
      logger.log(Level.FINEST,
          "Short name not suggested: empty name or short name already filled (name is \"{0}\", short name is \"{1}\")",
          new Object[] {name, shortName});
  }

  /**
   * TODO: document this method.
   * 
   * @return
   */
  public PersistentObjectConverterFromId<Role> getRoleConverter() {
    return manageAcademicsService.getRoleConverter();
  }

  /**
   * TODO: document this method.
   * 
   * @return
   */
  public List<Role> getSelectedRoles() {
    Set<Role> roles = selectedEntity.getRoles();
    return (roles == null) ? new ArrayList<>() : new ArrayList<>(roles);
  }

  /**
   * TODO: document this method.
   * 
   * @param selectedRoles
   */
  public void setSelectedRoles(List<Role> selectedRoles) {
    selectedEntity
        .setRoles((selectedRoles == null) ? new HashSet<>() : new HashSet<>(selectedRoles));
  }

  /**
   * TODO: document this method.
   * 
   * @param query
   * @return
   */
  public List<Role> completeRoles(String query) {
    return manageAcademicsService.findRoleByName(query);
  }
}
