package com.emptytomb.dbmanager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emptytomb.dbmanager.dao.DaoException;
import com.emptytomb.dbmanager.dao.QuestionCategoryDao;
import com.emptytomb.dbmanager.domain.QuestionCategory;
import com.google.gson.Gson;

/**
* The QuestionCategoryService class implements the CRUD service layer for the QuestionCategoryService
* resource.
* 
* <p><b>Note:</b> This class is implemented as a Singleton. Only one instance of this class
* can exist at a time.</p>
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-08-01
*/
public class QuestionCategoryService {
  private Logger logger = LoggerFactory.getLogger(QuestionCategoryService.class);
  private static QuestionCategoryService instance = null;
  private static QuestionCategoryDao questionCategoryDAO = null;
  private static Gson gson = new Gson();
   
  private QuestionCategoryService() {
      questionCategoryDAO = QuestionCategoryDao.getInstance();
  }

  /**
   * This method returns the single instance of the CategoryService object.
   * 
   * @return  the QuestionCategoryService object
   * 
  */
  public static QuestionCategoryService getInstance() {
      if (instance == null) {      
          synchronized (QuestionCategoryService.class) {
              if (instance == null) {
                  instance = new QuestionCategoryService();
              } 
          }
      }
      return instance;
  }
  
  /**
   * This method returns the JSON representation of the QuestionCategory object associated with the
   * specified question category identifier.
   * 
   * @param   id  the unique id of the question category to return.
   * @return      the JSON representation of the question category object
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String get(int id) throws ServiceException {
	  String result = null;
	  try {
	      QuestionCategory questionCategory = questionCategoryDAO.get(id);
	      result = gson.toJson(questionCategory);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": get() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, e.getReason());
	  }
      return result;
  }
  
  /**
   * This method returns the JSON representation of all the QuestionCategory objects.
   * 
   * @return      the JSON representation of all the QuestionCategory objects
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String list() throws ServiceException {
	  String result = null;
	  try {
		  List<QuestionCategory> questionCategories = questionCategoryDAO.list();
	      result = gson.toJson(questionCategories);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": list() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, e.getReason());
	  }
      return result;
  }
  
  /**
   * This method adds the QuestionCategory object.
   * 
   * @param   questionCategoryJson the JSON representation of the QuestionCategory object.
   * @return      id of the QuestionCategory added if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String add(String questionCategoryJson) throws ServiceException {
	  String result = null;
	  try {
		  QuestionCategory questionCategory = gson.fromJson(questionCategoryJson, QuestionCategory.class);
		  int questionCategoryId = questionCategoryDAO.add(questionCategory);
	      result = gson.toJson("questionCategoryId : " + questionCategoryId);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": add() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
  
  /**
   * This method updates the QuestionCategory object.
   * 
   * @param   questionCategoryJson the JSON representation of the QuestionCategory object.
   * @return      SUCCESS:200 if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String update(String questionCategoryJson) throws ServiceException {
	  String result = null;
	  try {
		  QuestionCategory questionCategory = gson.fromJson(questionCategoryJson, QuestionCategory.class);
		  questionCategoryDAO.update(questionCategory);
	      result = gson.toJson("SUCCESS : 200");
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": update() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
  
  /**
   * This method deletes the QuestionCategory object associated with the specified 
   * question category identifier.
   * 
   * @param   id  question category id to delete.
   * @return      SUCCESS:200 if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String delete(int id) throws ServiceException {
	  String result = null;
	  try {
		  questionCategoryDAO.delete(id);
	      result = gson.toJson("SUCCESS : 200");
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": delete() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
}