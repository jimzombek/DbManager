package com.emptytomb.dbmanager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emptytomb.dbmanager.dao.DaoException;
import com.emptytomb.dbmanager.dao.QuestionChoiceDao;
import com.emptytomb.dbmanager.domain.QuestionChoice;
import com.google.gson.Gson;

/**
* The QuestionChoiceService class implements the CRUD service layer for the QuestionChoice resource.
* 
* <p><b>Note:</b> This class is implemented as a Singleton. Only one instance of this class
* can exist at a time.</p>
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-08-01
*/
public class QuestionChoiceService {
  private Logger logger = LoggerFactory.getLogger(QuestionChoiceService.class);
  private static QuestionChoiceService instance = null;
  private static QuestionChoiceDao questionChoiceDAO = null;
  private static Gson gson = new Gson();
   
  private QuestionChoiceService() {
      questionChoiceDAO = QuestionChoiceDao.getInstance();
  }

  /**
   * This method returns the single instance of the QuestionChoiceService object.
   * 
   * @return  the QuestionChoiceService object
   * 
  */
  public static QuestionChoiceService getInstance() {
      if (instance == null) {      
          synchronized (QuestionChoiceService.class) {
              if (instance == null) {
                  instance = new QuestionChoiceService();
              } 
          }
      }
      return instance;
  }
  
  /**
   * This method returns the JSON representation of the QuestionChoice object associated with
   * the specified question choice identifier.
   * 
   * @param   id  the unique id of the question choice to return.
   * @return      the JSON representation of the question choice object
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String getQuestionChoice(int id) throws ServiceException {
	  String result = null;
	  try {
	      QuestionChoice questionChoice = questionChoiceDAO.get(id);
	      result = gson.toJson(questionChoice);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": getQuestionChoice() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, e.getReason());
	  }
      return result;
  }
  
  /**
   * This method returns the JSON representation of all the QuestionChoice objects.
   * 
   * @return      the JSON representation of all the QuestionChoice objects
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String getQuestionChoices() throws ServiceException {
	  String result = null;
	  try {
		  List<QuestionChoice> questionChoices = questionChoiceDAO.list();
	      result = gson.toJson(questionChoices);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": getQuestionChoices() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, e.getReason());
	  }
      return result;
  }
  
  /**
   * This method adds the QuestionChoice object.
   * 
   * @param   questionChoiceJson the JSON representation of the QuestionChoice object.
   * @return      questionChoiceId of QuestionChoice added if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String addQuestionChoice(String questionChoiceJson) throws ServiceException {
	  String result = null;
	  try {
		  QuestionChoice questionChoice = gson.fromJson(questionChoiceJson, QuestionChoice.class);
		  int questionChoiceId = questionChoiceDAO.add(questionChoice);
	      result = gson.toJson("questionChoiceId : " + questionChoiceId);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": addQuestionChoice() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
  
  /**
   * This method updates the QuestionChoice object.
   * 
   * @param   questionChoiceJson the JSON representation of the QuestionChoice object.
   * @return      SUCCESS:200 if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String updateQuestionChoice(String questionChoiceJson) throws ServiceException {
	  String result = null;
	  try {
		  QuestionChoice questionChoice = gson.fromJson(questionChoiceJson, QuestionChoice.class);
		  questionChoiceDAO.update(questionChoice);
	      result = gson.toJson("SUCCESS : 200");
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": updateQuestionChoice() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
  
  /**
   * This method deletes the QuestionChoice object associated with the specified 
   * questionChoice identifier.
   * 
   * @param   id  questionChoice id to delete.
   * @return      SUCCESS:200 if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String deleteQuestionChoice(int id) throws ServiceException {
	  String result = null;
	  try {
		  questionChoiceDAO.delete(id);
	      result = gson.toJson("SUCCESS : 200");
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": deleteQuestionChoice() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
}