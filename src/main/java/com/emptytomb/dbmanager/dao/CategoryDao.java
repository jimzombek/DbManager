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
import com.emptytomb.dbmanager.domain.Category;
import com.emptytomb.dbmanager.utility.ConnectionFactory;

/**
 * The CategoryDao class is a concrete JDBC implementation of the BaseDao interface.
 * This class implements the JDBC persistence storage layer for the Category domain model.
 * 
 * <p><b>Note:</b> This class is implemented as a Singleton. Only one instance of this class
 * can exist at a time.</p>
 * 
 * @author  Jim Zombek
 * @version 1.0
 * @since   2016-08-01
 */
public class CategoryDao implements BaseDao<Category> {
  private Logger logger = LoggerFactory.getLogger(CategoryDao.class);
  private static CategoryDao instance = null;
  private Connection connection = null;

  private static final String CATEGORY_TABLE = "category";
  private static final String CATEGORY_ID = "id";
  private static final String CATEGORY_QUESTION_ID = "questionId";
  private static final String CATEGORY_NAME = "name";
   
         
  private CategoryDao() {
	connection = ConnectionFactory.getInstance().getConnection();
  }
  
  /**
   * This method returns the single instance of the CategoryDao object.
   * 
   * @return  the CategoryDao object
   * 
   */
  public static CategoryDao getInstance() {
      if (instance == null) {      
          synchronized (CategoryDao.class) {
              if (instance == null) {
                  instance = new CategoryDao(); 
              }
          }
      }
      return instance;
  }
      
  /**
   * This method reads the specified category id record from the category
   * table stored in MySQL and returns a Category object.
   * 
   * @param   id  the unique id of the category to return.
   * @return      the Category object
   * 
   * @throws      DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public Category get(int id) throws DaoException {
      Category category = null;
      String sql = "SELECT * FROM " + CATEGORY_TABLE + " WHERE " + CATEGORY_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  try (ResultSet resultSet = pstmt.executeQuery();) {
   	          while (resultSet.next()) {
                  category = getCategoryFromResultSet(resultSet);
   	          }
          }
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": getCategory() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
      return category;
  }

  /**
   * This method reads all the category records from the category table stored in MySQL and 
   * returns a list of Category objects.
   * 
   * <p><b>Note:</b> This method is the equivalent of a full table scan. However, since
   * the contents of this table is small, this method will be performant.</p>
   * 
   * @return      List of Category objects
   * 
   * @throws      DaoException if a SQL Exception was encountered during processing 
   */
  @Override
  public List<Category> list() throws DaoException {
      ArrayList<Category> categories = new ArrayList<Category>();
      String sql = "SELECT * FROM " + CATEGORY_TABLE + ";";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);
    	   ResultSet resultSet = pstmt.executeQuery();) {
     	   while (resultSet.next()) {
              Category category = getCategoryFromResultSet(resultSet);
              categories.add(category);
           }
       } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": getCategories() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
 	       e.printStackTrace();
           throw new DaoException(e, errorMessage);
       }
      return categories;
   }
  
  /**
   * This method adds the specified Category record to the category table stored in MySQL.
   * 
   * <p><b>Note:</b>Referential integrity foreign key constraints will be checked
   * prior to adding the specified organization record to the organization table
   * stored in MySQL. The organization record associated with this personality
   * must exist in the organization table stored in MySQL.</p>
   * 
   * @param    category the Category object
   * @return                the unique id of the Category added
   * 
   * @throws   DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public int add(Category category) throws DaoException {
      int autoIncKey = -1;
	  String sql = "INSERT INTO " + CATEGORY_TABLE + "(" +
	               CATEGORY_QUESTION_ID + "," + 
	               CATEGORY_NAME + ") " +  "VALUES" +  
	               "(?,?)";
	  	               
       try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
          pstmt.setInt(1,category.getQuestionId());
          pstmt.setString(2,category.getName());
          pstmt.executeUpdate();
          
          // Get the auto-incremented key
          ResultSet rs = pstmt.getGeneratedKeys();
          if (rs.next()) {
              autoIncKey = rs.getInt(1);
          } else {
          	  String errorMessage = this.getClass().getName() + ": addCategory() - REASON-> " +
        	      "error obtaining auto incremented key";
        	  logger.error(errorMessage);
        	  throw new DaoException(new Exception(), errorMessage);
          }
       } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": addCategory() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DaoException(e, errorMessage);
       }
       return autoIncKey;
  }
  
  /**
   * This method updates the specified Category record in the category table stored in MySQL.
   * 
   * @param    category  the Category object
   * 
   * @throws   DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public void update(Category category) throws DaoException {
      String sql = "UPDATE " + CATEGORY_TABLE + " SET " +
                   CATEGORY_QUESTION_ID + "=?, " + 
                   CATEGORY_NAME + "=? " +  "WHERE " + 
                   CATEGORY_ID + "=?;";
       
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
          pstmt.setInt(1,category.getQuestionId());
          pstmt.setString(2,category.getName());
          pstmt.setInt(3,category.getCategoryId());
          pstmt.executeUpdate();
      } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": updatePassage() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
    }
  
  /**
   * This method deletes the specified category id from the category table stored in MySQL. 
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
      String sql = "DELETE fROM " + CATEGORY_TABLE + " WHERE " + CATEGORY_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  pstmt.execute();
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": deleteCategory() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
   }
  
   private Category getCategoryFromResultSet(ResultSet resultSet) throws SQLException {
       Category category = new Category();
       
       category.setCategoryId(resultSet.getInt(CATEGORY_ID));
       category.setQuestionId(resultSet.getInt(CATEGORY_QUESTION_ID));
       category.setName(resultSet.getString(CATEGORY_NAME));
                  
       return category;
   }
}