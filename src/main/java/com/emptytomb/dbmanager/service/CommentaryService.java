package com.emptytomb.dbmanager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emptytomb.dbmanager.dao.DaoException;
import com.emptytomb.dbmanager.dao.CommentaryDao;
import com.emptytomb.dbmanager.domain.Commentary;
import com.google.gson.Gson;

/**
* The CommentaryService class implements the CRUD service layer for the Commentary resource.
* 
* <p><b>Note:</b> This class is implemented as a Singleton. Only one instance of this class
* can exist at a time.</p>
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-07-01
*/
public class CommentaryService {
  private Logger logger = LoggerFactory.getLogger(Commentary.class);
  private static CommentaryService instance = null;
  private static CommentaryDao commentaryDAO = null;
  private static Gson gson = new Gson();
   
  private CommentaryService() {
      commentaryDAO = CommentaryDao.getInstance();
  }

  /**
   * This method returns the single instance of the CommentaryService object.
   * 
   * @return  the CommentaryService object
   * 
  */
  public static CommentaryService getInstance() {
      if (instance == null) {      
          synchronized (CommentaryService.class) {
              if (instance == null) {
                  instance = new CommentaryService();
              } 
          }
      }
      return instance;
  }
  
  /**
   * This method returns the JSON representation of the Commentary object associated with the
   * specified commentary identifier.
   * 
   * @param   id  the unique id of the organization to return.
   * @return      the JSON representation of the Organization object
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String getCommentary(int id) throws ServiceException {
	  String result = null;
	  try {
	      Commentary commentary = commentaryDAO.get(id);
	      result = gson.toJson(commentary);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": getCommentary() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, e.getReason());
	  }
      return result;
  }
  
  /**
   * This method returns the JSON representation of all the Commentary objects.
   * 
   * @return      the JSON representation of all the Commentary objects
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String getCommentaries() throws ServiceException {
	  String result = null;
	  try {
		  List<Commentary> commentaries = commentaryDAO.list();
	      result = gson.toJson(commentaries);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": getCommentaries() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, e.getReason());
	  }
      return result;
  }
  
  /**
   * This method adds the Commentary object.
   * 
   * @param   commentaryJson the JSON representation of the Commentary object.
   * @return      commentaryId of Commentary added if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String addCommentary(String commentaryJson) throws ServiceException {
	  String result = null;
	  try {
		  Commentary commentary = gson.fromJson(commentaryJson, Commentary.class);
		  int commentaryId = commentaryDAO.add(commentary);
	      result = gson.toJson("commentaryId : " + commentaryId);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": addCommentary() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
  
  /**
   * This method updates the Commentary object.
   * 
   * @param   commentaryJson the JSON representation of the Commentary object.
   * @return      SUCCESS:200 if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String updateCommentary(String commentaryJson) throws ServiceException {
	  String result = null;
	  try {
		  Commentary commentary = gson.fromJson(commentaryJson, Commentary.class);
		  commentaryDAO.update(commentary);
	      result = gson.toJson("SUCCESS : 200");
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": updateCommentary() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
  
  /**
   * This method deletes the Commentary object associated with the specified 
   * commentary identifier.
   * 
   * @param   id  commentary id to delete.
   * @return      SUCCESS:200 if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String deleteCommentary(int id) throws ServiceException {
	  String result = null;
	  try {
		  commentaryDAO.delete(id);
	      result = gson.toJson("SUCCESS : 200");
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": deleteCommentary() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
}