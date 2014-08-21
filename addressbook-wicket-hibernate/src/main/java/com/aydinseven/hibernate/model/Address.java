package com.aydinseven.hibernate.model;

import java.io.Serializable;

public class Address implements Serializable {
	
	private int id;
	private String street;
	private int zipcode;
	private String city;
	private String country;
	private boolean isWorkAddress;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public boolean getIsWorkAddress() {
		return isWorkAddress;
	}
	public void setIsWorkAddress(boolean isWorkAddress) {
		this.isWorkAddress = isWorkAddress;
	}

	private Contact contact;
	
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getZipcode() {
		return zipcode;
	}
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public Address() {
		
	}
}