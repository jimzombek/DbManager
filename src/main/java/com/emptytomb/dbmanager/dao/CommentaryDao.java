package com.emptytomb.dbmanager.dao;

import java.util.List;
import com.emptytomb.dbmanager.domain.Commentary;

/**
* The CommentaryDao defines an interface that provides CRUD 
* capability for the Commentary domain object to an underlying database
* or any other persistence storage mechanism.
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-07-01
*/
public interface CommentaryDao {
	/**
	 * This method reads the specified Commentary id record from the 
	 * underlying storage mechanism and returns a Commentary object.
	 * 
	 * @param   id  the unique id of the commentary to return.
	 * @return      the Commentary object
	 * 
	 * @throws  DaoException if an underlying storage mechanism Exception
	 *          was encountered during processing
	*/
    public Commentary getCommentary(int id) throws DaoException;
    
    /**
     * This method reads all the Commentary records from the underlying storage 
     * mechanism and returns a list of Organization objects.
     * 
     * @return      List of Commentary objects
     * 
     * @throws   DaoException if an underlying storage mechanism Exception was
     *           encountered during processing 
    */
    public List<Commentary> getCommentaries() throws DaoException;
     
    /**
     * This method adds the specified Commentary record to the underlying 
     * storage mechanism and returns the unique id of the Organization added.
     * 
     * @param    commentary   the Commentary object
     * @return                the unique id of the Commentary record added
     * 
     * @throws   DaoException if an underlying storage mechanism Exception was
     *           encountered during processing
    */
    public int addCommentary(Commentary commentary) throws DaoException;
    
    /**
     * This method updates the specified Commentary record in the underlying 
     * storage mechanism.
     * 
     * @param    commentary  the Commentary object
     * 
     * @throws   DaoException if an underlying storage mechanism Exception
     *           was encountered during processing
    */
    public void updateCommentary(Commentary commentary) throws DaoException;
    
    /**
     * This method deletes the specified Commentary id from the underlying storage
     * mechanism.
     * 
     * @param   id  the unique id of the Commentary to delete.
     *  
     * @throws  DaoException if an underlying storage mechanism Exception was
     *          encountered during processing
    */
    public void deleteCommentary(int id) throws DaoException;
}