package com.emptytomb.dbmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emptytomb.dbmanager.domain.Pastor;
import com.emptytomb.dbmanager.utility.ConnectionFactory;
import com.emptytomb.dbmanager.utility.Constants;

public class PastorDAO {
  private Logger logger = LoggerFactory.getLogger(PastorDAO.class);
  private static PastorDAO instance = null;
  private Connection connection = null;
     
   
  private PastorDAO() {
	connection = ConnectionFactory.getInstance().getConnection();
  }

  public static PastorDAO getInstance() {
      if (instance == null) {      
          synchronized (PastorDAO.class) {
              if (instance == null) {
                  instance = new PastorDAO(); 
              }
          }
      }
      return instance;
  }
  
  public Pastor getPastor(int id) throws DAOException {
      Pastor pastor = null;
      String sql = "SELECT * FROM " + Constants.PASTOR_TABLE + " WHERE " + Constants.PASTOR_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  try (ResultSet resultSet = pstmt.executeQuery();) {
   	          while (resultSet.next()) {
                  pastor = getPastorFromResultSet(resultSet);
   	          }
          }
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": getPastor() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DAOException(e, errorMessage);
      }
      return pastor;
  }

  public List<Pastor> getPastors() throws DAOException {
      ArrayList<Pastor> pastors = new ArrayList<Pastor>();
      String sql = "SELECT * FROM " + Constants.PASTOR_TABLE + ";";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);
    	   ResultSet resultSet = pstmt.executeQuery();) {
     	   while (resultSet.next()) {
              Pastor pastor = getPastorFromResultSet(resultSet);
              pastors.add(pastor);
           }
       } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": getPastors() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
 	       e.printStackTrace();
           throw new DAOException(e, errorMessage);
       }
      return pastors;
   }
  
  // investigate returning auto id
  public void addPastor(Pastor pastor) throws DAOException {
      String sql = "INSERT INTO " + Constants.PASTOR_TABLE + "(" +
    		       Constants.PASTOR_CHURCH_ID + "," +     
    		       Constants.PASTOR_NAME + "," +
 	               Constants.PASTOR_BIO + "," +
	               Constants.PASTOR_PICTURE + ") " +  "VALUES" +
 	               "(?,?,?,?)";
 	               
       try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
          pstmt.setInt(1,pastor.getChurchId());
          pstmt.setString(2,pastor.getName());
          pstmt.setString(3,pastor.getBio());
          pstmt.setBytes(4,pastor.getPicture());
          pstmt.executeUpdate();
      } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": addPastor() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DAOException(e, errorMessage);
      }
  }
  
  public void updatePastor(Pastor pastor) throws DAOException {
      String sql = "UPDATE " + Constants.PASTOR_TABLE + " SET " +
    		       Constants.PASTOR_CHURCH_ID + "=?, " +     
    		       Constants.PASTOR_NAME + "=?, " +
 	               Constants.PASTOR_BIO + "=?, " +
	               Constants.PASTOR_PICTURE + "=?, " +  "WHERE " + 
	               Constants.PASTOR_ID;
	               
       try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
          pstmt.setInt(1,pastor.getChurchId());
          pstmt.setString(2,pastor.getName());
          pstmt.setString(3,pastor.getBio());
          pstmt.setBytes(4,pastor.getPicture());
          pstmt.setInt(5,pastor.getPastorId());
          pstmt.executeUpdate();
      } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": addPastor() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DAOException(e, errorMessage);
      }

      //INSERT INTO phonebook(phone, firstname, lastname, address) VALUES('+1 123 456 7890', 'John', 'Doe', 'North America')
    }
  
  // investigate referrial integrity is pastor a foreign key into any tables??
  public void deletePastor(int id) throws DAOException {
      String sql = "DELETE fROM " + Constants.PASTOR_TABLE + " WHERE " + Constants.PASTOR_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  pstmt.execute();
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": deletePastor() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DAOException(e, errorMessage);
      }
   }
  
   private Pastor getPastorFromResultSet(ResultSet resultSet) throws SQLException {
       Pastor pastor = new Pastor();
       pastor.setPastorId(resultSet.getInt(Constants.PASTOR_ID));
       pastor.setChurchId(resultSet.getInt(Constants.PASTOR_CHURCH_ID));
       pastor.setName(resultSet.getString(Constants.PASTOR_NAME));
       pastor.setBio(resultSet.getString(Constants.PASTOR_BIO));
       pastor.setPicture(resultSet.getBytes(Constants.PASTOR_PICTURE));
       return pastor;
   }
}