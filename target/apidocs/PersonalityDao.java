package com.emptytomb.dbmanager.dao;

import java.util.List;
import com.emptytomb.dbmanager.domain.Personality;

/**
* The PersonalityDao defines an interface that provides CRUD 
* capability for the Personality domain object to an underlying database
* or any other persistence storage mechanism.
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-05-31
*/
public interface PersonalityDao {
	/**
	 * This method reads the specified Personality id record from the 
	 * underlying storage mechanism and returns a Personality object.
	 * 
	 * @param   id  the unique id of the personality to return.
	 * @return      the Personality object
	 * 
	 * @throws  DaoException if an underlying  storage mechanism Exception
	 *          was encountered during processing
	*/
    public Personality getPersonality(int id) throws DaoException;
    
    /**
     * This method reads all the Personality records from the underlying storage 
     * mechanism and returns a list of Personality objects.
     * 
     * @return      List of Personality objects
     * 
     * @throws   DaoException if an underlying storage mechanism Exception was
     *           encountered during processing 
    */
    public List<Personality> getPersonalities() throws DaoException;
     
    /**
     * This method adds the specified Personality record to the underlying 
     * storage mechanism and returns the unique id of the Personality added.
     * 
     * @param    personality  the Personality object
     * @return                the unique id of the Personality record added
     * 
     * @throws   DaoException if an underlying storage mechanism Exception was
     *           encountered during processing
    */
    public int addPersonality(Personality personality) throws DaoException;
    
    /**
     * This method updates the specified Personality record in the underlying 
     * storage mechanism.
     * 
     * @param    personality  the Personality object
     * 
     * @throws   DaoException if an underlying storage mechanism Exception
     *           was encountered during processing
    */
    public void updatePersonality(Personality personality) throws DaoException;
    
    /**
     * This method deletes the specified Personality id from the underlying storage
     * mechanism.
     * 
     * @param   id  the unique id of the Personality to delete.
     *  
     * @throws  DaoException if an underlying storage mechanism Exception was
     *          encountered during processing
    */
    public void deletePersonality(int id) throws DaoException;
}