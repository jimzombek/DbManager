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
import com.emptytomb.dbmanager.domain.Commentary;
import com.emptytomb.dbmanager.utility.ConnectionFactory;


/**
 * The CommentaryDao class is a concrete JDBC implementation of the BaseDao interface.
 * This class implements the JDBC persistence storage layer for the Commentary domain model.
 * 
 * <p><b>Note:</b> This class is implemented as a Singleton. Only one instance of this class
 * can exist at a time.</p>
 * 
 * @author  Jim Zombek
 * @version 1.0
 * @since   2016-07-01
 */
public class CommentaryDao implements BaseDao<Commentary> {
  private Logger logger = LoggerFactory.getLogger(CommentaryDao.class);
  private static CommentaryDao instance = null;
  private Connection connection = null;
  
  private static final String COMMENTARY_TABLE = "commentary";
  private static final String COMMENTARY_ID = "id";
  private static final String COMMENTARY_PERSONALITY_ID = "personalityId";
  private static final String COMMENTARY_PASSAGE_ID = "passageId";
  private static final String COMMENTARY_TEXT = "text";
     
   private CommentaryDao() {
	connection = ConnectionFactory.getInstance().getConnection();
  }
  
  /**
   * This method returns the single instance of the CommentaryDaoJdbc object.
   * 
   * @return  the CommentaryDaoJdbc object
   * 
   */
  public static CommentaryDao getInstance() {
      if (instance == null) {      
          synchronized (CommentaryDao.class) {
              if (instance == null) {
                  instance = new CommentaryDao(); 
              }
          }
      }
      return instance;
  }
      
  /**
   * This method reads the specified commentary id record from the commentary
   * table stored in MySQL and returns a Commentary object.
   * 
   * @param   id  the unique id of the commentary to return.
   * @return      the Commentary object
   * 
   * @throws      DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public Commentary get(int id) throws DaoException {
      Commentary commentary = null;
      String sql = "SELECT * FROM " + COMMENTARY_TABLE + " WHERE " + COMMENTARY_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  try (ResultSet resultSet = pstmt.executeQuery();) {
   	          while (resultSet.next()) {
                  commentary = getCommentaryFromResultSet(resultSet);
   	          }
          }
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": getCommentary() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
      return commentary;
  }

  /**
   * This method reads all the commentary records from the commentary table
   * stored in MySQL and returns a list of Commentary objects.
   * 
   * <p><b>Note:</b> This method is the equivalent of a full table scan. However, since
   * the contents of this table is small, this method will be performant.</p>
   * 
   * @return      List of Commentary objects
   * 
   * @throws      DaoException if a SQL Exception was encountered during processing 
   */
  @Override
  public List<Commentary> list() throws DaoException {
      ArrayList<Commentary> commentaries = new ArrayList<Commentary>();
      String sql = "SELECT * FROM " + COMMENTARY_TABLE + ";";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);
    	   ResultSet resultSet = pstmt.executeQuery();) {
     	   while (resultSet.next()) {
              Commentary commentary = getCommentaryFromResultSet(resultSet);
              commentaries.add(commentary);
           }
       } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": getCommentaries() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
 	       e.printStackTrace();
           throw new DaoException(e, errorMessage);
       }
      return commentaries;
   }
  
  /**
   * This method adds the specified Commentary record to the commentary
   * table stored in MySQL.
   * 
   * <p><b>Note:</b>Referential integrity foreign key constraints will be checked
   * prior to adding the commentary record to the commentary table stored in MySQL.
   * The personality id must exit in the personality table and the specified passage id
   * must exist in the passage table, otherwise this call will fail with a Referential
   * Integrity check.</p>
   * 
   * @param    commentary the Commentary object
   * @return                the unique id of the Commentary added
   * 
   * @throws   DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public int add(Commentary commentary) throws DaoException {
      int autoIncKey = -1;
	  String sql = "INSERT INTO " + COMMENTARY_TABLE + "(" +
	               COMMENTARY_PERSONALITY_ID + "," + 
	               COMMENTARY_PASSAGE_ID + "," + 
	               COMMENTARY_TEXT + ") " +  "VALUES" +  
	               "(?,?,?)";
                   	   	               
       try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
          pstmt.setInt(1,commentary.getPersonalityId());   
          pstmt.setInt(2,commentary.getPassageId());  
          pstmt.setString(3,commentary.getText());    
          pstmt.executeUpdate();
             
          // Get the auto-incremented key
          ResultSet rs = pstmt.getGeneratedKeys();
          if (rs.next()) {
              autoIncKey = rs.getInt(1);
          } else {
          	  String errorMessage = this.getClass().getName() + ": addCommentary() - REASON-> " +
        	      "error obtaining auto incremented key";
        	  logger.error(errorMessage);
        	  throw new DaoException(new Exception(), errorMessage);
          }
       } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": addCommentary() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DaoException(e, errorMessage);
       }
       return autoIncKey;
  }
  
  /**
   * This method updates the specified Commentary record in the commentary table
   * stored in MySQL.
   * 
   * @param    commentary  the Commentary object
   * 
   * @throws   DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public void update(Commentary commentary) throws DaoException {
      String sql = "UPDATE " + COMMENTARY_TABLE + " SET " +
                   COMMENTARY_PERSONALITY_ID + "=?, " + 
                   COMMENTARY_PASSAGE_ID + "=?, " +    
                   COMMENTARY_TEXT + "=? " +  "WHERE " + 
                   COMMENTARY_ID + "=?;";
         	               
       try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
          pstmt.setInt(1,commentary.getPersonalityId()); 
          pstmt.setInt(2,commentary.getPassageId());   
          pstmt.setString(3,commentary.getText());
          pstmt.setInt(4,commentary.getCommentaryId());
          pstmt.executeUpdate();
      } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": updateCommentary() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
    }
  
  /**
   * This method deletes the specified commentary id from the commentary table
   * stored in MySQL. 
   * 
   * <p><b>Note:</b>Referential integrity foreign key constraints will be checked
   * prior to deleting the specified commentary record from the commentary table
   * stored in MySQL. All commentary records in the commentary table stored in MySQL
   * associated with the specified personality id must have been previously deleted
   * prior to calling this method.</p>
   * 
   * @param   id  the unique id of the commentary to delete.
   *  
   * @throws  DaoException if a SQL Exception was encountered during processing
   */
  @Override
   public void delete(int id) throws DaoException {
      String sql = "DELETE fROM " + COMMENTARY_TABLE + " WHERE " + COMMENTARY_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  pstmt.execute();
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": deleteCommentary() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
   }
  
   private Commentary getCommentaryFromResultSet(ResultSet resultSet) throws SQLException {
       Commentary commentary = new Commentary();
               
       commentary.setCommentaryId(resultSet.getInt(COMMENTARY_ID));
       commentary.setPersonalityId(resultSet.getInt(COMMENTARY_PERSONALITY_ID));
       commentary.setPassageId(resultSet.getInt(COMMENTARY_PASSAGE_ID));
       commentary.setText(resultSet.getString(COMMENTARY_TEXT));
             
       return commentary;
   }
}