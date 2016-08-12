package com.emptytomb.dbmanager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emptytomb.dbmanager.dao.DaoException;
import com.emptytomb.dbmanager.dao.QuestionDao;
import com.emptytomb.dbmanager.domain.Question;
import com.google.gson.Gson;

/**
* The QuestionService class implements the CRUD service layer for the Question resource.
* 
* <p><b>Note:</b> This class is implemented as a Singleton. Only one instance of this class
* can exist at a time.</p>
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-08-01
*/
public class QuestionService {
  private Logger logger = LoggerFactory.getLogger(QuestionService.class);
  private static QuestionService instance = null;
  private static QuestionDao questionDAO = null;
  private static Gson gson = new Gson();
   
  private QuestionService() {
      questionDAO = QuestionDao.getInstance();
  }

  /**
   * This method returns the single instance of the QuestionService object.
   * 
   * @return  the QuestionService object
   * 
  */
  public static QuestionService getInstance() {
      if (instance == null) {      
          synchronized (QuestionService.class) {
              if (instance == null) {
                  instance = new QuestionService();
              } 
          }
      }
      return instance;
  }
  
  /**
   * This method returns the JSON representation of the Question object associated with the
   * specified question identifier.
   * 
   * @param   id  the unique id of the question to return.
   * @return      the JSON representation of the question object
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String getQuestion(int id) throws ServiceException {
	  String result = null;
	  try {
	      Question question = questionDAO.get(id);
	      result = gson.toJson(question);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": getQuestion() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, e.getReason());
	  }
      return result;
  }
  
  /**
   * This method returns the JSON representation of all the Question objects.
   * 
   * @return      the JSON representation of all the Question objects
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String getQuestions() throws ServiceException {
	  String result = null;
	  try {
		  List<Question> questions = questionDAO.list();
	      result = gson.toJson(questions);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": getQuestions() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, e.getReason());
	  }
      return result;
  }
  
  /**
   * This method adds the Question object.
   * 
   * @param   questionJson the JSON representation of the Question object.
   * @return      questionId of Question added if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String addQuestion(String questionJson) throws ServiceException {
	  String result = null;
	  try {
		  Question question = gson.fromJson(questionJson, Question.class);
		  int questionId = questionDAO.add(question);
	      result = gson.toJson("questionId : " + questionId);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": addQuestion() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
  
  /**
   * This method updates the Question object.
   * 
   * @param   questionJson the JSON representation of the Question object.
   * @return      SUCCESS:200 if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String updateQuestion(String questionJson) throws ServiceException {
	  String result = null;
	  try {
		  Question question = gson.fromJson(questionJson, Question.class);
		  questionDAO.update(question);
	      result = gson.toJson("SUCCESS : 200");
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": updateQuestion() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
  
  /**
   * This method deletes the Question object associated with the specified 
   * question identifier.
   * 
   * @param   id  question id to delete.
   * @return      SUCCESS:200 if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String deleteQuestion(int id) throws ServiceException {
	  String result = null;
	  try {
		  questionDAO.delete(id);
	      result = gson.toJson("SUCCESS : 200");
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": deleteQuestion() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
}