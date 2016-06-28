package com.emptytomb.dbmanager.domain;

import java.io.Serializable;

/**
* The Personality class is a simple POJO containing the getter/setter methods
* for the Personality domain model.
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-05-31
*/
public class Personality implements Serializable {
  private static final long serialVersionUID = 1L;
  private int personalityId;
  private int organizationId;          
  private String name; 
  private String title;
  private String bio;
  private byte[] picture;
 
  /**
   * Constructs a Personality instance.
  */
  public Personality()  {
  }
  
  /**
   * @return personality ID value associated with Personality
  */
  public int getPersonalityId() {
	return personalityId;
  }

  /**
   * @param personalityId personality ID value to set
  */
  public void setPersonalityId(int personalityId) {
	this.personalityId = personalityId;
  }

  /**
   * @return organization ID value associated with Personality
  */
  public int getOrganizationId() {
	return organizationId;
  }

  /**
   * @param organizationId organization ID value to set
  */
  public void setOrganizationId(int organizationId) {
	this.organizationId = organizationId;
  }

  /**
   * @return name value associated with Personality
  */
  public String getName() {
	return name;
  }

  /**
   * @param name name value to set
  */
  public void setName(String name) {
	this.name = name;
  }
  
  /**
   * @return title value associated with Personality
  */
  public String getTitle() {
	return title;
  }

  /**
   * @param title title value to set
  */
  public void setTitle(String title) {
	this.title = title;
  }

  /**
   * @return bio value associated with Personality
  */
  public String getBio() {
	return bio;
  }

  /**
   * @param bio bio value to set
  */
  public void setBio(String bio) {
	this.bio = bio;
  }

  /**
   * @return picture value associated with Personality
  */
  public byte[] getPicture() {
	return picture;
  }

  /**
   * @param picture picture value to set
  */
  public void setPicture(byte[] picture) {
	this.picture = picture;
  }
}