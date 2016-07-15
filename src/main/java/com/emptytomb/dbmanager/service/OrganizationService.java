package com.emptytomb.dbmanager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emptytomb.dbmanager.dao.DaoException;
import com.emptytomb.dbmanager.dao.OrganizationDao;
import com.emptytomb.dbmanager.dao.jdbc.OrganizationDaoJdbc;
import com.emptytomb.dbmanager.domain.Organization;
import com.google.gson.Gson;

/**
* The PersonalityService class implements the CRUD service layer for the Personality resource.
* 
* <p><b>Note:</b> This class is implemented as a Singleton. Only one instance of this class
* can exist at a time.</p>
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-07-01
*/
public class OrganizationService {
  private Logger logger = LoggerFactory.getLogger(OrganizationService.class);
  private static OrganizationService instance = null;
  private static OrganizationDao organizationDAO = null;
  private static Gson gson = new Gson();
   
  private OrganizationService() {
      organizationDAO = OrganizationDaoJdbc.getInstance();
  }

  /**
   * This method returns the single instance of the OrganizationService object.
   * 
   * @return  the OrganizationyService object
   * 
  */
  public static OrganizationService getInstance() {
      if (instance == null) {      
          synchronized (OrganizationService.class) {
              if (instance == null) {
                  instance = new OrganizationService();
              } 
          }
      }
      return instance;
  }
  
  /**
   * This method returns the JSON representation of the Organization object associated with the
   * specified organization identifier.
   * 
   * @param   id  the unique id of the organization to return.
   * @return      the JSON representation of the Organization object
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String getOrganization(int id) throws ServiceException {
	  String result = null;
	  try {
	      Organization organization = organizationDAO.getOrganization(id);
	      result = gson.toJson(organization);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": getOrganization() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, e.getReason());
	  }
      return result;
  }
  
  /**
   * This method returns the JSON representation of all the Organization objects.
   * 
   * @return      the JSON representation of all the Organization objects
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String getOrganizations() throws ServiceException {
	  String result = null;
	  try {
		  List<Organization> organizations = organizationDAO.getOrganizations();
	      result = gson.toJson(organizations);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": getOrganizations() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, e.getReason());
	  }
      return result;
  }
  
  /**
   * This method adds the Organization object.
   * 
   * @param   organizationJson the JSON representation of the Organization object.
   * @return      organizationId of Organization added if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String addOrganization(String organizationJson) throws ServiceException {
	  String result = null;
	  try {
		  Organization organization = gson.fromJson(organizationJson, Organization.class);
		  int organizationId = organizationDAO.addOrganization(organization);
	      result = gson.toJson("organizationId : " + organizationId);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": addOrganization() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
  
  /**
   * This method updates the Organization object.
   * 
   * @param   organizationJson the JSON representation of the Organization object.
   * @return      SUCCESS:200 if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String updateOrganization(String organizationJson) throws ServiceException {
	  String result = null;
	  try {
		  Organization organization = gson.fromJson(organizationJson, Organization.class);
		  organizationDAO.updateOrganization(organization);
	      result = gson.toJson("SUCCESS : 200");
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": updateOrganization() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
  
  /**
   * This method deletes the Organization object associated with the specified 
   * organization identifier.
   * 
   * @param   id  organization id to delete.
   * @return      SUCCESS:200 if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String deleteOrganization(int id) throws ServiceException {
	  String result = null;
	  try {
		  organizationDAO.deleteOrganization(id);
	      result = gson.toJson("SUCCESS : 200");
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": deleteOrganization() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
}