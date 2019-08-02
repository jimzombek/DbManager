package com.emptytomb.dbmanager.domain;

import java.io.Serializable;

/**
* The Bible class is a simple POJO containing the getter/setter methods
* for the Bible domain model.
* 
* @author  Jim Zombek
* @version 1.0
* @since   2019-08-02
*/
public class Bible implements Serializable {
  public enum Language {
    ENGLISH,
    SPANISH
  }
  private static final long serialVersionUID = 1L;
  private int       bibleId;
  private String    publisher;
  private timestamp datePublished;
  private String    name;      // King James Version
  private String    shortName; // KJV
  private Language  language;
  private String    history;
   
  public Bible() {
  }

  /**
   * @return bible ID value associated with Bible
  */
  public int getBibleId() {
	return bibleId;
  }

  /**
   * @param bibleId bible ID value to set
  */
  public void setBibleId(int bibleId) {
	this.bibleId = bibleId;
  }
  
  /**
   * @return publisher associated with Bible
  */
  public String getPublisher() {
	return publisher;
  }

  /**
   * @param publisher publisher of the bible to set
  */
  public void setPublisher(String publisher) {
	this.publisher = publisher;
  }
	
  /**
   * @return publisher date associated with Bible
  */
  public timestamp getDatePublished() {
	return publishedDate;
  }

  /**
   * @param publisheed date date bible was published to set
  */
  public void setDatePublished(timestamp publishedDate) {
	this.publishedDate = publishedDate;
  }
  
  /**
   * @return name of the bible associated with Bible
  */
  public String getName() {
	return name;
  }

  /**
   * @param name name of the bible to set
  */
  public void setName(String name) {
	this.name = name;
  }
  
  /**
   * @return short name of the Bible 
  */
  public String getShortName() {
	return shortName;
  }

  /**
   * @param short name short name of the Bible to set
  */
  public void setShortName(String shortName) {
	this.shortName = shortName;
  }

  /**
   * @return description value associated with Bible
  */
  public String getHistory() {
	return history;
  }

  /**
   * @param description description value of the bible to set
  */
  public void setHistory(String history) {
	this.history = history;
  }
}
