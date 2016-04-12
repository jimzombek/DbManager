package com.emptytomb.dbmanager.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.emptytomb.dbmanager.domain.Pastor;
import com.emptytomb.dbmanager.service.PastorService;
import com.emptytomb.dbmanager.service.ServiceException;

@Path("/pastor")
public class PastorServlet {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getPastors() throws WebException {
		String pastorsJson = null;
		try {
			pastorsJson = PastorService.getInstance().getPastors();
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return pastorsJson;
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPastor(@PathParam("id") final int id) throws WebException {
		String pastorJson = null;
		try {
			pastorJson = PastorService.getInstance().getPastor(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return pastorJson;
	}
	
	// investigate post vs put
	@POST
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String addPastor(@PathParam("id") final int id) throws WebException {
		String pastorJson = null;
		try {
			Pastor pastor = new Pastor();
			pastorJson = PastorService.getInstance().addPastor(pastor);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return pastorJson;
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String updatePastor(@PathParam("id") final int id) throws WebException {
		String pastorJson = null;
		try {
			Pastor pastor = new Pastor();
			pastorJson = PastorService.getInstance().updatePastor(pastor);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return pastorJson;
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deletePastor(@PathParam("id") final int id) throws WebException {
		String pastorJson = null;
		try {
			pastorJson = PastorService.getInstance().deletePastor(id);
		} catch (ServiceException e) {
			throw new WebException(WebException.INTERNAL_SERVER_ERROR, e.getReason());
		}
		return pastorJson;
	}
}