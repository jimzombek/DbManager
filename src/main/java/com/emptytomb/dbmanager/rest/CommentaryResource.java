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

import com.emptytomb.dbmanager.service.CommentaryService;
import com.emptytomb.dbmanager.service.ServiceException;

/**
 * The CommentaryResource class implements the CRUD REST end points for the Commentary resource.
 * 
 * @author  Jim Zombek
 * @version 1.0
 * @since   2016-07-01
 */
@Path("/commentary")
public class CommentaryResource {
	/**
	 * This method returns all Commentaries.
	 * 
	 * @return      JSON representation of all Commentaries
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getCommentaries() throws WebException {
		String result = null;
		try {
			result = CommentaryService.getInstance().getCommentaries();
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method returns a Commentary.
	 * 
	 * @param   id  the unique id of the commentary to return.
	 * @return      JSON representation of Commentary
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCommentary(@PathParam("id") final int id) throws WebException {
		String result = null;
		try {
			result = CommentaryService.getInstance().getCommentary(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method adds a new Commentary
	 * 
	 * @param   commentary   JSON representation of commentary to add
	 * @return               JSON representation of commentary id added
	 * 
	 * <pre>{@code
     * commentaryId : Integer;
     * }</pre>
     * 
	 * @throws      WebException if an exception was encountered during processing
	 */	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addCommentary(String commentary) throws WebException {
		String result = null;
		try {
			result = CommentaryService.getInstance().addCommentary(commentary);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method updates an existing Commentary
	 * 
	 * @param   commentary   JSON representation of commentary to update
	 * @return               JSON representation of commentary id updated
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCommentary(String commentary) throws WebException {
		String result = null;
		try {
			result = CommentaryService.getInstance().updateCommentary(commentary);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method deletes an existing Commentary
	 * 
	 * @param   id  the unique id of the commentary to delete.
	 * @return      JSON representation of commentary id deleted
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */		
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteCommentary(@PathParam("id") final int id) throws WebException {
		String result = null;
		try {
			result = CommentaryService.getInstance().deleteCommentary(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
}