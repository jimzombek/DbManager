package com.emptytomb.dbmanager.dao;

public class DAOException extends Exception {
  private static final long serialVersionUID = 1L;
  private Exception exception;
  private String reason;

  public DAOException(Exception exception, String reason) {
	  this.exception = exception;
	  this.reason = reason;
  }

  public Exception getException() {
	return exception;
  }

  public void setException(Exception exception) {
	this.exception = exception;
  }

  public String getReason() {
	return reason;
  }

  public void setReason(String reason) {
	this.reason = reason;
  }
}