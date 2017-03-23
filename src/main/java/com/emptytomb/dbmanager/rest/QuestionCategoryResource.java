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

import com.emptytomb.dbmanager.service.QuestionCategoryService;
import com.emptytomb.dbmanager.service.ServiceException;

/**
 * The QuestionCategoryResource class implements the CRUD REST end points for the 
 * QuestionCategory resource.
 * 
 * @author  Jim Zombek
 * @version 1.0
 * @since   2016-08-01
 */
@Path("/questioncategory")
public class QuestionCategoryResource {
	/**
	 * This method returns all Question Categories.
	 * 
	 * @return      JSON representation of all Question Categories
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getQuestionCategories() throws WebException {
		String result = null;
		try {
			result = QuestionCategoryService.getInstance().list();
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method returns a Question Category.
	 * 
	 * @param   id  the unique id of the question category to return.
	 * @return      JSON representation of Category
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getQuestionCategory(@PathParam("id") final int id) throws WebException {
		String result = null;
		try {
			result = QuestionCategoryService.getInstance().get(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method adds a new Question Category
	 * 
	 * @param   category JSON representation of question category to add
	 * @return           JSON representation of question category id added
	 * 
	 * <pre>{@code
     * questionCategoryId : Integer;
     * }</pre>
     * 
	 * @throws      WebException if an exception was encountered during processing
	 */	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addQuestionCategory(String category) throws WebException {
		String result = null;
		try {
			result = QuestionCategoryService.getInstance().add(category);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method updates an existing Question Category
	 * 
	 * @param   category JSON representation of question category to update
	 * @return           JSON representation of question category id updated
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateQuestionCategory(String questionCategory) throws WebException {
		String result = null;
		try {
			result = QuestionCategoryService.getInstance().update(questionCategory);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method deletes an existing Question Category
	 * 
	 * @param   id  the unique id of the question category to delete.
	 * @return      JSON representation of question category id deleted
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */		
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteQuestionCategory(@PathParam("id") final int id) throws WebException {
		String result = null;
		try {
			result = QuestionCategoryService.getInstance().delete(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
}