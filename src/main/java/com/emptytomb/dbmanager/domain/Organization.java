package com.emptytomb.dbmanager.domain;

import java.io.Serializable;

/**
* The Organization class is a simple POJO containing the getter/setter methods
* for the Organization domain model.
* 
* @author  Jim Zombek
* @version 1.0
* @since   2016-05-31
*/
public class Organization implements Serializable {
  private static final long serialVersionUID = 1L; 
  private int    organizationId; 
  private String name; 
  private String shortName;
  private String addressLine1;
  private String addressLine2;
  private String city;
  private String providence;
  private String postalCode;
  private String country;
  private int    yearStarted;
  private String affiliation;
  private String webSite;
  private int    membershipSize;  
 
  public Organization() {
  }
 
  /**
   * @return organization ID value associated with Organization
  */
  public int getOrganizationId() {
	return organizationId;
  }

  /**
   * @param organizationId organization ID value to set
  */
  public void setOrganizationId(int organizationId) {
	this.organizationId = organizationId;
  }

  /**
   * @return name associated with Organization
  */
  public String getName() {
	return name;
  }

  /**
   * @param name name of the organization to set
  */
  public void setName(String name) {
	this.name = name;
  }
	
  /**
   * @return shortName associated with Organization
  */
  public String getShortName() {
	return shortName;
  }

  /**
   * @param shortName short name of the organization to set
  */
  public void setShortName(String shortName) {
	this.shortName = shortName;
  }

  /**
   * @return address line 1 associated with Organization
  */
  public String getAddressLine1() {
	return addressLine1;
  }

  /**
   * @param addressLine1 address line 1 of the organization to set
  */
  public void setAddressLine1(String addressLine1) {
	this.addressLine1 = addressLine1;
  }

  /**
   * @return address line 2 associated with Organization
  */
  public String getAddressLine2() {
	return addressLine2;
  }

  /**
   * @param addressLine2 address line 2 of the organization to set
  */
  public void setAddressLine2(String addressLine2) {
	this.addressLine2 = addressLine2;
  }

  /**
   * @return city associated with Organization
  */
  public String getCity() {
	return city;
  }

  /**
   * @param city city name of the organization to set
  */
  public void setCity(String city) {
	this.city = city;
  }

  /**
   * @return providence name associated with Organization
  */
  public String getProvidence() {
	return providence;
  }

  /**
   * @param providence providence name of the organization to set
  */
  public void setProvidence(String providence) {
	this.providence = providence;
  }

  /**
   * @return postal code associated with Organization
  */
  public String getPostalCode() {
	return postalCode;
  }

  /**
   * @param postalCode postal code of the organization to set
  */
  public void setPostalCode(String postalCode) {
	this.postalCode = postalCode;
  }

  /**
   * @return country name associated with Organization
  */
  public String getCountry() {
	return country;
  }

  /**
   * @param country country name of the organization to set
  */
  public void setCountry(String country) {
	this.country = country;
  }

  /**
   * @return year organization started
  */
  public int getYearStarted() {
	return yearStarted;
  }

  /**
   * @param yearStarted organization's year started to set
  */
  public void setYearStarted(int yearStarted) {
	this.yearStarted = yearStarted;
  }

  /**
   * @return affiliation name associated with Organization
  */
  public String getAffiliation() {
	return affiliation;
  }

  /**
   * @param affiliation affiliation name of the organization to set
  */
  public void setAffiliation(String affiliation) {
	this.affiliation = affiliation;
  }

  /**
   * @return web site URL associated with Organization
  */
  public String getWebSite() {
	return webSite;
  }

  /**
   * @param webSite URL of the organization's webs site to set
  */
  public void setWebSite(String webSite) {
	this.webSite = webSite;
  }

  /**
   * @return membership size of the organization
  */
  public int getMembershipSize() {
	return membershipSize;
  }

  /**
   * @param membershipSize size of the organization to set
  */
  public void setMembershipSize(int membershipSize) {
	this.membershipSize = membershipSize;
  }
}
