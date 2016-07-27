package com.emptytomb.dbmanager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emptytomb.dbmanager.dao.DaoException;
import com.emptytomb.dbmanager.dao.PassageDao;
import com.emptytomb.dbmanager.domain.Passage;
import com.google.gson.Gson;

/**
* The PassageService class implements the CRUD service layer for the Passage resource.
* 
* <p><b>Note:</b> This class is implemented as a Singleton. Only one instance of this class
* can exist at a time.</p>
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-08-01
*/
public class PassageService {
  private Logger logger = LoggerFactory.getLogger(PassageService.class);
  private static PassageService instance = null;
  private static PassageDao passageDAO = null;
  private static Gson gson = new Gson();
   
  private PassageService() {
      passageDAO = PassageDao.getInstance();
  }

  /**
   * This method returns the single instance of the PassageService object.
   * 
   * @return  the PassageService object
   * 
  */
  public static PassageService getInstance() {
      if (instance == null) {      
          synchronized (PassageService.class) {
              if (instance == null) {
                  instance = new PassageService();
              } 
          }
      }
      return instance;
  }
  
  /**
   * This method returns the JSON representation of the Passage object associated with the
   * specified passage identifier.
   * 
   * @param   id  the unique id of the passage to return.
   * @return      the JSON representation of the passage object
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String getPassage(int id) throws ServiceException {
	  String result = null;
	  try {
	      Passage passage = passageDAO.get(id);
	      result = gson.toJson(passage);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": getPassage() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, e.getReason());
	  }
      return result;
  }
  
  /**
   * This method returns the JSON representation of all the Passage objects.
   * 
   * @return      the JSON representation of all the Passage objects
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String getPassages() throws ServiceException {
	  String result = null;
	  try {
		  List<Passage> passages = passageDAO.list();
	      result = gson.toJson(passages);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": getPassages() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, e.getReason());
	  }
      return result;
  }
  
  /**
   * This method adds the Passage object.
   * 
   * @param   passageJson the JSON representation of the Passage object.
   * @return      passageId of Passage added if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String addPassage(String passageJson) throws ServiceException {
	  String result = null;
	  try {
		  Passage passage = gson.fromJson(passageJson, Passage.class);
		  int passageId = passageDAO.add(passage);
	      result = gson.toJson("passageId : " + passageId);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": addPassage() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
  
  /**
   * This method updates the Passage object.
   * 
   * @param   passageJson the JSON representation of the Passage object.
   * @return      SUCCESS:200 if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String updatePassage(String passageJson) throws ServiceException {
	  String result = null;
	  try {
		  Passage passage = gson.fromJson(passageJson, Passage.class);
		  passageDAO.update(passage);
	      result = gson.toJson("SUCCESS : 200");
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": updatePassage() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
  
  /**
   * This method deletes the Passage object associated with the specified 
   * passage identifier.
   * 
   * @param   id  passage id to delete.
   * @return      SUCCESS:200 if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String deletePassage(int id) throws ServiceException {
	  String result = null;
	  try {
		  passageDAO.delete(id);
	      result = gson.toJson("SUCCESS : 200");
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": deletePassage() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
}