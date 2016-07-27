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
import com.emptytomb.dbmanager.domain.Personality;
import com.emptytomb.dbmanager.utility.ConnectionFactory;


/**
 * The PersonalityDao class is a concrete JDBC implementation of the BaseDao interface.
 * This class implements the JDBC persistence storage layer for the Personality domain model.
 * 
 * <p><b>Note:</b> This class is implemented as a Singleton. Only one instance of this class
 * can exist at a time.</p>
 * 
 * @author  Jim Zombek
 * @version 1.0
 * @since   2016-05-31
 */
public class PersonalityDao implements BaseDao<Personality> {
  private Logger logger = LoggerFactory.getLogger(PersonalityDao.class);
  private static PersonalityDao instance = null;
  private Connection connection = null;
  
  private static final String PERSONALITY_TABLE = "personality";
  private static final String PERSONALITY_ID = "id";
  private static final String PERSONALITY_ORGANIZATION_ID = "organizationId";
  private static final String PERSONALITY_NAME = "name";
  private static final String PERSONALITY_TITLE = "title";
  private static final String PERSONALITY_BIO = "bio";
  private static final String PERSONALITY_PICTURE = "picture";  
     
   private PersonalityDao() {
	connection = ConnectionFactory.getInstance().getConnection();
  }
  
  /**
   * This method returns the single instance of the PersonalityDao object.
   * 
   * @return  the PersonalityDao object
   * 
   */
  public static PersonalityDao getInstance() {
      if (instance == null) {      
          synchronized (PersonalityDao.class) {
              if (instance == null) {
                  instance = new PersonalityDao(); 
              }
          }
      }
      return instance;
  }
      
  /**
   * This method reads the specified personality id record from the personality
   * table stored in MySQL and returns a Personality object.
   * 
   * @param   id  the unique id of the personality to return.
   * @return      the Personality object
   * 
   * @throws      DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public Personality get(int id) throws DaoException {
      Personality personality = null;
      String sql = "SELECT * FROM " + PERSONALITY_TABLE + " WHERE " + PERSONALITY_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  try (ResultSet resultSet = pstmt.executeQuery();) {
   	          while (resultSet.next()) {
                  personality = getPersonalityFromResultSet(resultSet);
   	          }
          }
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": getPersonality() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
      return personality;
  }

  /**
   * This method reads all the personality records from the personality table
   * stored in MySQL and returns a list of Personality objects.
   * 
   * <p><b>Note:</b> This method is the equivalent of a full table scan. However, since
   * the contents of this table is small, this method will be performant.</p>
   * 
   * @return      List of Personality objects
   * 
   * @throws      DaoException if a SQL Exception was encountered during processing 
   */
  @Override
  public List<Personality> list() throws DaoException {
      ArrayList<Personality> personalities = new ArrayList<Personality>();
      String sql = "SELECT * FROM " + PERSONALITY_TABLE + ";";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);
    	   ResultSet resultSet = pstmt.executeQuery();) {
     	   while (resultSet.next()) {
              Personality personality = getPersonalityFromResultSet(resultSet);
              personalities.add(personality);
           }
       } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": getPersonalities() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
 	       e.printStackTrace();
           throw new DaoException(e, errorMessage);
       }
      return personalities;
   }
  
  /**
   * This method adds the specified Personality record to the personality
   * table stored in MySQL.
   * 
   * <p><b>Note:</b>Referential integrity foreign key constraints will be checked
   * prior to adding the specified personality record to the personality table
   * stored in MySQL. The organization record associated with this personality
   * must exist in the organization table stored in MySQL.</p>
   * 
   * @param    personality  the Personality object
   * @return                the unique id of the Personality added
   * 
   * @throws   DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public int add(Personality personality) throws DaoException {
      int autoIncKey = -1;
	  String sql = "INSERT INTO " + PERSONALITY_TABLE + "(" +
    		       PERSONALITY_ORGANIZATION_ID + "," +     
    		       PERSONALITY_NAME + "," +
    		       PERSONALITY_TITLE + "," +
 	               PERSONALITY_BIO + "," +
	               PERSONALITY_PICTURE + ") " +  "VALUES" +
 	               "(?,?,?,?,?)";
                   	   	               
       try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
          pstmt.setInt(1,personality.getOrganizationId());
          pstmt.setString(2,personality.getName());
          pstmt.setString(3,personality.getTitle());
          pstmt.setString(4,personality.getBio());
          pstmt.setBytes(5,personality.getPicture());
          pstmt.executeUpdate();
          
          // Get the auto-incremented key
          ResultSet rs = pstmt.getGeneratedKeys();
          if (rs.next()) {
              autoIncKey = rs.getInt(1);
          } else {
          	  String errorMessage = this.getClass().getName() + ": addPersonality() - REASON-> " +
        	      "error obtaining auto incremented key";
        	  logger.error(errorMessage);
        	  throw new DaoException(new Exception(), errorMessage);
          }
       } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": addPersonality() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DaoException(e, errorMessage);
       }
       return autoIncKey;
  }
  
  /**
   * This method updates the specified Personality record in the personality
   * table stored in MySQL.
   * 
   * @param    personality  the Personality object
   * 
   * @throws   DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public void update(Personality personality) throws DaoException {
      String sql = "UPDATE " + PERSONALITY_TABLE + " SET " +
    		       PERSONALITY_ORGANIZATION_ID + "=?, " +     
    		       PERSONALITY_NAME + "=?, " +
    		       PERSONALITY_TITLE + "=?, " +
 	               PERSONALITY_BIO + "=?, " +
	               PERSONALITY_PICTURE + "=? " +  "WHERE " + 
	               PERSONALITY_ID + "=?;";
 	               
       try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
          pstmt.setInt(1,personality.getOrganizationId());
          pstmt.setString(2,personality.getName());
          pstmt.setString(3,personality.getTitle());
          pstmt.setString(4,personality.getBio());
          pstmt.setBytes(5,personality.getPicture());
          pstmt.setInt(6,personality.getPersonalityId());
          pstmt.executeUpdate();
      } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": updatePersonality() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
    }
  
  /**
   * This method deletes the specified personality id from the personality table
   * stored in MySQL. 
   * 
   * <p><b>Note:</b>Referential integrity foreign key constraints will be checked
   * prior to deleting the specified personality record from the personality table
   * stored in MySQL. All commentary records in the commentary table stored in MySQL
   * associated with the specified personality id must have been previously deleted
   * prior to calling this method.</p>
   * 
   * @param   id  the unique id of the personality to delete.
   *  
   * @throws  DaoException if a SQL Exception was encountered during processing
   */
  @Override
   public void delete(int id) throws DaoException {
      String sql = "DELETE fROM " + PERSONALITY_TABLE + " WHERE " + PERSONALITY_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  pstmt.execute();
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": deletePersonality() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
   }
  
   private Personality getPersonalityFromResultSet(ResultSet resultSet) throws SQLException {
       Personality personality = new Personality();
       personality.setPersonalityId(resultSet.getInt(PERSONALITY_ID));
       personality.setOrganizationId(resultSet.getInt(PERSONALITY_ORGANIZATION_ID));
       personality.setName(resultSet.getString(PERSONALITY_NAME));
       personality.setTitle(resultSet.getString(PERSONALITY_TITLE));
       personality.setBio(resultSet.getString(PERSONALITY_BIO));
       personality.setPicture(resultSet.getBytes(PERSONALITY_PICTURE));
       return personality;
   }
}