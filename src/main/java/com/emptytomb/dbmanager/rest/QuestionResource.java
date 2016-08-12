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

import com.emptytomb.dbmanager.service.QuestionService;
import com.emptytomb.dbmanager.service.ServiceException;

/**
 * The QuestionResource class implements the CRUD REST end points for the Question resource.
 * 
 * @author  Jim Zombek
 * @version 1.0
 * @since   2016-08-01
 */
@Path("/question")
public class QuestionResource {
	/**
	 * This method returns all Questions.
	 * 
	 * @return      JSON representation of all Questions
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getQuestions() throws WebException {
		String result = null;
		try {
			result = QuestionService.getInstance().getQuestions();
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method returns a Question.
	 * 
	 * @param   id  the unique id of the question to return.
	 * @return      JSON representation of Question
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getQuestion(@PathParam("id") final int id) throws WebException {
		String result = null;
		try {
			result = QuestionService.getInstance().getQuestion(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method adds a new Question
	 * 
	 * @param   question JSON representation of question to add
	 * @return               JSON representation of question id added
	 * 
	 * <pre>{@code
     * passageId : Integer;
     * }</pre>
     * 
	 * @throws      WebException if an exception was encountered during processing
	 */	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addQuestion(String scripture) throws WebException {
		String result = null;
		try {
			result = QuestionService.getInstance().addQuestion(scripture);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method updates an existing Question
	 * 
	 * @param   question JSON representation of question to update
	 * @return               JSON representation of question id updated
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateQuestion(String passage) throws WebException {
		String result = null;
		try {
			result = QuestionService.getInstance().updateQuestion(passage);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method deletes an existing Question
	 * 
	 * @param   id  the unique id of the question to delete.
	 * @return      JSON representation of question id deleted
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */		
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteQuestion(@PathParam("id") final int id) throws WebException {
		String result = null;
		try {
			result = QuestionService.getInstance().deleteQuestion(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
}