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

import com.emptytomb.dbmanager.dao.DaoException;
import com.emptytomb.dbmanager.service.PersonalityService;
import com.emptytomb.dbmanager.service.ServiceException;

/**
 * The PersonalityResource class implements the CRUD REST end points for the Personality resource.
 * 
 * @author  Jim Zombek
 * @version 1.0
 * @since   2016-05-31
 */
@Path("/personality")
public class PersonalityResource {
	/**
	 * This method gets all the persisted Personalities.
	 * 
	 * @return      JSON representation of all Personalities
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getPersonalities() throws WebException {
		String result = null;
		try {
			result = PersonalityService.getInstance().getPersonalities();
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method reads the specified personality id record from the personality
	 * table stored in MySQL and returns a Personality object.
	 * 
	 * @param   id  the unique id of the personality to return.
	 * @return      JSON representation of Personality
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPersonality(@PathParam("id") final int id) throws WebException {
		String result = null;
		try {
			result = PersonalityService.getInstance().getPersonality(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method adds a new personality
	 * 
	 * @param   personality  JSON representation of personality to add
	 * @return               JSON representation of personality id added
	 * 
	 * <pre>{@code
     * personalityId : Integer;
     * }</pre>
     * 
	 * @throws      WebException if an exception was encountered during processing
	 */	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addPersonality(String personality) throws WebException {
		String result = null;
		try {
			result = PersonalityService.getInstance().addPersonality(personality);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method updates an existing personality
	 * 
	 * @param   personality  JSON representation of personality to update
	 * @return               JSON representation of personality id updated
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updatePersonality(String personality) throws WebException {
		String result = null;
		try {
			result = PersonalityService.getInstance().updatePersonality(personality);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method deletes an existing personality
	 * 
	 * @param   id  the unique id of the personality to delete.
	 * @return      JSON representation of personality id deleted
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */		
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deletePersonaality(@PathParam("id") final int id) throws WebException {
		String result = null;
		try {
			result = PersonalityService.getInstance().deletePersonality(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
}