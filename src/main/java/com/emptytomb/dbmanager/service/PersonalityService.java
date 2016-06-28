package com.emptytomb.dbmanager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emptytomb.dbmanager.dao.DaoException;
import com.emptytomb.dbmanager.dao.PersonalityDao;
import com.emptytomb.dbmanager.dao.jdbc.PersonalityDaoJdbc;
import com.emptytomb.dbmanager.domain.Personality;
import com.google.gson.Gson;

/**
* The PersonalityService class implements the CRUD service layer for the Personality resource.
* 
* <p><b>Note:</b> This class is implemented as a Singleton. Only one instance of this class
* can exist at a time.</p>
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-05-31
*/
public class PersonalityService {
  private Logger logger = LoggerFactory.getLogger(PersonalityService.class);
  private static PersonalityService instance = null;
  private static PersonalityDao personalityDAO = null;
  private static Gson gson = new Gson();
   
  private PersonalityService() {
      personalityDAO = PersonalityDaoJdbc.getInstance();
  }

  /**
   * This method returns the single instance of the PersonalityService object.
   * 
   * @return  the PersonalityService object
   * 
  */
  public static PersonalityService getInstance() {
      if (instance == null) {      
          synchronized (PersonalityService.class) {
              if (instance == null) {
                  instance = new PersonalityService();
              } 
          }
      }
      return instance;
  }
  
  /**
   * This method returns the JSON representation of the Personality object associated with the
   * specified personality identifier.
   * 
   * @param   id  the unique id of the personality to return.
   * @return      the JSON representation of the Personality object
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String getPersonality(int id) throws ServiceException {
	  String result = null;
	  try {
	      Personality personality = personalityDAO.getPersonality(id);
	      result = gson.toJson(personality);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": getPersonality() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, e.getReason());
	  }
      return result;
  }
  
  /**
   * This method returns the JSON representation of all the Personality objects.
   * 
   * @return      the JSON representation of all the Personality objects
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String getPersonalities() throws ServiceException {
	  String result = null;
	  try {
		  List<Personality> personalities = personalityDAO.getPersonalities();
	      result = gson.toJson(personalities);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": getPersonalities() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, e.getReason());
	  }
      return result;
  }
  
  /**
   * This method adds the Personality object.
   * 
   * @param   personalityJson the JSON representation of the Personality object.
   * @return      personalityId of Personality added if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String addPersonality(String personalityJson) throws ServiceException {
	  String result = null;
	  try {
		  Personality personality = gson.fromJson(personalityJson, Personality.class);
		  int personalityId = personalityDAO.addPersonality(personality);
	      result = gson.toJson("personalityId : " + personalityId);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": addPersonality() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
  
  /**
   * This method updates the Personality object.
   * 
   * @param   personalityJson the JSON representation of the Personality object.
   * @return      SUCCESS:200 if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String updatePersonality(String personalityJson) throws ServiceException {
	  String result = null;
	  try {
		  Personality personality = gson.fromJson(personalityJson, Personality.class);
		  personalityDAO.updatePersonality(personality);
	      result = gson.toJson("SUCCESS : 200");
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": updatePersonality() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
  
  /**
   * This method deletes the Personality object associated with the specified 
   * Personality identifier.
   * 
   * @param   id  personality id to delete.
   * @return      SUCCESS:200 if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String deletePersonality(int id) throws ServiceException {
	  String result = null;
	  try {
		  personalityDAO.deletePersonality(id);
	      result = gson.toJson("SUCCESS : 200");
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": deletePersonality() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
}