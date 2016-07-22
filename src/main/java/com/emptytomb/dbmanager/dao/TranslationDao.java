package com.emptytomb.dbmanager.dao;

import java.util.List;
import com.emptytomb.dbmanager.domain.Translation;

/**
* The TranslationDao defines an interface that provides CRUD 
* capability for the Translation domain object to an underlying database
* or any other persistence storage mechanism.
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-07-01
*/
public interface TranslationDao {
	/**
	 * This method reads the specified Translation id record from the 
	 * underlying storage mechanism and returns a Translation object.
	 * 
	 * @param   id  the unique id of the translation to return.
	 * @return      the Translation object
	 * 
	 * @throws  DaoException if an underlying storage mechanism Exception
	 *          was encountered during processing
	*/
    public Translation getTranslation(int id) throws DaoException;
    
    /**
     * This method reads all the Translation records from the underlying storage 
     * mechanism and returns a list of Translation objects.
     * 
     * @return      List of Translation objects
     * 
     * @throws   DaoException if an underlying storage mechanism Exception was
     *           encountered during processing 
    */
    public List<Translation> getTranslations() throws DaoException;
     
    /**
     * This method adds the specified Translation record to the underlying 
     * storage mechanism and returns the unique id of the Translation added.
     * 
     * @param    translation  the Translation object
     * @return                the unique id of the Translation record added
     * 
     * @throws   DaoException if an underlying storage mechanism Exception was
     *           encountered during processing
    */
    public int addTranslation(Translation translation) throws DaoException;
    
    /**
     * This method updates the specified Translation record in the underlying 
     * storage mechanism.
     * 
     * @param    translation  the Translation object
     * 
     * @throws   DaoException if an underlying storage mechanism Exception
     *           was encountered during processing
    */
    public void updateTranslation(Translation translation) throws DaoException;
    
    /**
     * This method deletes the specified Translation id from the underlying storage
     * mechanism.
     * 
     * @param   id  the unique id of the Translation to delete.
     *  
     * @throws  DaoException if an underlying storage mechanism Exception was
     *          encountered during processing
    */
    public void deleteTranslation(int id) throws DaoException;
}