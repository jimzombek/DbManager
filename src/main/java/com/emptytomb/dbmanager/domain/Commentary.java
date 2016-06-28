package com.emptytomb.dbmanager.domain;

import java.io.Serializable;

/**
* The Commentary class is a simple POJO containing the getter/setter methods
* for the Commentary domain model.
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-05-31
*/
public class Commentary implements Serializable {
  private static final long serialVersionUID = 1L;
  private int commentaryId;
  private int personalityId;
  private int scriptureId;
  private String text;
 
  public Commentary() {
  }
  
  /**
   * @return commentary ID value associated with Commentary
  */
  public int getCommentaryId() {
	return commentaryId;
  }

  /**
   * @param commentaryId commentary ID value to set
  */
  public void setCommentaryId(int commentaryId) {
	this.commentaryId = commentaryId;
  }

  /**
   * @return personality ID value associated with Commentary
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
   * @return scripture ID value associated with Commentary
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
   * @return text associated with Commentary
  */
  public String getText() {
	return text;
  }

  /**
   * @param text commentary text to set
  */
  public void setText(String text) {
	this.text = text;
  }
}
