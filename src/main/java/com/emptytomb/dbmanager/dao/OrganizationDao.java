package com.emptytomb.dbmanager.dao;

import java.util.List;
import com.emptytomb.dbmanager.domain.Organization;

/**
* The OrganizationDao defines an interface that provides CRUD 
* capability for the Organization domain object to an underlying database
* or any other persistence storage mechanism.
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-07-01
*/
public interface OrganizationDao {
	/**
	 * This method reads the specified Organization id record from the 
	 * underlying storage mechanism and returns an Organization object.
	 * 
	 * @param   id  the unique id of the organization to return.
	 * @return      the Organization object
	 * 
	 * @throws  DaoException if an underlying storage mechanism Exception
	 *          was encountered during processing
	*/
    public Organization getOrganization(int id) throws DaoException;
    
    /**
     * This method reads all the Organization records from the underlying storage 
     * mechanism and returns a list of Organization objects.
     * 
     * @return      List of Organization objects
     * 
     * @throws   DaoException if an underlying storage mechanism Exception was
     *           encountered during processing 
    */
    public List<Organization> getOrganizations() throws DaoException;
     
    /**
     * This method adds the specified Organization record to the underlying 
     * storage mechanism and returns the unique id of the Organization added.
     * 
     * @param    organization  the Organization object
     * @return                the unique id of the Organization record added
     * 
     * @throws   DaoException if an underlying storage mechanism Exception was
     *           encountered during processing
    */
    public int addOrganization(Organization organization) throws DaoException;
    
    /**
     * This method updates the specified Organization record in the underlying 
     * storage mechanism.
     * 
     * @param    organization  the Organization object
     * 
     * @throws   DaoException if an underlying storage mechanism Exception
     *           was encountered during processing
    */
    public void updateOrganization(Organization organization) throws DaoException;
    
    /**
     * This method deletes the specified Organization id from the underlying storage
     * mechanism.
     * 
     * @param   id  the unique id of the Organization to delete.
     *  
     * @throws  DaoException if an underlying storage mechanism Exception was
     *          encountered during processing
    */
    public void deleteOrganization(int id) throws DaoException;
}