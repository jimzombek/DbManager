package com.emptytomb.dbmanager.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emptytomb.dbmanager.dao.DaoException;
import com.emptytomb.dbmanager.dao.CommentaryDao;
import com.emptytomb.dbmanager.domain.Commentary;
import com.emptytomb.dbmanager.utility.ConnectionFactory;
import com.emptytomb.dbmanager.utility.Constants;

/**
 * The CommentaryDaoJdbc class is a concrete JDBC implementation of the CommentaryDao interface.
 * This class implements the JDBC persistence storage layer for the Commentary domain model.
 * 
 * <p><b>Note:</b> This class is implemented as a Singleton. Only one instance of this class
 * can exist at a time.</p>
 * 
 * @author  Jim Zombek
 * @version 1.0
 * @since   2016-07-01
 */
public class CommentaryDaoJdbc implements CommentaryDao {
  private Logger logger = LoggerFactory.getLogger(CommentaryDaoJdbc.class);
  private static CommentaryDaoJdbc instance = null;
  private Connection connection = null;
     
   private CommentaryDaoJdbc() {
	connection = ConnectionFactory.getInstance().getConnection();
  }
  
  /**
   * This method returns the single instance of the CommentaryDaoJdbc object.
   * 
   * @return  the CommentaryDaoJdbc object
   * 
   */
  public static CommentaryDaoJdbc getInstance() {
      if (instance == null) {      
          synchronized (CommentaryDaoJdbc.class) {
              if (instance == null) {
                  instance = new CommentaryDaoJdbc(); 
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
  public Commentary getCommentary(int id) throws DaoException {
      Commentary commentary = null;
      String sql = "SELECT * FROM " + Constants.COMMENTARY_TABLE + " WHERE " + Constants.COMMENTARY_ID + " = ?;";
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
  public List<Commentary> getCommentaries() throws DaoException {
      ArrayList<Commentary> commentaries = new ArrayList<Commentary>();
      String sql = "SELECT * FROM " + Constants.COMMENTARY_TABLE + ";";
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
   * prior to adding the specified organization record to the organization table
   * stored in MySQL. The organization record associated with this personality
   * must exist in the organization table stored in MySQL.</p>
   * 
   * @param    commentary the Commentary object
   * @return                the unique id of the Commentary added
   * 
   * @throws   DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public int addCommentary(Commentary commentary) throws DaoException {
      int autoIncKey = -1;
	  String sql = "INSERT INTO " + Constants.COMMENTARY_TABLE + "(" +
	               Constants.COMMENTARY_PERSONALITY_ID + "," + 
	               Constants.COMMENTARY_SCRIPTURE_ID + "," + 
	               Constants.COMMENTARY_TEXT + ") " +  "VALUES" +  
	               "(?,?,?)";
                   	   	               
       try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
          pstmt.setInt(1,commentary.getPersonalityId());   
          pstmt.setInt(2,commentary.getScriptureId());  
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
  public void updateCommentary(Commentary commentary) throws DaoException {
      String sql = "UPDATE " + Constants.COMMENTARY_TABLE + " SET " +
                   Constants.COMMENTARY_PERSONALITY_ID + "=?, " + 
                   Constants.COMMENTARY_SCRIPTURE_ID + "=?, " +    
                   Constants.COMMENTARY_TEXT + "=? " +  "WHERE " + 
                   Constants.COMMENTARY_ID + "=?;";
         	               
       try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
          pstmt.setInt(1,commentary.getPersonalityId()); 
          pstmt.setInt(2,commentary.getScriptureId());   
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
   * prior to deleting the specified personality record from the personality table
   * stored in MySQL. All commentary records in the commentary table stored in MySQL
   * associated with the specified personality id must have been previously deleted
   * prior to calling this method.</p>
   * 
   * @param   id  the unique id of the commentary to delete.
   *  
   * @throws  DaoException if a SQL Exception was encountered during processing
   */
  @Override
   public void deleteCommentary(int id) throws DaoException {
      String sql = "DELETE fROM " + Constants.COMMENTARY_TABLE + " WHERE " + Constants.COMMENTARY_ID + " = ?;";
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
               
       commentary.setCommentaryId(resultSet.getInt(Constants.COMMENTARY_ID));
       commentary.setPersonalityId(resultSet.getInt(Constants.COMMENTARY_PERSONALITY_ID));
       commentary.setScriptureId(resultSet.getInt(Constants.COMMENTARY_SCRIPTURE_ID));
       commentary.setText(resultSet.getString(Constants.COMMENTARY_TEXT));
             
       return commentary;
   }
}