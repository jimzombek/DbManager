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

import com.emptytomb.dbmanager.service.OrganizationService;
import com.emptytomb.dbmanager.service.ServiceException;

/**
 * The OrganizationResource class implements the CRUD REST end points for the Organization resource.
 * 
 * @author  Jim Zombek
 * @version 1.0
 * @since   2016-05-31
 */
@Path("/organization")
public class OrganizationResource {
	/**
	 * This method returns all Organizations.
	 * 
	 * @return      JSON representation of all Organizations
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getOrganizations() throws WebException {
		String result = null;
		try {
			result = OrganizationService.getInstance().getOrganizations();
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method returns an Organization.
	 * 
	 * @param   id  the unique id of the organization to return.
	 * @return      JSON representation of Organization
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getOrganization(@PathParam("id") final int id) throws WebException {
		String result = null;
		try {
			result = OrganizationService.getInstance().getOrganization(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method adds a new Organization
	 * 
	 * @param   organization JSON representation of organization to add
	 * @return               JSON representation of organization id added
	 * 
	 * <pre>{@code
     * organizationId : Integer;
     * }</pre>
     * 
	 * @throws      WebException if an exception was encountered during processing
	 */	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addOrganization(String organization) throws WebException {
		String result = null;
		try {
			result = OrganizationService.getInstance().addOrganization(organization);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method updates an existing Organization
	 * 
	 * @param   organization JSON representation of organization to update
	 * @return               JSON representation of organization id updated
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateOrganization(String organization) throws WebException {
		String result = null;
		try {
			result = OrganizationService.getInstance().updateOrganization(organization);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method deletes an existing Organization
	 * 
	 * @param   id  the unique id of the organization to delete.
	 * @return      JSON representation of organization id deleted
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */		
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteOrganization(@PathParam("id") final int id) throws WebException {
		String result = null;
		try {
			result = OrganizationService.getInstance().deleteOrganization(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
}