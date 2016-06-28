package com.emptytomb.dbmanager.dao;

public class DaoException extends Exception {
  private static final long serialVersionUID = 1L;
  private Exception exception;
  private String reason;

  public DaoException(Exception exception, String reason) {
	  this.exception = exception;
	  this.reason = reason;
  }

  /**
   * This method returns the Exception object associated with this object.
   * 
   * @return Exception
  */
  public Exception getException() {
	return exception;
  }

  /**
   * @param exception Exception to set
  */
  public void setException(Exception exception) {
	this.exception = exception;
  }

  /**
   * @return reason associated with DAOException
  */
  public String getReason() {
	return reason;
  }

  /**
   * @param reason description of the reason to set
  */
  public void setReason(String reason) {
	this.reason = reason;
  }
}