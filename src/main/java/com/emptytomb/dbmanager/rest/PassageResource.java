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

import com.emptytomb.dbmanager.service.PassageService;
import com.emptytomb.dbmanager.service.ServiceException;

/**
 * The PassageResource class implements the CRUD REST end points for the Passage resource.
 * 
 * @author  Jim Zombek
 * @version 1.0
 * @since   2016-08-01
 */
@Path("/passage")
public class PassageResource {
	/**
	 * This method returns all Passages.
	 * 
	 * @return      JSON representation of all Passages
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getPassages() throws WebException {
		String result = null;
		try {
			result = PassageService.getInstance().getPassages();
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method returns a Passage.
	 * 
	 * @param   id  the unique id of the passage to return.
	 * @return      JSON representation of Passage
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPassage(@PathParam("id") final int id) throws WebException {
		String result = null;
		try {
			result = PassageService.getInstance().getPassage(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method adds a new Passage
	 * 
	 * @param   passage JSON representation of passage to add
	 * @return               JSON representation of passage id added
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
	public String addPassage(String scripture) throws WebException {
		String result = null;
		try {
			result = PassageService.getInstance().addPassage(scripture);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method updates an existing Passage
	 * 
	 * @param   passage JSON representation of passage to update
	 * @return               JSON representation of passage id updated
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updatePassage(String passage) throws WebException {
		String result = null;
		try {
			result = PassageService.getInstance().updatePassage(passage);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method deletes an existing Passage
	 * 
	 * @param   id  the unique id of the passage to delete.
	 * @return      JSON representation of passage id deleted
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */		
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deletePassage(@PathParam("id") final int id) throws WebException {
		String result = null;
		try {
			result = PassageService.getInstance().deletePassage(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
}