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
import com.emptytomb.dbmanager.domain.Organization;
import com.emptytomb.dbmanager.utility.ConnectionFactory;

/**
 * The OrganizationDao class is a concrete JDBC implementation of the BaseDao interface. This class
 * implements the JDBC persistence storage layer for the Organization domain model.
 * 
 * <p><b>Note:</b> This class is implemented as a Singleton. Only one instance of this class
 * can exist at a time.</p>
 * 
 * @author  Jim Zombek
 * @version 1.0
 * @since   2016-07-01
 */
public class OrganizationDao implements BaseDao<Organization> {
  private Logger logger = LoggerFactory.getLogger(OrganizationDao.class);
  private static OrganizationDao instance = null;
  private Connection connection = null;
  
  private static final String ORGANIZATION_ID = "id";
  private static final String ORGANIZATION_TABLE = "organization";
  private static final String ORGANIZATION_NAME = "name";
  private static final String ORGANIZATION_ADDRESS_LINE_1 = "addressLine1";
  private static final String ORGANIZATION_ADDRESS_LINE_2 = "addressLine2";
  private static final String ORGANIZATION_CITY = "city";
  private static final String ORGANIZATION_PROVIDENCE = "providence";
  private static final String ORGANIZATION_POSTAL_CODE = "postalCode";
  private static final String ORGANIZATION_COUNTRY = "country";
  private static final String ORGANIZATION_YEAR_STARTED = "yearStarted";
  private static final String ORGANIZATION_AFFILIATION = "affiliation";
  private static final String ORGANIZATION_WEB_SITE = "webSite";
  private static final String ORGANIZATION_MEMBERSHIP_SIZE = "membershipSize";
     
   private OrganizationDao() {
	connection = ConnectionFactory.getInstance().getConnection();
  }
  
  /**
   * This method returns the single instance of the OrganizationDaoJdbc object.
   * 
   * @return  the OrganizationDaoJdbc object
   * 
   */
  public static OrganizationDao getInstance() {
      if (instance == null) {      
          synchronized (OrganizationDao.class) {
              if (instance == null) {
                  instance = new OrganizationDao(); 
              }
          }
      }
      return instance;
  }
      
  /**
   * This method reads the specified organization id record from the organization
   * table stored in MySQL and returns an Organization object.
   * 
   * @param   id  the unique id of the organization to return.
   * @return      the Organization object
   * 
   * @throws      DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public Organization get(int id) throws DaoException {
      Organization organization = null;
      String sql = "SELECT * FROM " + ORGANIZATION_TABLE + " WHERE " + ORGANIZATION_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  try (ResultSet resultSet = pstmt.executeQuery();) {
   	          while (resultSet.next()) {
                  organization = getOrganizationFromResultSet(resultSet);
   	          }
          }
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": getOrganization() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
      return organization;
  }

  /**
   * This method reads all the organization records from the organization table
   * stored in MySQL and returns a list of Organization objects.
   * 
   * <p><b>Note:</b> This method is the equivalent of a full table scan. However, since
   * the contents of this table is small, this method will be performant.</p>
   * 
   * @return      List of Organization objects
   * 
   * @throws      DaoException if a SQL Exception was encountered during processing 
   */
  @Override
  public List<Organization> list() throws DaoException {
      ArrayList<Organization> organizations = new ArrayList<Organization>();
      String sql = "SELECT * FROM " + ORGANIZATION_TABLE + ";";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);
    	   ResultSet resultSet = pstmt.executeQuery();) {
     	   while (resultSet.next()) {
              Organization organization = getOrganizationFromResultSet(resultSet);
              organizations.add(organization);
           }
       } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": getOrganizations() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
 	       e.printStackTrace();
           throw new DaoException(e, errorMessage);
       }
      return organizations;
   }
  
  /**
   * This method adds the specified Organization record to the organization
   * table stored in MySQL.
   * 
   * <p><b>Note:</b>Referential integrity foreign key constraints will be checked
   * prior to adding the specified organization record to the organization table
   * stored in MySQL. The organization record associated with this personality
   * must exist in the organization table stored in MySQL.</p>
   * 
   * @param    organization the Organization object
   * @return                the unique id of the Organization added
   * 
   * @throws   DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public int add(Organization organization) throws DaoException {
      int autoIncKey = -1;
	  String sql = "INSERT INTO " + ORGANIZATION_TABLE + "(" +
	               ORGANIZATION_NAME + "," + 
	               ORGANIZATION_ADDRESS_LINE_1 + "," + 
	               ORGANIZATION_ADDRESS_LINE_2 + "," + 
	               ORGANIZATION_CITY + "," + 
	               ORGANIZATION_PROVIDENCE + "," + 
	               ORGANIZATION_POSTAL_CODE + "," + 
	               ORGANIZATION_COUNTRY + "," + 
	               ORGANIZATION_YEAR_STARTED + "," + 
	               ORGANIZATION_AFFILIATION + "," + 
	               ORGANIZATION_WEB_SITE + "," + 
	               ORGANIZATION_MEMBERSHIP_SIZE + ") " +  "VALUES" +  
	               "(?,?,?,?,?,?,?,?,?,?,?)";
                   	   	               
       try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
          pstmt.setString(1,organization.getName()); 
          pstmt.setString(2,organization.getAddressLine1());   
          pstmt.setString(3,organization.getAddressLine2());  
          pstmt.setString(4,organization.getCity());    
          pstmt.setString(5,organization.getProvidence());    
          pstmt.setString(6,organization.getPostalCode());    
          pstmt.setString(7,organization.getCountry());    
          pstmt.setInt(8,organization.getYearStarted());    
          pstmt.setString(9,organization.getAffiliation());   
          pstmt.setString(10,organization.getWebSite());   
          pstmt.setInt(11,organization.getMembershipSize());   
          pstmt.executeUpdate();
          
          // Get the auto-incremented key
          ResultSet rs = pstmt.getGeneratedKeys();
          if (rs.next()) {
              autoIncKey = rs.getInt(1);
          } else {
          	  String errorMessage = this.getClass().getName() + ": addOrganization() - REASON-> " +
        	      "error obtaining auto incremented key";
        	  logger.error(errorMessage);
        	  throw new DaoException(new Exception(), errorMessage);
          }
       } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": addOrganization() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DaoException(e, errorMessage);
       }
       return autoIncKey;
  }
  
  /**
   * This method updates the specified Organization record in the organization table
   * stored in MySQL.
   * 
   * @param    organization  the Organization object
   * 
   * @throws   DaoException if a SQL Exception was encountered during processing
   */
  @Override
  public void update(Organization organization) throws DaoException {
      String sql = "UPDATE " + ORGANIZATION_TABLE + " SET " +
                   ORGANIZATION_NAME + "=?, " + 
                   ORGANIZATION_ADDRESS_LINE_1 + "=?, " +    
                   ORGANIZATION_ADDRESS_LINE_2  + "=?, " + 
                   ORGANIZATION_CITY  + "=?, " + 
                   ORGANIZATION_PROVIDENCE + "=?, " +  
                   ORGANIZATION_POSTAL_CODE  + "=?, " + 
                   ORGANIZATION_COUNTRY  + "=?, " + 
                   ORGANIZATION_YEAR_STARTED + "=?, " +   
                   ORGANIZATION_AFFILIATION   + "=?, " + 
                   ORGANIZATION_WEB_SITE  + "=?, " + 
                   ORGANIZATION_MEMBERSHIP_SIZE + "=? " +  "WHERE " + 
                   ORGANIZATION_ID + "=?;";
   	               
       try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
          pstmt.setString(1,organization.getName()); 
          pstmt.setString(2,organization.getAddressLine1());   
          pstmt.setString(3,organization.getAddressLine2());  
          pstmt.setString(4,organization.getCity());    
          pstmt.setString(5,organization.getProvidence());    
          pstmt.setString(6,organization.getPostalCode());    
          pstmt.setString(7,organization.getCountry());    
          pstmt.setInt(8,organization.getYearStarted());    
          pstmt.setString(9,organization.getAffiliation());   
          pstmt.setString(10,organization.getWebSite());   
          pstmt.setInt(11,organization.getMembershipSize());  
          pstmt.setInt(12,organization.getOrganizationId()); 
            
          pstmt.executeUpdate();
      } catch (SQLException e) {
    	   String errorMessage = this.getClass().getName() + ": updateOrganization() - REASON-> " + e.getMessage();
   	       logger.error(errorMessage);
	       e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
    }
  
  /**
   * This method deletes the specified organization id from the organization table
   * stored in MySQL. 
   * 
   * <p><b>Note:</b>Referential integrity foreign key constraints will be checked
   * prior to deleting the specified personality record from the personality table
   * stored in MySQL. All commentary records in the commentary table stored in MySQL
   * associated with the specified personality id must have been previously deleted
   * prior to calling this method.</p>
   * 
   * @param   id  the unique id of the organization to delete.
   *  
   * @throws  DaoException if a SQL Exception was encountered during processing
   */
  @Override
   public void delete(int id) throws DaoException {
      String sql = "DELETE fROM " + ORGANIZATION_TABLE + " WHERE " + ORGANIZATION_ID + " = ?;";
      try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
     	  pstmt.setInt(1, id);
      	  pstmt.execute();
      } catch (SQLException e) {
          String errorMessage = this.getClass().getName() + ": deleteOrganization() - REASON-> " + e.getMessage();
  	      logger.error(errorMessage);
          e.printStackTrace();
          throw new DaoException(e, errorMessage);
      }
   }
  
   private Organization getOrganizationFromResultSet(ResultSet resultSet) throws SQLException {
       Organization organization = new Organization();
       
       organization.setOrganizationId(resultSet.getInt(ORGANIZATION_ID));
       organization.setName(resultSet.getString(ORGANIZATION_NAME));
       organization.setAddressLine1(resultSet.getString(ORGANIZATION_ADDRESS_LINE_1));
       organization.setAddressLine2(resultSet.getString(ORGANIZATION_ADDRESS_LINE_2));
       organization.setCity(resultSet.getString(ORGANIZATION_CITY));
       organization.setProvidence(resultSet.getString(ORGANIZATION_PROVIDENCE));
       organization.setPostalCode(resultSet.getString(ORGANIZATION_POSTAL_CODE));
       organization.setCountry(resultSet.getString(ORGANIZATION_COUNTRY));
       organization.setYearStarted(resultSet.getInt(ORGANIZATION_YEAR_STARTED));
       organization.setAffiliation(resultSet.getString(ORGANIZATION_AFFILIATION));
       organization.setWebSite(resultSet.getString(ORGANIZATION_WEB_SITE));
       organization.setMembershipSize(resultSet.getInt(ORGANIZATION_MEMBERSHIP_SIZE));
             
       return organization;
   }
}