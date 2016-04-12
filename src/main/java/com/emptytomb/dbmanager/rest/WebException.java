package com.emptytomb.dbmanager.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class WebException extends WebApplicationException {
  private static final long serialVersionUID = 1L;
  public static final int RESOURCE_NOT_FOUND = 404;
  public static final int INTERNAL_SERVER_ERROR = 500;
  
  public WebException(int statusCode, String msg) {
	  super(Response.status(statusCode).entity(msg).type(MediaType.TEXT_PLAIN).build());
  }
}