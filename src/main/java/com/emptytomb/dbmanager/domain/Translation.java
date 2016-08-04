package com.emptytomb.dbmanager.domain;

import java.io.Serializable;

/**
* The Translation class is a simple POJO containing the getter/setter methods
* for the Translation domain model.
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-05-31
*/
public class Translation implements Serializable {
  private static final long serialVersionUID = 1L;
  private int translationId; 
  private String name;
  private String history;
  private String version;  // kjv
 
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
  public String getHistory() {
	return history;
  }

  /**
   * @param description description value of the translation to set
  */
  public void setHistory(String history) {
	this.history = history;
  }

  /**
   * @return name of the translation version associated with Translation
  */
  public String getVersion() {
	return version;
  }

  /**
   * @param translation version name of the translation to set
  */
  public void setVersion(String version) {
	this.version = version;
  }
}
