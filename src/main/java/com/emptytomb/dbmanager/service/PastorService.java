package com.emptytomb.dbmanager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emptytomb.dbmanager.dao.DAOException;
import com.emptytomb.dbmanager.dao.PastorDAO;
import com.emptytomb.dbmanager.domain.Pastor;
import com.google.gson.Gson;

public class PastorService {
  private Logger logger = LoggerFactory.getLogger(PastorService.class);
  private static PastorService instance = null;
  private static PastorDAO pastorDAO = null;
  private static Gson gson = new Gson();
   
  private PastorService() {
      pastorDAO = PastorDAO.getInstance();
  }

  public static PastorService getInstance() {
      if (instance == null) {      
          synchronized (PastorService.class) {
              if (instance == null) {
                  instance = new PastorService();
              } 
          }
      }
      return instance;
  }
  
  public String getPastor(int id) throws ServiceException {
	  String pastorJson = null;
	  try {
	      Pastor pastor = pastorDAO.getPastor(id);
	      pastorJson = gson.toJson(pastor);
	  } catch (DAOException e) {
		  throw new ServiceException(e, e.getReason());
	  }
      return pastorJson;
  }
      
  public String getPastors() throws ServiceException {
	  String pastorsJson = null;
	  try {
		  List<Pastor> pastors = pastorDAO.getPastors();
	      pastorsJson = gson.toJson(pastors);
	  } catch (DAOException e) {
		  throw new ServiceException(e, e.getReason());
	  }
      return pastorsJson;
  }
  
  public String addPastor(Pastor pastor) throws ServiceException {
	  String pastorsJson = null;
	  try {
		  pastorDAO.addPastor(pastor);
	      pastorsJson = gson.toJson("SUCCESS : 200");
	  } catch (DAOException e) {
		  String errorMessage = this.getClass().getName() + ": addPastor() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return pastorsJson;
  }
  
  public String updatePastor(Pastor pastor) throws ServiceException {
	  String pastorsJson = null;
	  try {
		  pastorDAO.updatePastor(pastor);
	      pastorsJson = gson.toJson("SUCCESS : 200");
	  } catch (DAOException e) {
		  String errorMessage = this.getClass().getName() + ": updatePastor() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return pastorsJson;
  }
  
  public String deletePastor(int id) throws ServiceException {
	  String pastorsJson = null;
	  try {
		  pastorDAO.deletePastor(id);
	      pastorsJson = gson.toJson("SUCCESS : 200");
	  } catch (DAOException e) {
		  String errorMessage = this.getClass().getName() + ": deleteePastor() - REASON-> " + e.getReason();
  	      logger.error(errorMessage);
		  throw new ServiceException(e, errorMessage);
	  }
      return pastorsJson;
  }
}