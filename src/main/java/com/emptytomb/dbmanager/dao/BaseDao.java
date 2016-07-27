package com.emptytomb.dbmanager.dao;

import java.util.List;

/**
* The BaseDao defines an interface that provides CRUD capability for the <T> domain
* object to an underlying database or any other persistence storage mechanism.
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-07-01
*/
public interface BaseDao<T> {
	/**
	 * This method reads the specified <T> record id from the underlying storage
	 * mechanism and returns a <T> object.
	 * 
	 * @param   id  the unique id of the <T> to return.
	 * @return      the <T> object
	 * 
	 * @throws  DaoException if an underlying storage mechanism Exception
	 *          was encountered during processing
	*/
    public T get(int id) throws DaoException;
    
    /**
     * This method reads all the <T> records from the underlying storage 
     * mechanism and returns a list of <T> objects.
     * 
     * @return      List of <T> objects
     * 
     * @throws   DaoException if an underlying storage mechanism Exception was
     *           encountered during processing 
    */    
    public List<T> list() throws DaoException;
    
    /**
     * This method adds the specified <T> record to the underlying storage mechanism
     * and returns the unique id of the <T> added.
     * 
     * @param    commentary   the <T> object
     * @return                the unique id of the <T> record added
     * 
     * @throws   DaoException if an underlying storage mechanism Exception was
     *           encountered during processing
    */    
    public int add(T t) throws DaoException;
    
    /**
     * This method updates the specified <T> record in the underlying storage mechanism.
     * 
     * @param    <T>  the <T> object
     * 
     * @throws   DaoException if an underlying storage mechanism Exception
     *           was encountered during processing
    */    
    public void update(T t) throws DaoException;
    
    /**
     * This method deletes the specified <T> id from the underlying storage mechanism.
     * 
     * @param   id  the unique id of the <T> to delete.
     *  
     * @throws  DaoException if an underlying storage mechanism Exception was
     *          encountered during processing
    */    
    public void delete(int id) throws DaoException;
}