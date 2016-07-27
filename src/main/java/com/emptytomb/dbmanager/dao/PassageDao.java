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
import com.emptytomb.dbmanager.domain.Passage;
import com.emptytomb.dbmanager.utility.ConnectionFactory;

/**
 * The PassageDao class is a concrete JDBC implementation of the BaseDao interface.
 * This class implements the JDBC persistence storage layer for the Passage domain model.
 * 
 * <p><b>Note:</b> This class is implemented as a Singleton. Only one instance of this class
 * can exist at a time.</p>
 * 
 * @author  Jim Zombek
 * @version 1.0
 * @since   2016-08-01
 */
public class PassageDao implements BaseDao<Passage> {
  private Logger logger = LoggerFactory.getLogger(PassageDao.class);
  private static PassageDao instance = null;
  private Connection connection = null;

  private static final String PASSAGE_TABLE = "passage";
  private static final String PASSAGE_ID = "id";
  private static final String PASSAGE_TRANSLATION_ID = "translationId";
  private static final String PASSAGE_SCRIPTURE_ID = "scriptureId";
  private static final String PASSAGE_TEXT = "text";
   
         
  private PassageDao() {
	connection = ConnectionFactory.getInstance().getConnection();
  }
  
  /**
   * This method returns the single instance of the PassageDao object.
   * 
   * @return  the PassageDao object
   * 
   */
  public static PassageDao getInstance() {
      if (instance == null) {      
          synchronized (PassageDao.class) {
              if (instance == null) {
                  instance = new PassageDao(); 
              }
          }
      }
      return instance;
  }
      
  /**
   * This method reads the specified passage id record from the passage
   * table stored in MySQL and returns a Passage object.
   * 
   * @param   id  the unique id of the passage to return.
   * @return      the Passage object
   * 
   * @throws      DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public Passage get(int id) throws DaoException {
      Passage passage = null;
      String sql = "SELECT * FROM " + PASSAGE_TABLE + " WHERE " + PASSAGE_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  try (ResultSet resultSet = pstmt.executeQuery();) {
   	          while (resultSet.next()) {
                  passage = getPassageFromResultSet(resultSet);
   	          }
          }
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": getPassage() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
      return passage;
  }

  /**
   * This method reads all the passage records from the passage table stored in MySQL and 
   * returns a list of Passage objects.
   * 
   * <p><b>Note:</b> This method is the equivalent of a full table scan. However, since
   * the contents of this table is small, this method will be performant.</p>
   * 
   * @return      List of Passage objects
   * 
   * @throws      DaoException if a SQL Exception was encountered during processing 
   */
  @Override
  public List<Passage> list() throws DaoException {
      ArrayList<Passage> passages = new ArrayList<Passage>();
      String sql = "SELECT * FROM " + PASSAGE_TABLE + ";";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);
    	   ResultSet resultSet = pstmt.executeQuery();) {
     	   while (resultSet.next()) {
              Passage passage = getPassageFromResultSet(resultSet);
              passages.add(passage);
           }
       } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": getPassages() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
 	       e.printStackTrace();
           throw new DaoException(e, errorMessage);
       }
      return passages;
   }
  
  /**
   * This method adds the specified Passage record to the passage table stored in MySQL.
   * 
   * <p><b>Note:</b>Referential integrity foreign key constraints will be checked
   * prior to adding the specified organization record to the organization table
   * stored in MySQL. The organization record associated with this personality
   * must exist in the organization table stored in MySQL.</p>
   * 
   * @param    passage the Passage object
   * @return                the unique id of the Passage added
   * 
   * @throws   DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public int add(Passage passage) throws DaoException {
      int autoIncKey = -1;
	  String sql = "INSERT INTO " + PASSAGE_TABLE + "(" +
	               PASSAGE_TRANSLATION_ID + "," + 
	               PASSAGE_SCRIPTURE_ID + "," + 
	               PASSAGE_TEXT + ") " +  "VALUES" +  
	               "(?,?,?)";
	  	               
       try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
          pstmt.setInt(1,passage.getTranslationId());
          pstmt.setInt(2,passage.getScriptureId());
          pstmt.setString(3,passage.getText());
          pstmt.executeUpdate();
          
          // Get the auto-incremented key
          ResultSet rs = pstmt.getGeneratedKeys();
          if (rs.next()) {
              autoIncKey = rs.getInt(1);
          } else {
          	  String errorMessage = this.getClass().getName() + ": addPassage() - REASON-> " +
        	      "error obtaining auto incremented key";
        	  logger.error(errorMessage);
        	  throw new DaoException(new Exception(), errorMessage);
          }
       } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": addPassage() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DaoException(e, errorMessage);
       }
       return autoIncKey;
  }
  
  /**
   * This method updates the specified Passage record in the passage table stored in MySQL.
   * 
   * @param    passage  the Passage object
   * 
   * @throws   DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public void update(Passage passage) throws DaoException {
      String sql = "UPDATE " + PASSAGE_TABLE + " SET " +
                   PASSAGE_TRANSLATION_ID + "=?, " + 
                   PASSAGE_SCRIPTURE_ID + "=?, " + 
                   PASSAGE_TEXT + "=? " +  "WHERE " + 
                   PASSAGE_ID + "=?;";
       
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
          pstmt.setInt(1,passage.getTranslationId());
          pstmt.setInt(2,passage.getScriptureId());
          pstmt.setString(3,passage.getText());
          pstmt.setInt(4,passage.getPassageId());
          pstmt.executeUpdate();
      } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": updatePassage() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
    }
  
  /**
   * This method deletes the specified passage id from the passage table stored in MySQL. 
   * 
   * <p><b>Note:</b>Referential integrity foreign key constraints will be checked
   * prior to deleting the specified personality record from the personality table
   * stored in MySQL. All commentary records in the commentary table stored in MySQL
   * associated with the specified personality id must have been previously deleted
   * prior to calling this method.</p>
   * 
   * @param   id  the unique id of the passage to delete.
   *  
   * @throws  DaoException if a SQL Exception was encountered during processing
   */
  @Override
   public void delete(int id) throws DaoException {
      String sql = "DELETE fROM " + PASSAGE_TABLE + " WHERE " + PASSAGE_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  pstmt.execute();
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": deletePassage() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
   }
  
   private Passage getPassageFromResultSet(ResultSet resultSet) throws SQLException {
       Passage passage = new Passage();
       
       passage.setPassageId(resultSet.getInt(PASSAGE_ID));
       passage.setTranslationId(resultSet.getInt(PASSAGE_TRANSLATION_ID));
       passage.setScriptureId(resultSet.getInt(PASSAGE_SCRIPTURE_ID));
       passage.setText(resultSet.getString(PASSAGE_TEXT));
              
       return passage;
   }
}