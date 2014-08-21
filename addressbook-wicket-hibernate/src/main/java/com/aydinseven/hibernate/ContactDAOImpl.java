package com.aydinseven.hibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.aydinseven.hibernate.model.Address;
import com.aydinseven.hibernate.model.Contact;
import com.aydinseven.hibernate.model.User;

public class ContactDAOImpl implements ContactDAO {

	public Contact createContact(String name, String lastName,
			int[] dateOfBirth) {
		final Contact contact = new Contact();
		contact.setFirstName(name);
		contact.setLastName(lastName);
		contact.setDateOfBirth(newDate(dateOfBirth[0], dateOfBirth[1], dateOfBirth[2]));
		HibernateUtil.save(contact);
		return contact;
	}
	
	public Contact createContact(String name, String lastName) {
		final Contact contact = new Contact();
		contact.setFirstName(name);
		contact.setLastName(lastName);
		HibernateUtil.save(contact);
		return contact;
	}
	
	public Contact saveContact(Contact contact) {
		
		HibernateUtil.save(contact);
		return contact;
	}

	public Contact getContact(int id) {
		return (Contact) HibernateUtil.get(Contact.class, id);
	}

	public Address getAddress(int id) {
		return (Address) HibernateUtil.get(Address.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Contact> getContacts() {
		return (List<Contact>) HibernateUtil.getList(Contact.class);
	}
	
	public Contact updateContact(Contact contact) {
		HibernateUtil.update(contact);
		return contact;
	}

	public Address updateAddress(Address address) {
		HibernateUtil.update(address);
		return address;
	}

	public void deleteContact(Contact contact) {
		HibernateUtil.delete(contact);
	}

	private static Date newDate(final int year, final int month, final int date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(year + 1900, month, date);
		return calendar.getTime();
	}
	
	public void deleteAddress(Address address) {
		address.getContact().removeAddress(address);
		HibernateUtil.delete(address);
	}
}