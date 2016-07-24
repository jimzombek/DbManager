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
import com.emptytomb.dbmanager.dao.ScriptureDao;
import com.emptytomb.dbmanager.domain.Scripture;
import com.emptytomb.dbmanager.utility.ConnectionFactory;
import com.emptytomb.dbmanager.utility.Constants;

/**
 * The ScriptureDaoJdbc class is a concrete JDBC implementation of the ScriptureDao interface.
 * This class implements the JDBC persistence storage layer for the Scripture domain model.
 * 
 * <p><b>Note:</b> This class is implemented as a Singleton. Only one instance of this class
 * can exist at a time.</p>
 * 
 * @author  Jim Zombek
 * @version 1.0
 * @since   2016-07-01
 */
public class ScriptureDaoJdbc implements ScriptureDao {
  private Logger logger = LoggerFactory.getLogger(ScriptureDaoJdbc.class);
  private static ScriptureDaoJdbc instance = null;
  private Connection connection = null;

  private static final String SCRIPTURE_TABLE = "scripture";
  private static final String SCRIPTURE_ID = "id";
  private static final String SCRIPTURE_TRANSLATION_ID = "translationId";
  private static final String SCRIPTURE_TESTAMENT = "testament";
  private static final String SCRIPTURE_BOOK = "book";
  private static final String SCRIPTURE_CHAPTER ="chapter";
  private static final String SCRIPTURE_VERSE = "verse";
  private static final String SCRIPTURE_TEXT = "text";
  private static final String SCRIPTURE_PROPHECY ="prophecy";
       
   private ScriptureDaoJdbc() {
	connection = ConnectionFactory.getInstance().getConnection();
  }
  
  /**
   * This method returns the single instance of the ScriptureDaoJdbc object.
   * 
   * @return  the ScriptureDaoJdbc object
   * 
   */
  public static ScriptureDaoJdbc getInstance() {
      if (instance == null) {      
          synchronized (ScriptureDaoJdbc.class) {
              if (instance == null) {
                  instance = new ScriptureDaoJdbc(); 
              }
          }
      }
      return instance;
  }
      
  /**
   * This method reads the specified scripture id record from the scripture
   * table stored in MySQL and returns a Scripture object.
   * 
   * @param   id  the unique id of the scripture to return.
   * @return      the Scripture object
   * 
   * @throws      DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public Scripture getScripture(int id) throws DaoException {
      Scripture scripture = null;
      String sql = "SELECT * FROM " + SCRIPTURE_TABLE + " WHERE " + SCRIPTURE_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  try (ResultSet resultSet = pstmt.executeQuery();) {
   	          while (resultSet.next()) {
                  scripture = getScriptureFromResultSet(resultSet);
   	          }
          }
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": getScripture() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
      return scripture;
  }

  /**
   * This method reads all the scripture records from the scripture table
   * stored in MySQL and returns a list of Scripture objects.
   * 
   * <p><b>Note:</b> This method is the equivalent of a full table scan. However, since
   * the contents of this table is small, this method will be performant.</p>
   * 
   * @return      List of Scripture objects
   * 
   * @throws      DaoException if a SQL Exception was encountered during processing 
   */
  @Override
  public List<Scripture> getScriptures() throws DaoException {
      ArrayList<Scripture> scriptures = new ArrayList<Scripture>();
      String sql = "SELECT * FROM " + SCRIPTURE_TABLE + ";";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);
    	   ResultSet resultSet = pstmt.executeQuery();) {
     	   while (resultSet.next()) {
              Scripture scripture = getScriptureFromResultSet(resultSet);
              scriptures.add(scripture);
           }
       } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": getScriptures() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
 	       e.printStackTrace();
           throw new DaoException(e, errorMessage);
       }
      return scriptures;
   }
  
  /**
   * This method adds the specified Scripture record to the scripture table
   * stored in MySQL.
   * 
   * <p><b>Note:</b>Referential integrity foreign key constraints will be checked
   * prior to adding the specified organization record to the organization table
   * stored in MySQL. The organization record associated with this personality
   * must exist in the organization table stored in MySQL.</p>
   * 
   * @param    scripture the Scripture object
   * @return                the unique id of the Scripture added
   * 
   * @throws   DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public int addScripture(Scripture scripture) throws DaoException {
      int autoIncKey = -1;
	  String sql = "INSERT INTO " + SCRIPTURE_TABLE + "(" +
	               SCRIPTURE_TRANSLATION_ID + "," + 
	               SCRIPTURE_TESTAMENT + "," + 
	               SCRIPTURE_BOOK + "," + 
	               SCRIPTURE_CHAPTER + "," + 
	               SCRIPTURE_VERSE + "," + 
	               SCRIPTURE_TEXT + "," +
	               SCRIPTURE_PROPHECY + ") " +  "VALUES" +  
	               "(?,?,?,?,?,?,?)";
	  	               
       try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
          pstmt.setInt(1,scripture.getTranslationId());
          pstmt.setString(2,scripture.getTestament());
          pstmt.setString(3,scripture.getBook());
          pstmt.setInt(4,scripture.getChapter());
          pstmt.setInt(5,scripture.getVerse());
          pstmt.setString(6,scripture.getText());
          pstmt.setBoolean(7,scripture.isProphecy());
          pstmt.executeUpdate();
          
          // Get the auto-incremented key
          ResultSet rs = pstmt.getGeneratedKeys();
          if (rs.next()) {
              autoIncKey = rs.getInt(1);
          } else {
          	  String errorMessage = this.getClass().getName() + ": addScripture() - REASON-> " +
        	      "error obtaining auto incremented key";
        	  logger.error(errorMessage);
        	  throw new DaoException(new Exception(), errorMessage);
          }
       } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": addScripture() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DaoException(e, errorMessage);
       }
       return autoIncKey;
  }
  
  /**
   * This method updates the specified Scripture record in the scripture table
   * stored in MySQL.
   * 
   * @param    scripture  the Scripture object
   * 
   * @throws   DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public void updateScripture(Scripture scripture) throws DaoException {
      String sql = "UPDATE " + SCRIPTURE_TABLE + " SET " +
                   SCRIPTURE_TRANSLATION_ID + "=?, " + 
                   SCRIPTURE_TESTAMENT + "=?, " + 
                   SCRIPTURE_BOOK + "=?, " + 
                   SCRIPTURE_CHAPTER + "=?, " + 
                   SCRIPTURE_VERSE + "=?, " + 
                   SCRIPTURE_TEXT + "=?, " + 
                   SCRIPTURE_PROPHECY + "=? " +  "WHERE " + 
                   SCRIPTURE_ID + "=?;";
       
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
          pstmt.setInt(1,scripture.getTranslationId());
          pstmt.setString(2,scripture.getTestament());
          pstmt.setString(3,scripture.getBook());
          pstmt.setInt(4,scripture.getChapter());
          pstmt.setInt(5,scripture.getVerse());
          pstmt.setString(6,scripture.getText());
          pstmt.setBoolean(7,scripture.isProphecy());
          pstmt.setInt(8,scripture.getTranslationId());
          pstmt.executeUpdate();
      } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": updateScripture() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
    }
  
  /**
   * This method deletes the specified scripture id from the scripture table
   * stored in MySQL. 
   * 
   * <p><b>Note:</b>Referential integrity foreign key constraints will be checked
   * prior to deleting the specified personality record from the personality table
   * stored in MySQL. All commentary records in the commentary table stored in MySQL
   * associated with the specified personality id must have been previously deleted
   * prior to calling this method.</p>
   * 
   * @param   id  the unique id of the scripture to delete.
   *  
   * @throws  DaoException if a SQL Exception was encountered during processing
   */
  @Override
   public void deleteScripture(int id) throws DaoException {
      String sql = "DELETE fROM " + SCRIPTURE_TABLE + " WHERE " + SCRIPTURE_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  pstmt.execute();
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": deleteScripture() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
   }
  
   private Scripture getScriptureFromResultSet(ResultSet resultSet) throws SQLException {
       Scripture scripture = new Scripture();
       
       scripture.setScriptureId(resultSet.getInt(SCRIPTURE_ID));
       scripture.setTranslationId(resultSet.getInt(SCRIPTURE_TRANSLATION_ID));
       scripture.setTestament(resultSet.getString(SCRIPTURE_TESTAMENT));
       scripture.setBook(resultSet.getString(SCRIPTURE_BOOK));
       scripture.setChapter(resultSet.getInt(SCRIPTURE_CHAPTER));
       scripture.setVerse(resultSet.getInt(SCRIPTURE_VERSE));
       scripture.setText(resultSet.getString(SCRIPTURE_TEXT));
       scripture.setProphecy(resultSet.getBoolean(SCRIPTURE_PROPHECY));
                              
       return scripture;
   }
}