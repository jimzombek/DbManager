package com.emptytomb.dbmanager.domain;

import java.io.Serializable;

/**
* The QuestionChoice class is a simple POJO containing the getter/setter methods
* for the QuestionChoice domain model.
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-08-01
*/
public class QuestionChoice implements Serializable {
  private static final long serialVersionUID = 1L;
  private int questionChoiceId; 
  private String text;
  private boolean correctAnswer;
  
   
  public QuestionChoice() {
  }
  
  /**
   * @return question ID value associated with Question Choice
  */
  public int getQuestionChoiceId() {
	return questionChoiceId;
  }

  /**
   * @param questionChoiceId question choice ID value to set
  */
  public void setQuestionChoiceId(int questionChoiceId) {
	this.questionChoiceId = questionChoiceId;
  }
     
  /**
   * @return text associated with Question Choice
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
   * @return correctAnswer value associated with Question Choice
  */
  public boolean isCorrectAnswer() {
	return correctAnswer;
  }

  /**
   * @param correctAnswer correctAnswer to set
  */
  public void setCorrectAnswer(boolean correctAnswer) {
	this.correctAnswer = correctAnswer;
  }
  
}
