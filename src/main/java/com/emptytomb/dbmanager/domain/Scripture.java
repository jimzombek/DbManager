package com.emptytomb.dbmanager.domain;

/**
* The Scripture class is a simple POJO containing the getter/setter methods
* for the Scripture domain model.
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-05-31
*/
public class Scripture {
  private int scriptureId;
  private int translationId;
  private String testament;
  private String book;
  private int chapter;
  private int verse;
  private String text;
  private boolean prophecy;
  private boolean jesusSpeaking;
     
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
   * @return translation ID value associated with Scripture
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
   * @return text value associated with Scripture
  */
  public String getText() {
	return text;
  }

  /**
   * @param text text value of scripture to set
  */
  public void setText(String text) {
	this.text = text;
  }

  /**
   * @return prophecy value associated with Scripture. TRUE if the scripture fulfills prophecy, otherwise FALSE
  */
  public boolean isProphecy() {
	return prophecy;
  }

  /**
   * @param prophecy prophecy value to set. TRUE if the Jesus is speaking, otherwise FALSE
  */
  public void setProphecy(boolean prophecy) {
	this.prophecy = prophecy;
  }

  /**
   * @return Jesus speaking value associated with Scripture. TRUE if the Jesus is speaking, otherwise FALSE
  */
  public boolean isJesusSpeaking() {
	return jesusSpeaking;
  }

  /**
   * @param jesusSpeaking jesusSpeaking value to set. TRUE if the Jesus is speaking, otherwise FALSE
  */
  public void setJesusSpeaking(boolean jesusSpeaking) {
	this.jesusSpeaking = jesusSpeaking;
  }
}