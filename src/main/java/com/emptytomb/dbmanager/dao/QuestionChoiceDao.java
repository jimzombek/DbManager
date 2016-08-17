package com.emptytomb.dbmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emptytomb.dbmanager.dao.DaoException;
import com.emptytomb.dbmanager.domain.QuestionChoice;
import com.emptytomb.dbmanager.utility.ConnectionFactory;

/**
 * The QuestionChoiceDao class is a concrete JDBC implementation of the BaseDao interface.
 * This class implements the JDBC persistence storage layer for the QuestionChoice domain model.
 * 
 * <p><b>Note:</b> This class is implemented as a Singleton. Only one instance of this class
 * can exist at a time.</p>
 * 
 * @author  Jim Zombek
 * @version 1.0
 * @since   2016-08-01
 */
public class QuestionChoiceDao implements BaseDao<QuestionChoice> {
  private Logger logger = LoggerFactory.getLogger(QuestionChoiceDao.class);
  private static QuestionChoiceDao instance = null;
  private Connection connection = null;
   
  private static final String QUESTION_CHOICE_TABLE = "question_choice";
  private static final String QUESTION_CHOICE_ID = "id";
  private static final String QUESTION_CHOICE_TEXT = "text";
  private static final String QUESTION_CHOICE_CORRECT_ANSWER = "correctAnswer";
    
  private QuestionChoiceDao() {
	connection = ConnectionFactory.getInstance().getConnection();
  }
  
  /**
   * This method returns the single instance of the QuestionChoiceDao object.
   * 
   * @return  the QuestionDao object
   * 
   */
  public static QuestionChoiceDao getInstance() {
      if (instance == null) {      
          synchronized (QuestionChoiceDao.class) {
              if (instance == null) {
                  instance = new QuestionChoiceDao(); 
              }
          }
      }
      return instance;
  }
      
  /**
   * This method reads the specified question choice id record from the question_choice
   * table stored in MySQL and returns a QuestionChoice object.
   * 
   * @param   id  the unique id of the question choice to return.
   * @return      the QuestionChoice object
   * 
   * @throws      DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public QuestionChoice get(int id) throws DaoException {
      QuestionChoice questionChoice = null;
      String sql = "SELECT * FROM " + QUESTION_CHOICE_TABLE + " WHERE " + QUESTION_CHOICE_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  try (ResultSet resultSet = pstmt.executeQuery();) {
   	          while (resultSet.next()) {
                  questionChoice = getQuestionChoiceFromResultSet(resultSet);
   	          }
          }
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": getQuestionChoice() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
      return questionChoice;
  }

  /**
   * This method reads all the question choice records from the question table stored in MySQL and 
   * returns a list of QuestionChoice objects.
   * 
   * @return      List of QuestionChoice objects
   * 
   * @throws      DaoException if a SQL Exception was encountered during processing 
   */
  @Override
  public List<QuestionChoice> list() throws DaoException {
      ArrayList<QuestionChoice> questionChoices = new ArrayList<QuestionChoice>();
      String sql = "SELECT * FROM " + QUESTION_CHOICE_TABLE + ";";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);
    	   ResultSet resultSet = pstmt.executeQuery();) {
     	   while (resultSet.next()) {
              QuestionChoice questionChoice = getQuestionChoiceFromResultSet(resultSet);
              questionChoices.add(questionChoice);
           }
       } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": getQuestionChoices() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
 	       e.printStackTrace();
           throw new DaoException(e, errorMessage);
       }
      return questionChoices;
   }
  
  /**
   * This method adds the specified QuestionChoice record to the question_choice table stored in MySQL.
   * 
   * <p><b>Note:</b>Referential integrity foreign key constraints will be checked
   * prior to adding the specified organization record to the organization table
   * stored in MySQL. The organization record associated with this personality
   * must exist in the organization table stored in MySQL.</p>
   * 
   * @param    questionChoice the QuestionChoice object
   * @return                the unique id of the QuestionChoice added
   * 
   * @throws   DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public int add(QuestionChoice questionChoice) throws DaoException {
      int autoIncKey = -1;
	  String sql = "INSERT INTO " + QUESTION_CHOICE_TABLE + "(" +
	               QUESTION_CHOICE_TEXT + "," + 
                   QUESTION_CHOICE_CORRECT_ANSWER + ") " +  "VALUES" +  
                   "(?,?)";
	  	               
       try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
          pstmt.setString(1,questionChoice.getText());
          pstmt.setBoolean(2,questionChoice.isCorrectAnswer());
          pstmt.executeUpdate();
 
          // Get the auto-incremented key
          ResultSet rs = pstmt.getGeneratedKeys();
          if (rs.next()) {
              autoIncKey = rs.getInt(1);
          } else {
          	  String errorMessage = this.getClass().getName() + ": addQuestionChoice() - REASON-> " +
        	      "error obtaining auto incremented key";
        	  logger.error(errorMessage);
        	  throw new DaoException(new Exception(), errorMessage);
          }
       } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": addQuestionChoice() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DaoException(e, errorMessage);
       }
       return autoIncKey;
  }
  
  /**
   * This method updates the specified QuestionChoice record in the question_choice table stored in MySQL.
   * 
   * @param    questionChoice  the QuestionChoice object
   * 
   * @throws   DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public void update(QuestionChoice questionChoice) throws DaoException {
      String sql = "UPDATE " + QUESTION_CHOICE_TABLE + " SET " +
                   QUESTION_CHOICE_TEXT + "=?, " + 
                   QUESTION_CHOICE_CORRECT_ANSWER + "=? " +  "WHERE " +  
                   QUESTION_CHOICE_ID + "=?;"; 
         
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
          pstmt.setString(1,questionChoice.getText()); 
          pstmt.setBoolean(2,questionChoice.isCorrectAnswer()); 
          pstmt.setInt(3,questionChoice.getQuestionChoiceId());
          pstmt.executeUpdate();
      } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": updateQuestion() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
    }
  
  /**
   * This method deletes the specified question choice id from the question_choice table stored in MySQL. 
   * 
   * <p><b>Note:</b>Referential integrity foreign key constraints will be checked
   * prior to deleting the specified personality record from the personality table
   * stored in MySQL. All commentary records in the commentary table stored in MySQL
   * associated with the specified personality id must have been previously deleted
   * prior to calling this method.</p>
   * 
   * @param   id  the unique id of the question choice to delete.
   *  
   * @throws  DaoException if a SQL Exception was encountered during processing
   */
  @Override
   public void delete(int id) throws DaoException {
      String sql = "DELETE FROM " + QUESTION_CHOICE_TABLE + " WHERE " + QUESTION_CHOICE_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  pstmt.execute();
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": deleteQuestionChoice() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
   }
  
   private QuestionChoice getQuestionChoiceFromResultSet(ResultSet resultSet) throws SQLException {
       QuestionChoice questionChoice = new QuestionChoice();
       
       questionChoice.setQuestionChoiceId(resultSet.getInt(QUESTION_CHOICE_ID));
       questionChoice.setText(resultSet.getString(QUESTION_CHOICE_TEXT));
       questionChoice.setCorrectAnswer(resultSet.getBoolean(QUESTION_CHOICE_CORRECT_ANSWER));
                 
       return questionChoice;
   }
}