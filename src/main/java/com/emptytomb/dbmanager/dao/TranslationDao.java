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
import com.emptytomb.dbmanager.domain.Translation;
import com.emptytomb.dbmanager.utility.ConnectionFactory;

/**
 * The TranslationDao class is a concrete JDBC implementation of the BaseDao interface. This class
 * implements the JDBC persistence storage layer for the Translation domain model.
 * 
 * <p><b>Note:</b> This class is implemented as a Singleton. Only one instance of this class
 * can exist at a time.</p>
 * 
 * @author  Jim Zombek
 * @version 1.0
 * @since   2016-07-01
 */
public class TranslationDao implements BaseDao<Translation> {
  private Logger logger = LoggerFactory.getLogger(TranslationDao.class);
  private static TranslationDao instance = null;
  private Connection connection = null;
  
  private static final String TRANSLATION_TABLE = "translation";
  private static final String TRANSLATION_ID = "id";
  private static final String TRANSLATION_NAME = "name";
  private static final String TRANSLATION_HISTORY = "history";
  private static final String TRANSLATION_VERSION = "version";  // niv
       
   private TranslationDao() {
	connection = ConnectionFactory.getInstance().getConnection();
  }
  
  /**
   * This method returns the single instance of the TranslationDao object.
   * 
   * @return  the TranslationDao object
   * 
   */
  public static TranslationDao getInstance() {
      if (instance == null) {      
          synchronized (TranslationDao.class) {
              if (instance == null) {
                  instance = new TranslationDao(); 
              }
          }
      }
      return instance;
  }
      
  /**
   * This method reads the specified translation id record from the translation
   * table stored in MySQL and returns a Translation object.
   * 
   * @param   id  the unique id of the translation to return.
   * @return      the Translation object
   * 
   * @throws      DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public Translation get(int id) throws DaoException {
      Translation translation = null;
      String sql = "SELECT * FROM " + TRANSLATION_TABLE + " WHERE " + TRANSLATION_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  try (ResultSet resultSet = pstmt.executeQuery();) {
   	          while (resultSet.next()) {
                  translation = getTranslationFromResultSet(resultSet);
   	          }
          }
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": getTranslation() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
      return translation;
  }

  /**
   * This method reads all the translation records from the translation table
   * stored in MySQL and returns a list of Translation objects.
   * 
   * <p><b>Note:</b> This method is the equivalent of a full table scan. However, since
   * the contents of this table is small, this method will be performant.</p>
   * 
   * @return      List of Translation objects
   * 
   * @throws      DaoException if a SQL Exception was encountered during processing 
   */
  @Override
  public List<Translation> list() throws DaoException {
      ArrayList<Translation> translations = new ArrayList<Translation>();
      String sql = "SELECT * FROM " + TRANSLATION_TABLE + ";";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);
    	   ResultSet resultSet = pstmt.executeQuery();) {
     	   while (resultSet.next()) {
              Translation translation = getTranslationFromResultSet(resultSet);
              translations.add(translation);
           }
       } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": getTranslations() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
 	       e.printStackTrace();
           throw new DaoException(e, errorMessage);
       }
      return translations;
   }
  
  /**
   * This method adds the specified Translation record to the translation table
   * stored in MySQL.
   * 
   * <p><b>Note:</b>Referential integrity foreign key constraints will be checked
   * prior to adding the specified organization record to the organization table
   * stored in MySQL. The organization record associated with this personality
   * must exist in the organization table stored in MySQL.</p>
   * 
   * @param    translation the Translation object
   * @return                the unique id of the Translation added
   * 
   * @throws   DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public int add(Translation translation) throws DaoException {
      int autoIncKey = -1;
	  String sql = "INSERT INTO " + TRANSLATION_TABLE + "(" +
	               TRANSLATION_NAME + "," + 
	               TRANSLATION_HISTORY + "," + 
	               TRANSLATION_VERSION + ") " +  "VALUES" +  
	               "(?,?,?)";
  	               
       try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
          pstmt.setString(1,translation.getName()); 
          pstmt.setString(2,translation.getHistory());   
          pstmt.setString(3,translation.getVersion());  
          pstmt.executeUpdate();
          
          // Get the auto-incremented key
          ResultSet rs = pstmt.getGeneratedKeys();
          if (rs.next()) {
              autoIncKey = rs.getInt(1);
          } else {
          	  String errorMessage = this.getClass().getName() + ": addTranslation() - REASON-> " +
        	      "error obtaining auto incremented key";
        	  logger.error(errorMessage);
        	  throw new DaoException(new Exception(), errorMessage);
          }
       } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": addTranslation() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DaoException(e, errorMessage);
       }
       return autoIncKey;
  }
  
  /**
   * This method updates the specified Translation record in the translation table
   * stored in MySQL.
   * 
   * @param    translation  the Translation object
   * 
   * @throws   DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public void update(Translation translation) throws DaoException {
      String sql = "UPDATE " + TRANSLATION_TABLE + " SET " +
                   TRANSLATION_NAME + "=?, " + 
                   TRANSLATION_HISTORY + "=?, " +    
                   TRANSLATION_VERSION + "=? " +  "WHERE " + 
                   TRANSLATION_ID + "=?;";
    	               
       try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
          pstmt.setString(1,translation.getName()); 
          pstmt.setString(2,translation.getHistory());   
          pstmt.setString(3,translation.getVersion());  
          pstmt.setInt(4,translation.getTranslationId());    
          pstmt.executeUpdate();
      } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": updateTranslation() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
    }
  
  /**
   * This method deletes the specified translation id from the translation table
   * stored in MySQL. 
   * 
   * <p><b>Note:</b>Referential integrity foreign key constraints will be checked
   * prior to deleting the specified personality record from the personality table
   * stored in MySQL. All commentary records in the commentary table stored in MySQL
   * associated with the specified personality id must have been previously deleted
   * prior to calling this method.</p>
   * 
   * @param   id  the unique id of the translation to delete.
   *  
   * @throws  DaoException if a SQL Exception was encountered during processing
   */
  @Override
   public void delete(int id) throws DaoException {
      String sql = "DELETE fROM " + TRANSLATION_TABLE + " WHERE " + TRANSLATION_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  pstmt.execute();
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": deleteTranslation() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
   }
  
   private Translation getTranslationFromResultSet(ResultSet resultSet) throws SQLException {
       Translation translation = new Translation();
       
       translation.setTranslationId(resultSet.getInt(TRANSLATION_ID));
       translation.setName(resultSet.getString(TRANSLATION_NAME));
       translation.setHistory(resultSet.getString(TRANSLATION_HISTORY));
       translation.setVersion(resultSet.getString(TRANSLATION_VERSION));
                   
       return translation;
   }
}