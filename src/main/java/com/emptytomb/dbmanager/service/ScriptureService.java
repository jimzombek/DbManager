package com.emptytomb.dbmanager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emptytomb.dbmanager.dao.DaoException;
import com.emptytomb.dbmanager.dao.ScriptureDao;
import com.emptytomb.dbmanager.dao.jdbc.ScriptureDaoJdbc;
import com.emptytomb.dbmanager.domain.Scripture;
import com.google.gson.Gson;

/**
* The ScriptureService class implements the CRUD service layer for the Scripture resource.
* 
* <p><b>Note:</b> This class is implemented as a Singleton. Only one instance of this class
* can exist at a time.</p>
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-07-01
*/
public class ScriptureService {
  private Logger logger = LoggerFactory.getLogger(ScriptureService.class);
  private static ScriptureService instance = null;
  private static ScriptureDao scriptureDAO = null;
  private static Gson gson = new Gson();
   
  private ScriptureService() {
      scriptureDAO = ScriptureDaoJdbc.getInstance();
  }

  /**
   * This method returns the single instance of the ScriptureService object.
   * 
   * @return  the ScriptureService object
   * 
  */
  public static ScriptureService getInstance() {
      if (instance == null) {      
          synchronized (ScriptureService.class) {
              if (instance == null) {
                  instance = new ScriptureService();
              } 
          }
      }
      return instance;
  }
  
  /**
   * This method returns the JSON representation of the Scripture object associated with the
   * specified scripture identifier.
   * 
   * @param   id  the unique id of the scripture to return.
   * @return      the JSON representation of the Scripture object
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String getScripture(int id) throws ServiceException {
	  String result = null;
	  try {
	      Scripture scripture = scriptureDAO.getScripture(id);
	      result = gson.toJson(scripture);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": getScripture() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, e.getReason());
	  }
      return result;
  }
  
  /**
   * This method returns the JSON representation of all the Scripture objects.
   * 
   * @return      the JSON representation of all the Scripture objects
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String getScriptures() throws ServiceException {
	  String result = null;
	  try {
		  List<Scripture> scriptures = scriptureDAO.getScriptures();
	      result = gson.toJson(scriptures);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": getScriptures() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, e.getReason());
	  }
      return result;
  }
  
  /**
   * This method adds the Scripture object.
   * 
   * @param   scriptureJson the JSON representation of the Scripture object.
   * @return      scriptureId of Scripture added if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String addScripture(String scriptureJson) throws ServiceException {
	  String result = null;
	  try {
		  Scripture scripture = gson.fromJson(scriptureJson, Scripture.class);
		  int scriptureId = scriptureDAO.addScripture(scripture);
	      result = gson.toJson("scriptureId : " + scriptureId);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": addScripture() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
  
  /**
   * This method updates the Scripture object.
   * 
   * @param   scriptureJson the JSON representation of the Scripture object.
   * @return      SUCCESS:200 if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String updateScripture(String scriptureJson) throws ServiceException {
	  String result = null;
	  try {
		  Scripture scripture = gson.fromJson(scriptureJson, Scripture.class);
		  scriptureDAO.updateScripture(scripture);
	      result = gson.toJson("SUCCESS : 200");
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": updateScripture() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
  
  /**
   * This method deletes the Scripture object associated with the specified 
   * scripture identifier.
   * 
   * @param   id  scripture id to delete.
   * @return      SUCCESS:200 if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String deleteScripture(int id) throws ServiceException {
	  String result = null;
	  try {
		  scriptureDAO.deleteScripture(id);
	      result = gson.toJson("SUCCESS : 200");
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": deleteScripture() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
}