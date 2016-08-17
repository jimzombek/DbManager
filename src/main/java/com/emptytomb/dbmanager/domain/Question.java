package com.emptytomb.dbmanager.domain;

import java.io.Serializable;

/**
* The Question class is a simple POJO containing the getter/setter methods
* for the Question domain model.
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-08-01
*/
public class Question implements Serializable {
  private static final long serialVersionUID = 1L;
  private int questionId; 
  private int categoryId; 
  private String text;
  private String type;  // true/false, multiple choice, matching
  private String testament;
  private String difficulty;
  private float sinceVersion;
  
   
  public Question() {
  }
  
  /**
   * @return question ID value associated with Question
  */
  public int getQuestionId() {
	return questionId;
  }

  /**
   * @param questionId question ID value to set
  */
  public void setQuestionId(int questionId) {
	this.questionId = questionId;
  }
   
  /**
   * @return category ID value associated with Question
  */
  public int getCategoryId() {
	return categoryId;
  }

  /**
   * @param categoryId categoryID to set
  */
  public void setCategoryId(int categoryId) {
	this.categoryId = categoryId;
  }
 
  /**
   * @return text associated with Question
  */
  public String getText() {
	return text;
  }

  /**
   * @param text question text to set
  */
  public void setText(String text) {
	this.text = text;
  }
  
  /**
   * @return type value associated with Question
  */
  public String getType() {
	return type;
  }

  /**
   * @param type question type to set
  */
  public void setType(String type) {
	this.type = type;
  }

  /**
   * @return testament value associated with Question
  */
  public String getTestament() {
	return testament;
  }

  /**
   * @param testament testament to set
  */
  public void setTestament(String testament) {
	this.testament = testament;
  }

  /**
   * @return difficulty value associated with Question
  */
  public String getDifficulty() {
	return difficulty;
  }

  /**
   * @param difficulty difficulty to set
  */
  public void setDifficulty(String difficulty) {
	this.difficulty = difficulty;
  }

  /**
   * @return since version value associated with Question
  */
  public float getSinceVersion() {
	return sinceVersion;
  }

  /**
   * @param sinceVersion sinceVersion to set
  */
  public void setSinceVersion(float sinceVersion) {
	this.sinceVersion = sinceVersion;
  }
  
}
