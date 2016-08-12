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
import com.emptytomb.dbmanager.domain.Question;
import com.emptytomb.dbmanager.utility.ConnectionFactory;

/**
 * The QuestionDao class is a concrete JDBC implementation of the BaseDao interface.
 * This class implements the JDBC persistence storage layer for the Question domain model.
 * 
 * <p><b>Note:</b> This class is implemented as a Singleton. Only one instance of this class
 * can exist at a time.</p>
 * 
 * @author  Jim Zombek
 * @version 1.0
 * @since   2016-08-01
 */
public class QuestionDao implements BaseDao<Question> {
  private Logger logger = LoggerFactory.getLogger(QuestionDao.class);
  private static QuestionDao instance = null;
  private Connection connection = null;
   
  private static final String QUESTION_TABLE = "question";
  private static final String QUESTION_ID = "id";
  private static final String QUESTION_CATEGORY_ID = "categoryId";
  private static final String QUESTION_ANSWER_ID = "answerId";
  private static final String QUESTION_TEXT = "text";
  private static final String QUESTION_TYPE = "type";
  private static final String QUESTION_TESTAMENT = "testament";
  private static final String QUESTION_DIFFICULTY = "difficulty";
  private static final String QUESTION_SINCE_VERSION = "sinceVersion";
    
  private QuestionDao() {
	connection = ConnectionFactory.getInstance().getConnection();
  }
  
  /**
   * This method returns the single instance of the QuestionDao object.
   * 
   * @return  the QuestionDao object
   * 
   */
  public static QuestionDao getInstance() {
      if (instance == null) {      
          synchronized (PassageDao.class) {
              if (instance == null) {
                  instance = new QuestionDao(); 
              }
          }
      }
      return instance;
  }
      
  /**
   * This method reads the specified question id record from the question
   * table stored in MySQL and returns a Question object.
   * 
   * @param   id  the unique id of the question to return.
   * @return      the Question object
   * 
   * @throws      DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public Question get(int id) throws DaoException {
      Question question = null;
      String sql = "SELECT * FROM " + QUESTION_TABLE + " WHERE " + QUESTION_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  try (ResultSet resultSet = pstmt.executeQuery();) {
   	          while (resultSet.next()) {
                  question = getQuestionFromResultSet(resultSet);
   	          }
          }
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": getQuestion() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
      return question;
  }

  /**
   * This method reads all the question records from the question table stored in MySQL and 
   * returns a list of Question objects.
   * 
   * @return      List of Question objects
   * 
   * @throws      DaoException if a SQL Exception was encountered during processing 
   */
  @Override
  public List<Question> list() throws DaoException {
      ArrayList<Question> questions = new ArrayList<Question>();
      String sql = "SELECT * FROM " + QUESTION_TABLE + ";";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);
    	   ResultSet resultSet = pstmt.executeQuery();) {
     	   while (resultSet.next()) {
              Question question = getQuestionFromResultSet(resultSet);
              questions.add(question);
           }
       } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": getQuestions() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
 	       e.printStackTrace();
           throw new DaoException(e, errorMessage);
       }
      return questions;
   }
  
  /**
   * This method adds the specified Question record to the question table stored in MySQL.
   * 
   * <p><b>Note:</b>Referential integrity foreign key constraints will be checked
   * prior to adding the specified organization record to the organization table
   * stored in MySQL. The organization record associated with this personality
   * must exist in the organization table stored in MySQL.</p>
   * 
   * @param    question the Question object
   * @return                the unique id of the Question added
   * 
   * @throws   DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public int add(Question question) throws DaoException {
      int autoIncKey = -1;
	  String sql = "INSERT INTO " + QUESTION_TABLE + "(" +
	               QUESTION_CATEGORY_ID + "," +   
                   QUESTION_ANSWER_ID + "," + 
                   QUESTION_TEXT + "," + 
                   QUESTION_TYPE + "," + 
                   QUESTION_TESTAMENT + "," +   
                   QUESTION_DIFFICULTY + "," + 
                   QUESTION_SINCE_VERSION + ") " +  "VALUES" +  
                   "(?,?,?,?,?,?,?)";
	  	               
       try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
          pstmt.setInt(1,question.getCategoryId());
          pstmt.setInt(2,question.getAnswerId());
          pstmt.setString(3,question.getText());
          pstmt.setString(4,question.getType());
          pstmt.setString(5,question.getTestament()); 
          pstmt.setString(6,question.getDifficulty());
          pstmt.setFloat(7,question.getSinceVersion());
          pstmt.executeUpdate();
 
          // Get the auto-incremented key
          ResultSet rs = pstmt.getGeneratedKeys();
          if (rs.next()) {
              autoIncKey = rs.getInt(1);
          } else {
          	  String errorMessage = this.getClass().getName() + ": addQuestion() - REASON-> " +
        	      "error obtaining auto incremented key";
        	  logger.error(errorMessage);
        	  throw new DaoException(new Exception(), errorMessage);
          }
       } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": addQuestion() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DaoException(e, errorMessage);
       }
       return autoIncKey;
  }
  
  /**
   * This method updates the specified Question record in the question table stored in MySQL.
   * 
   * @param    question  the Question object
   * 
   * @throws   DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public void update(Question question) throws DaoException {
      String sql = "UPDATE " + QUESTION_TABLE + " SET " +
                   QUESTION_CATEGORY_ID + "=?, " + 
                   QUESTION_ANSWER_ID + "=?, " + 
                   QUESTION_TEXT + "=?, " + 
                   QUESTION_TYPE + "=?, " + 
                   QUESTION_TESTAMENT + "=?, " + 
                   QUESTION_DIFFICULTY + "=?, " + 
                   QUESTION_SINCE_VERSION + "=? " +  "WHERE " +  
                   QUESTION_ID + "=?;"; 
         
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
          pstmt.setInt(1,question.getCategoryId()); 
          pstmt.setInt(2,question.getAnswerId()); 
          pstmt.setString(3,question.getText()); 
          pstmt.setString(4,question.getType()); 
          pstmt.setString(5,question.getTestament()); 
          pstmt.setString(6,question.getDifficulty()); 
          pstmt.setFloat(7,question.getSinceVersion()); 
          pstmt.setInt(8,question.getQuestionId());
          pstmt.executeUpdate();
      } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": updateQuestion() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
    }
  
  /**
   * This method deletes the specified question id from the question table stored in MySQL. 
   * 
   * <p><b>Note:</b>Referential integrity foreign key constraints will be checked
   * prior to deleting the specified personality record from the personality table
   * stored in MySQL. All commentary records in the commentary table stored in MySQL
   * associated with the specified personality id must have been previously deleted
   * prior to calling this method.</p>
   * 
   * @param   id  the unique id of the question to delete.
   *  
   * @throws  DaoException if a SQL Exception was encountered during processing
   */
  @Override
   public void delete(int id) throws DaoException {
      String sql = "DELETE fROM " + QUESTION_TABLE + " WHERE " + QUESTION_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  pstmt.execute();
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": deleteQuestion() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
   }
  
   private Question getQuestionFromResultSet(ResultSet resultSet) throws SQLException {
       Question question = new Question();
       
       question.setQuestionId(resultSet.getInt(QUESTION_ID));
       question.setCategoryId(resultSet.getInt(QUESTION_CATEGORY_ID));
       question.setAnswerId(resultSet.getInt(QUESTION_ANSWER_ID));
       question.setText(resultSet.getString(QUESTION_TEXT));
       question.setType(resultSet.getString(QUESTION_TYPE));
       question.setTestament(resultSet.getString(QUESTION_TESTAMENT));
       question.setDifficulty(resultSet.getString(QUESTION_DIFFICULTY));
       question.setSinceVersion(resultSet.getFloat(QUESTION_SINCE_VERSION));
                 
       return question;
   }
}