package com.emptytomb.dbmanager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emptytomb.dbmanager.dao.DaoException;
import com.emptytomb.dbmanager.dao.TranslationDao;
import com.emptytomb.dbmanager.domain.Translation;
import com.google.gson.Gson;

/**
* The TranslationService class implements the CRUD service layer for the Translation resource.
* 
* <p><b>Note:</b> This class is implemented as a Singleton. Only one instance of this class
* can exist at a time.</p>
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-07-01
*/
public class TranslationService {
  private Logger logger = LoggerFactory.getLogger(TranslationService.class);
  private static TranslationService instance = null;
  private static TranslationDao translationDAO = null;
  private static Gson gson = new Gson();
   
  private TranslationService() {
      translationDAO = TranslationDao.getInstance();
  }

  /**
   * This method returns the single instance of the TranslationService object.
   * 
   * @return  the TranslationService object
   * 
  */
  public static TranslationService getInstance() {
      if (instance == null) {      
          synchronized (TranslationService.class) {
              if (instance == null) {
                  instance = new TranslationService();
              } 
          }
      }
      return instance;
  }
  
  /**
   * This method returns the JSON representation of the Translation object associated with the
   * specified translation identifier.
   * 
   * @param   id  the unique id of the translation to return.
   * @return      the JSON representation of the Translation object
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String getTranslation(int id) throws ServiceException {
	  String result = null;
	  try {
	      Translation translation = translationDAO.get(id);
	      result = gson.toJson(translation);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": getTranslation() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, e.getReason());
	  }
      return result;
  }
  
  /**
   * This method returns the JSON representation of all the Translation objects.
   * 
   * @return      the JSON representation of all the Translation objects
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String getTranslations() throws ServiceException {
	  String result = null;
	  try {
		  List<Translation> translations = translationDAO.list();
	      result = gson.toJson(translations);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": getTranslations() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, e.getReason());
	  }
      return result;
  }
  
  /**
   * This method adds the Translation object.
   * 
   * @param   translationJson the JSON representation of the Translation object.
   * @return      translationId of Translation added if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String addTranslation(String translationJson) throws ServiceException {
	  String result = null;
	  try {
		  Translation translation = gson.fromJson(translationJson, Translation.class);
		  int translationId = translationDAO.add(translation);
	      result = gson.toJson("translationId : " + translationId);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": addTranslation() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
  
  /**
   * This method updates the Translation object.
   * 
   * @param   translationJson the JSON representation of the Translation object.
   * @return      SUCCESS:200 if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String updateTranslation(String translationJson) throws ServiceException {
	  String result = null;
	  try {
		  Translation translation = gson.fromJson(translationJson, Translation.class);
		  translationDAO.update(translation);
	      result = gson.toJson("SUCCESS : 200");
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": updateTranslation() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
  
  /**
   * This method deletes the Translation object associated with the specified 
   * translation identifier.
   * 
   * @param   id  translation id to delete.
   * @return      SUCCESS:200 if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String deleteTranslation(int id) throws ServiceException {
	  String result = null;
	  try {
		  translationDAO.delete(id);
	      result = gson.toJson("SUCCESS : 200");
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": deleteTranslation() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
}