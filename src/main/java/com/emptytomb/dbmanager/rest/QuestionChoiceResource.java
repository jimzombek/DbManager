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

import com.emptytomb.dbmanager.service.QuestionChoiceService;
import com.emptytomb.dbmanager.service.ServiceException;

/**
 * The QuestionChoiceResource class implements the CRUD REST end points for the QuestionChoice resource.
 * 
 * @author  Jim Zombek
 * @version 1.0
 * @since   2016-08-01
 */
@Path("/question-choice")
public class QuestionChoiceResource {
	/**
	 * This method returns all Question Choices.
	 * 
	 * @return      JSON representation of all Question Choices
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getQuestionChoices() throws WebException {
		String result = null;
		try {
			result = QuestionChoiceService.getInstance().getQuestionChoices();
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method returns a QuestionChoice.
	 * 
	 * @param   id  the unique id of the question choice to return.
	 * @return      JSON representation of QuestionChoice
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getQuestionChoice(@PathParam("id") final int id) throws WebException {
		String result = null;
		try {
			result = QuestionChoiceService.getInstance().getQuestionChoice(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method adds a new Question Choice
	 * 
	 * @param   questionChoice JSON representation of question choice to add
	 * @return               JSON representation of question choice id added
	 * 
	 * <pre>{@code
     * questionChoiceId : Integer;
     * }</pre>
     * 
	 * @throws      WebException if an exception was encountered during processing
	 */	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addQuestionChoice(String questionChoice) throws WebException {
		String result = null;
		try {
			result = QuestionChoiceService.getInstance().addQuestionChoice(questionChoice);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method updates an existing QuestionChoice
	 * 
	 * @param   questionChoice JSON representation of question choice to update
	 * @return               JSON representation of question choice id updated
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateQuestionChoice(String questionChoice) throws WebException {
		String result = null;
		try {
			result = QuestionChoiceService.getInstance().updateQuestionChoice(questionChoice);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method deletes an existing QuestionChoice
	 * 
	 * @param   id  the unique id of the question choice to delete.
	 * @return      JSON representation of question choice id deleted
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */		
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteQuestionChoice(@PathParam("id") final int id) throws WebException {
		String result = null;
		try {
			result = QuestionChoiceService.getInstance().deleteQuestionChoice(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
}