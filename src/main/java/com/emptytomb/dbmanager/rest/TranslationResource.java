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

import com.emptytomb.dbmanager.service.TranslationService;
import com.emptytomb.dbmanager.service.ServiceException;

/**
 * The TranslationResource class implements the CRUD REST end points for the Translation resource.
 * 
 * @author  Jim Zombek
 * @version 1.0
 * @since   2016-07-01
 */
@Path("/translation")
public class TranslationResource {
	/**
	 * This method returns all Translations.
	 * 
	 * @return      JSON representation of all Translations
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getTranslations() throws WebException {
		String result = null;
		try {
			result = TranslationService.getInstance().getTranslations();
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method returns a Translation.
	 * 
	 * @param   id  the unique id of the translation to return.
	 * @return      JSON representation of Translation
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTranslation(@PathParam("id") final int id) throws WebException {
		String result = null;
		try {
			result = TranslationService.getInstance().getTranslation(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method adds a new Translation
	 * 
	 * @param   organization JSON representation of translation to add
	 * @return               JSON representation of translation id added
	 * 
	 * <pre>{@code
     * translationId : Integer;
     * }</pre>
     * 
	 * @throws      WebException if an exception was encountered during processing
	 */	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addTranslation(String translation) throws WebException {
		String result = null;
		try {
			result = TranslationService.getInstance().addTranslation(translation);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method updates an existing Translation
	 * 
	 * @param   organization JSON representation of translation to update
	 * @return               JSON representation of translation id updated
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateTranslation(String translation) throws WebException {
		String result = null;
		try {
			result = TranslationService.getInstance().updateTranslation(translation);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
	
	/**
	 * This method deletes an existing Translation
	 * 
	 * @param   id  the unique id of the translation to delete.
	 * @return      JSON representation of translation id deleted
	 * 
	 * @throws      WebException if an exception was encountered during processing
	 */		
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteTranslation(@PathParam("id") final int id) throws WebException {
		String result = null;
		try {
			result = TranslationService.getInstance().deleteTranslation(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return result;
	}
}