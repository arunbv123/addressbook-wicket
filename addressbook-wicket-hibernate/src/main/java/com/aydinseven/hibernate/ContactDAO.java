package com.aydinseven.hibernate;

import java.util.List;

import com.aydinseven.hibernate.model.Address;
import com.aydinseven.hibernate.model.Contact;

public interface ContactDAO {

	Contact createContact(String firstName, String lastName, int[] dateOfBirth);
	
	Contact createContact(String firstName, String lastName);
	
	Contact saveContact(Contact contact);
	
	Contact getContact(int id);

	Address getAddress(int id);
	
	List<Contact> getContacts();
	
	Contact updateContact(Contact contact);
	
	Address updateAddress(Address address);
	
	void deleteContact(Contact contact);
	
	void deleteAddress(Address address);
}