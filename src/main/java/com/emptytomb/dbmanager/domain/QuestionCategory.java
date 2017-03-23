package com.emptytomb.dbmanager.domain;

import java.io.Serializable;

/**
* The QuestionCategory class is a simple POJO containing the getter/setter methods
* for the QuestionCategory domain model.
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-08-01
*/
public class QuestionCategory implements Serializable {
  private static final long serialVersionUID = 1L;
  private int id;
  private String name; 
  private String dateCreated;
  private String dateUpdated;
  
  public QuestionCategory() {
  }

  /**
   * @return id value associated with Question Category
  */
  public int getId() {
	return id;
  }

  /**
   * @param id id of Question Category to set
  */
  public void setId(int id) {
	this.id = id;
  }
         
  /**
   * @return name associated with Question Category
  */
  public String getName() {
	return name;
  }

  /**
   * @param name name of the Question Category to set
  */
  public void setName(String name) {
	this.name = name;
  }
  
  /**
   * @return dateCreated creation date associated with Question Category
  */
  public String getDateCreated() {
		return dateCreated;
  }
  
  /**
   * @param dateCreated creation date of the Question Category to set
  */
  public void setDateCreated(String dateCreated) {
	this.dateCreated = dateCreated;
  }
  
  /**
   * @return dateUpdated update date associated with Question Category
  */
  public String getDateUpdated() {
	return dateUpdated;
  }
  
  /**
   * @param dateUpdated update date of the Question Category to set
  */
  public void setDateUpdated(String dateUpdated) {
	this.dateUpdated = dateUpdated;
  }

}
