package com.emptytomb.dbmanager.domain;

/**
* The Translation class is a simple POJO containing the getter/setter methods
* for the Translation domain model.
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-05-31
*/
public class Translation {
  private int translationId; 
  private String name;
  private String description;
  private String translation;  // kjv
 
  public Translation() {
  }

  /**
   * @return translation ID value associated with Translation
  */
  public int getTranslationId() {
	return translationId;
  }

  /**
   * @param translationId translation ID value to set
  */
  public void setTranslationId(int translationId) {
	this.translationId = translationId;
  }

  /**
   * @return name of the translation version associated with Translation
  */
  public String getName() {
	return name;
  }

  /**
   * @param name name of the translation version to set
  */
  public void setName(String name) {
	this.name = name;
  }

  /**
   * @return description value associated with Translation
  */
  public String getDescription() {
	return description;
  }

  /**
   * @param description description value of the translation to set
  */
  public void setDescription(String description) {
	this.description = description;
  }

  /**
   * @return name of the translation associated with Translation
  */
  public String getTranslation() {
	return translation;
  }

  /**
   * @param translation name of the translation to set
  */
  public void setTranslation(String translation) {
	this.translation = translation;
  }
}
