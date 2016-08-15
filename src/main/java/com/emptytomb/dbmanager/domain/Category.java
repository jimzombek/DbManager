package com.emptytomb.dbmanager.domain;

import java.io.Serializable;

/**
* The Category class is a simple POJO containing the getter/setter methods
* for the Category domain model.
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-08-01
*/
public class Category implements Serializable {
  private static final long serialVersionUID = 1L;
  private int categoryId; 
  private int questionId; 
  private String name; 
    
 
  public Category() {
  }

  /**
   * @return category ID value associated with Category
  */
  public int getCategoryId() {
	return categoryId;
  }

  /**
   * @param categoryId category ID value to set
  */
  public void setCategoryId(int categoryId) {
	this.categoryId = categoryId;
  }
  
  /**
   * @return question ID value associated with Category
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
   * @return name associated with Category
  */
  public String getName() {
	return name;
  }

  /**
   * @param name name of the category to set
  */
  public void setName(String name) {
	this.name = name;
  }

}
