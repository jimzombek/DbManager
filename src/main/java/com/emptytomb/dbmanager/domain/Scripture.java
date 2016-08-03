package com.emptytomb.dbmanager.domain;

import java.io.Serializable;

/**
* The Scripture class is a simple POJO containing the getter/setter methods
* for the Scripture domain model.
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-05-31
*/
public class Scripture implements Serializable {
  private static final long serialVersionUID = 1L;
  private int scriptureId;
  private String testament;
  private String book;
  private String author;
  private int chapter;
  private int verse;
  private boolean prophecy;
      
  public Scripture() {
  }

  /**
   * @return scripture ID value associated with Scripture
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
   * @return testament value associated with Scripture
  */
  public String getTestament() {
	return testament;
  }

  /**
   * @param testament testament value to set, valid values are OLD or NEW
  */
  public void setTestament(String testament) {
	this.testament = testament;
  }

  /**
   * @return the name of the book associated with Scripture
  */
  public String getBook() {
	return book;
  }

  /**
   * @param book name of the book to set
  */
  public void setBook(String book) {
	this.book = book;
  }
  
  /**
   * @return the author of the book associated with Scripture
  */
  public String getAuthor() {
	return author;
  }

  /**
   * @param author name of the book to set
  */
  public void setAuthor(String author) {
	this.author = author;
  }

  /**
   * @return chapter value associated with Scripture
  */
  public int getChapter() {
	return chapter;
  }

  /**
   * @param chapter chapter value to set
  */
  public void setChapter(int chapter) {
	this.chapter = chapter;
  }

  /**
   * @return verse value associated with Scripture
  */
  public int getVerse() {
	return verse;
  }

  /**
   * @param verse verse value to set
  */
  public void setVerse(int verse) {
	this.verse = verse;
  }
 
  /**
   * @return prophecy value associated with Scripture. Returns TRUE if the scripture 
   * fulfills prophecy, otherwise FALSE
  */
  public boolean isProphecy() {
	return prophecy;
  }

  /**
   * @param prophecy prophecy value to set. TRUE if the scripture fulfills prophecy, 
   * otherwise FALSE
  */
  public void setProphecy(boolean prophecy) {
	this.prophecy = prophecy;
  }

}
