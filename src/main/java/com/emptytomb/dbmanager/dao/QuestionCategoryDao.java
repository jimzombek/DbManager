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
import com.emptytomb.dbmanager.domain.QuestionCategory;
import com.emptytomb.dbmanager.utility.ConnectionFactory;

/**
 * The QuestionCategoryDao class is a concrete JDBC implementation of the BaseDao interface.
 * This class implements the JDBC persistence storage layer for the QuestionCategory domain
 * model.
 * 
 * <p><b>Note:</b> This class is implemented as a Singleton. Only one instance of this class
 * can exist at a time.</p>
 * 
 * @author  Jim Zombek
 * @version 1.0
 * @since   2016-08-01
 */
public class QuestionCategoryDao implements BaseDao<QuestionCategory> {
  private Logger logger = LoggerFactory.getLogger(QuestionCategoryDao.class);
  private static QuestionCategoryDao instance = null;
  private Connection connection = null;

  private static final String QUESTION_CATEGORY_TABLE = "question_category";
  private static final String QUESTION_CATEGORY_ID = "id";
  private static final String QUESTION_CATEGORY_NAME = "name";
  private static final String QUESTION_CATEGORY_DATE_CREATED = "dateCreated";
  private static final String QUESTION_CATEGORY_DATE_UPDATED = "dateUpdated";   
         
  private QuestionCategoryDao() {
	connection = ConnectionFactory.getInstance().getConnection();
  }
  
  /**
   * This method returns the single instance of the CategoryDao object.
   * 
   * @return  the QuestionCategoryDao object
   * 
   */
  public static QuestionCategoryDao getInstance() {
      if (instance == null) {      
          synchronized (QuestionCategoryDao.class) {
              if (instance == null) {
                  instance = new QuestionCategoryDao(); 
              }
          }
      }
      return instance;
  }
      
  /**
   * This method reads the specified id record from the question_category
   * table stored in MySQL and returns a QuestionCategory object.
   * 
   * @param   id  the unique id of the question category record to return.
   * @return      the QuestionCategory object
   * 
   * @throws      DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public QuestionCategory get(int id) throws DaoException {
      QuestionCategory questionCategory = null;
      String sql = "SELECT * FROM " + QUESTION_CATEGORY_TABLE + " WHERE " + QUESTION_CATEGORY_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  try (ResultSet resultSet = pstmt.executeQuery();) {
   	          while (resultSet.next()) {
                  questionCategory = createQuestionCategoryFromResultSet(resultSet);
   	          }
          }
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": get() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
      return questionCategory;
  }

  /**
   * This method reads all the question category records from the question_category table
   * stored in MySQL and returns a list of QuestionCategory objects.
   * 
   * <p><b>Note:</b> This method is the equivalent of a full table scan. However, since
   * the contents of this table is small, this method will be performant.</p>
   * 
   * @return      List of QuestionCategory objects
   * 
   * @throws      DaoException if a SQL Exception was encountered during processing 
   */
  @Override
  public List<QuestionCategory> list() throws DaoException {
      ArrayList<QuestionCategory> questionCategories = new ArrayList<QuestionCategory>();
      String sql = "SELECT * FROM " + QUESTION_CATEGORY_TABLE + ";";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);
    	   ResultSet resultSet = pstmt.executeQuery();) {
     	   while (resultSet.next()) {
              QuestionCategory questionCategory = createQuestionCategoryFromResultSet(resultSet);
              questionCategories.add(questionCategory);
           }
       } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": list() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
 	       e.printStackTrace();
           throw new DaoException(e, errorMessage);
       }
      return questionCategories;
   }
  
  /**
   * This method adds the specified QuestionCategory record to the question_category
   * table stored in MySQL.
   * 
   * @param    questionCategory the QuestionCategory object
   * @return                the unique id of the QuestionCategory record added
   * 
   * @throws   DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public int add(QuestionCategory questionCategory) throws DaoException {
      int autoIncKey = -1;
 	  String sql = "INSERT INTO " + QUESTION_CATEGORY_TABLE + "(" +
	                QUESTION_CATEGORY_NAME + ") " +  "VALUES" +  
	               "(?)";
	  	               
       try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
          pstmt.setString(1,questionCategory.getName());
          pstmt.executeUpdate();
          
          // Get the auto-incremented key
          ResultSet rs = pstmt.getGeneratedKeys();
          if (rs.next()) {
              autoIncKey = rs.getInt(1);
          } else {
          	  String errorMessage = this.getClass().getName() + ": add() - REASON-> " +
        	      "error obtaining auto incremented key";
        	  logger.error(errorMessage);
        	  throw new DaoException(new Exception(), errorMessage);
          }
       } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": add() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DaoException(e, errorMessage);
       }
       return autoIncKey;
  }
  
  /**
   * This method updates the specified QuestionCategory record in the question_category
   * table stored in MySQL.
   * 
   * @param    questionCategory  the QuestionCategory object
   * 
   * @throws   DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public void update(QuestionCategory questionCategory) throws DaoException {
      String sql = "UPDATE " + QUESTION_CATEGORY_TABLE + " SET " +
                   QUESTION_CATEGORY_NAME + "=? " +  "WHERE " + 
                   QUESTION_CATEGORY_ID + "=?;";
       
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
          pstmt.setString(1,questionCategory.getName());
          pstmt.setInt(2,questionCategory.getId());
          pstmt.executeUpdate();
      } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": update() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
    }
  
  /**
   * This method deletes the specified question category record from the question_category
   * table stored in MySQL. 
   * 
   * <p><b>Note:</b>Referential integrity foreign key constraints will be checked
   * prior to deleting the specified personality record from the personality table
   * stored in MySQL. All commentary records in the commentary table stored in MySQL
   * associated with the specified personality id must have been previously deleted
   * prior to calling this method.</p>
   * 
   * @param   id  the unique id of the category to delete.
   *  
   * @throws  DaoException if a SQL Exception was encountered during processing
   */
  @Override
   public void delete(int id) throws DaoException {
      String sql = "DELETE FROM " + QUESTION_CATEGORY_TABLE + " WHERE " + QUESTION_CATEGORY_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  pstmt.execute();
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": delete() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
   }
  
   private QuestionCategory createQuestionCategoryFromResultSet(ResultSet resultSet) throws SQLException {
       QuestionCategory questionCategory = new QuestionCategory();
       
       questionCategory.setId(resultSet.getInt(QUESTION_CATEGORY_ID));
       questionCategory.setName(resultSet.getString(QUESTION_CATEGORY_NAME));
       questionCategory.setDateCreated(resultSet.getString(QUESTION_CATEGORY_DATE_CREATED));
       questionCategory.setDateUpdated(resultSet.getString(QUESTION_CATEGORY_DATE_UPDATED));
                  
       return questionCategory;
   }
}