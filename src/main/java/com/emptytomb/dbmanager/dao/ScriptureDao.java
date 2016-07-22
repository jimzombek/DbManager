package com.emptytomb.dbmanager.dao;

import java.util.List;
import com.emptytomb.dbmanager.domain.Scripture;

/**
* The ScriptureDao defines an interface that provides CRUD 
* capability for the Scripture domain object to an underlying database
* or any other persistence storage mechanism.
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-07-01
*/
public interface ScriptureDao {
	/**
	 * This method reads the specified Scripture id record from the 
	 * underlying storage mechanism and returns a Scripture object.
	 * 
	 * @param   id  the unique id of the scripture to return.
	 * @return      the Scripture object
	 * 
	 * @throws  DaoException if an underlying storage mechanism Exception
	 *          was encountered during processing
	*/
    public Scripture getScripture(int id) throws DaoException;
    
    /**
     * This method reads all the Scripture records from the underlying storage 
     * mechanism and returns a list of Scripture objects.
     * 
     * @return      List of Scripture objects
     * 
     * @throws   DaoException if an underlying storage mechanism Exception was
     *           encountered during processing 
    */
    public List<Scripture> getScripture() throws DaoException;
     
    /**
     * This method adds the specified Scripture record to the underlying 
     * storage mechanism and returns the unique id of the Scripture added.
     * 
     * @param    scripture  the Scripture object
     * @return                the unique id of the Scripture record added
     * 
     * @throws   DaoException if an underlying storage mechanism Exception was
     *           encountered during processing
    */
    public int addScripture(Scripture scripture) throws DaoException;
    
    /**
     * This method updates the specified Scripture record in the underlying 
     * storage mechanism.
     * 
     * @param    scripture  the Scripture object
     * 
     * @throws   DaoException if an underlying storage mechanism Exception
     *           was encountered during processing
    */
    public void updateScripture(Scripture scripture) throws DaoException;
    
    /**
     * This method deletes the specified Scripture id from the underlying storage
     * mechanism.
     * 
     * @param   id  the unique id of the Scripture to delete.
     *  
     * @throws  DaoException if an underlying storage mechanism Exception was
     *          encountered during processing
    */
    public void deleteScripture(int id) throws DaoException;
}