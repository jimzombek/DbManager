package com.emptytomb.dbmanager.domain;

import java.io.Serializable;

/**
* The Passage class is a simple POJO containing the getter/setter methods
* for the Passage domain model.
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-08-01
*/
public class Passage implements Serializable {
  private static final long serialVersionUID = 1L;
  private int passageId; 
  private int translationId; 
  private int scriptureId; 
  private String text;
    
 
  public Passage() {
  }

  /**
   * @return passage ID value associated with Passage
  */
  public int getPassageId() {
	return passageId;
  }

  /**
   * @param passageId passage ID value to set
  */
  public void setPassageId(int passageId) {
	this.passageId = passageId;
  }
  
  /**
   * @return translation ID value associated with Passage
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
   * @return scripture ID value associated with Passage
  */
  public int getScriptureId() {
	return scriptureId;
  }

  /**
   * @param scriptureId scripture ID value to set
  */
  public void setScriptureId(int scriptureId) {
	this.scriptureId = scriptureId;
  }

  /**
   * @return text associated with Passage
  */
  public String getText() {
	return text;
  }

  /**
   * @param text text of the passage to set
  */
  public void setText(String text) {
	this.text = text;
  }

}
