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

import com.emptytomb.dbmanager.service.ScriptureService;
import com.emptytomb.dbmanager.service.ServiceException;

/**
 * The ScriptureResource class implements the CRUD REST end points for the Scripture resource.
 * 
 * @author  Jim Zombek
 * @version 1.0
 * @since   2016-07-01
 */
@Path("/scripture")
public class ScriptureResource {
	/**
	 * This method returns all Scriptures.
	 * 
	 * @return      JSON representation of all Scriptures
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getScriptures() throws WebException {
		String result = null;
		try {
			result = ScriptureService.getInstance().getScriptures();
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method returns a Scripture.
	 * 
	 * @param   id  the unique id of the scripture to return.
	 * @return      JSON representation of Scripture
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getScripture(@PathParam("id") final int id) throws WebException {
		String result = null;
		try {
			result = ScriptureService.getInstance().getScripture(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method adds a new Scripture
	 * 
	 * @param   scripture JSON representation of scripture to add
	 * @return               JSON representation of scripture id added
	 * 
	 * <pre>{@code
     * scriptureId : Integer;
     * }</pre>
     * 
	 * @throws      WebException if an exception was encountered during processing
	 */	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addScripture(String scripture) throws WebException {
		String result = null;
		try {
			result = ScriptureService.getInstance().addScripture(scripture);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method updates an existing Scripture
	 * 
	 * @param   scripture JSON representation of scripture to update
	 * @return               JSON representation of scripture id updated
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateScripture(String scripture) throws WebException {
		String result = null;
		try {
			result = ScriptureService.getInstance().updateScripture(scripture);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method deletes an existing Scripture
	 * 
	 * @param   id  the unique id of the scripture to delete.
	 * @return      JSON representation of scripture id deleted
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */		
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteScripture(@PathParam("id") final int id) throws WebException {
		String result = null;
		try {
			result = ScriptureService.getInstance().deleteScripture(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
}