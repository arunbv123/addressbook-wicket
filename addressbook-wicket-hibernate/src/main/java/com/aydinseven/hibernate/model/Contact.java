package com.aydinseven.hibernate.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by florian on 17/04/14.
 */
public class Contact implements Serializable {

    private int id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Set<Address> addresses = new HashSet<Address>();
    
    public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

    public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	@SuppressWarnings("unchecked")
	public List<Contact> getAddressesList() {
		return (List<Contact>) new ArrayList(this.getAddresses());
	}
	public int getId() {
        return id;
    }

    public void setId(int contactId) {
        this.id = contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public Contact addAddress(final Address address) {
    	this.addresses.add(address);
    	address.setContact(this);
    	return this;
    }
    
    public Contact removeAddress(final Address address) {
    	this.addresses.remove(address);
    	
    	return this;
    }
}