package com.emptytomb.dbmanager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emptytomb.dbmanager.dao.DaoException;
import com.emptytomb.dbmanager.dao.CategoryDao;
import com.emptytomb.dbmanager.domain.Category;
import com.google.gson.Gson;

/**
* The CategoryService class implements the CRUD service layer for the Category resource.
* 
* <p><b>Note:</b> This class is implemented as a Singleton. Only one instance of this class
* can exist at a time.</p>
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-08-01
*/
public class CategoryService {
  private Logger logger = LoggerFactory.getLogger(CategoryService.class);
  private static CategoryService instance = null;
  private static CategoryDao categoryDAO = null;
  private static Gson gson = new Gson();
   
  private CategoryService() {
      categoryDAO = CategoryDao.getInstance();
  }

  /**
   * This method returns the single instance of the CategoryService object.
   * 
   * @return  the CategoryService object
   * 
  */
  public static CategoryService getInstance() {
      if (instance == null) {      
          synchronized (CategoryService.class) {
              if (instance == null) {
                  instance = new CategoryService();
              } 
          }
      }
      return instance;
  }
  
  /**
   * This method returns the JSON representation of the Category object associated with the
   * specified category identifier.
   * 
   * @param   id  the unique id of the category to return.
   * @return      the JSON representation of the category object
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String getCategory(int id) throws ServiceException {
	  String result = null;
	  try {
	      Category category = categoryDAO.get(id);
	      result = gson.toJson(category);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": getCategory() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, e.getReason());
	  }
      return result;
  }
  
  /**
   * This method returns the JSON representation of all the Category objects.
   * 
   * @return      the JSON representation of all the Category objects
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String getCategories() throws ServiceException {
	  String result = null;
	  try {
		  List<Category> categories = categoryDAO.list();
	      result = gson.toJson(categories);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": getCategories() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, e.getReason());
	  }
      return result;
  }
  
  /**
   * This method adds the Category object.
   * 
   * @param   categoryJson the JSON representation of the Category object.
   * @return      categoryId of Category added if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String addCategory(String categoryJson) throws ServiceException {
	  String result = null;
	  try {
		  Category category = gson.fromJson(categoryJson, Category.class);
		  int categoryId = categoryDAO.add(category);
	      result = gson.toJson("categoryId : " + categoryId);
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": addCategory() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
  
  /**
   * This method updates the Category object.
   * 
   * @param   categoryJson the JSON representation of the Category object.
   * @return      SUCCESS:200 if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String updateCategory(String categoryJson) throws ServiceException {
	  String result = null;
	  try {
		  Category category = gson.fromJson(categoryJson, Category.class);
		  categoryDAO.update(category);
	      result = gson.toJson("SUCCESS : 200");
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": updateCategory() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
  
  /**
   * This method deletes the Category object associated with the specified 
   * category identifier.
   * 
   * @param   id  category id to delete.
   * @return      SUCCESS:200 if successful.
   * 
   * @throws      ServiceException if an error was encountered during processing
  */
  public String deleteCategory(int id) throws ServiceException {
	  String result = null;
	  try {
		  categoryDAO.delete(id);
	      result = gson.toJson("SUCCESS : 200");
	  } catch (DaoException e) {
		  String errorMessage = this.getClass().getName() + ": deleteCategory() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return result;
  }
}