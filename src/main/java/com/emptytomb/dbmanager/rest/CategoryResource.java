package com.emptytomb.dbmanager.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.emptytomb.dbmanager.service.CategoryService;
import com.emptytomb.dbmanager.service.ServiceException;

/**
 * The CategoryResource class implements the CRUD REST end points for the Category resource.
 * 
 * @author  Jim Zombek
 * @version 1.0
 * @since   2016-08-01
 */
@Path("/category")
public class CategoryResource {
	/**
	 * This method returns all Categories.
	 * 
	 * @return      JSON representation of all Categories
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getCategories() throws WebException {
		String result = null;
		try {
			result = CategoryService.getInstance().getCategories();
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method returns a Category.
	 * 
	 * @param   id  the unique id of the category to return.
	 * @return      JSON representation of Category
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCategory(@PathParam("id") final int id) throws WebException {
		String result = null;
		try {
			result = CategoryService.getInstance().getCategory(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method adds a new Category
	 * 
	 * @param   category JSON representation of category to add
	 * @return           JSON representation of category id added
	 * 
	 * <pre>{@code
     * categoryId : Integer;
     * }</pre>
     * 
	 * @throws      WebException if an exception was encountered during processing
	 */	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addCategory(String category) throws WebException {
		String result = null;
		try {
			result = CategoryService.getInstance().addCategory(category);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method updates an existing Category
	 * 
	 * @param   category JSON representation of category to update
	 * @return           JSON representation of category id updated
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCategory(String category) throws WebException {
		String result = null;
		try {
			result = CategoryService.getInstance().updateCategory(category);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method deletes an existing Category
	 * 
	 * @param   id  the unique id of the category to delete.
	 * @return      JSON representation of category id deleted
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */		
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteCategory(@PathParam("id") final int id) throws WebException {
		String result = null;
		try {
			result = CategoryService.getInstance().deleteCategory(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
}