package com.emptytomb.dbmanager.domain;

public class Pastor {
  private int pastorId;
  private int churchId;          
  private String name; 
  private String bio;
  private byte[] picture;
 
  public Pastor() {
  }
  
  public int getPastorId() {
	return pastorId;
  }

  public void setPastorId(int pastorId) {
	this.pastorId = pastorId;
  }

  public int getChurchId() {
	return churchId;
  }

  public void setChurchId(int churchId) {
	this.churchId = churchId;
  }

  public String getName() {
	return name;
  }

  public void setName(String name) {
	this.name = name;
  }

  public String getBio() {
	return bio;
  }

  public void setBio(String bio) {
	this.bio = bio;
  }

  public byte[] getPicture() {
	return picture;
  }

  public void setPicture(byte[] picture) {
	this.picture = picture;
  }
}