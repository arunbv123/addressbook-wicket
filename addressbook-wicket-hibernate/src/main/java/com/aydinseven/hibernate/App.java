package com.aydinseven.hibernate;

import java.util.ArrayList;
import java.util.List;

import com.aydinseven.hibernate.model.Address;
import com.aydinseven.hibernate.model.Contact;
import com.aydinseven.hibernate.model.User;

/**
 * Hello world!
 */
public class App {

	public static void addTestData(final ContactDAO dao) {
		Contact contact;
		Address address;
		
		contact = dao.createContact("James", "Hetfield", new int[]{72, 11, 12});
		address = new Address();
		address.setStreet("Hallo Str. 1");
		address.setZipcode(25874);
		address.setCity("Leipzig");
		address.setCountry("Germany");
		address.setIsWorkAddress(true);
		contact.addAddress(address);
		dao.updateContact(contact);

		contact = dao.createContact("Lars", "Ulrich", new int[]{73, 8, 11});
		address = new Address();
		address.setStreet("Wieso Str. 2");
		address.setZipcode(34532);
		address.setCity("Berlin");
		address.setCountry("Danmark");
		address.setIsWorkAddress(false);
		contact.addAddress(address);
		dao.updateContact(contact);
		
		contact = dao.createContact("Kirk", "Hammet", new int[]{75, 6, 6});
		address = new Address();
		address.setStreet("Warum Str 3");
		address.setZipcode(12654);
		address.setCity("Delmenhorst");
		address.setCountry("USA");
		address.setIsWorkAddress(true);
		contact.addAddress(address);
		dao.updateContact(contact);
		
		contact = dao.createContact("Robert", "Trujillo", new int[]{78, 10, 25});
		address = new Address();
		address.setStreet("Alter Str. 4");
		address.setZipcode(16546);
		address.setCity("Bremen");
		address.setCountry("Canada");
		address.setIsWorkAddress(false);
		contact.addAddress(address);
		dao.updateContact(contact);
		
	}
	
    public static void main(String[] args) {
    	
    	final List<String> listArguments = new ArrayList<String>();
    	
    	for (String a : args) {
    		listArguments.add(a);
    	}
    	
        System.out.println("Maven + Hibernate + HSQL Quickstart");

        final ContactDAO contactDAO = new ContactDAOImpl();
        
        if (listArguments.contains("testdata")) {
        	addTestData(contactDAO);
        }
        
        //-------------------------------------------------------
      
        User user = new User();
        final UserDAO userDAO = new UserDAOImpl();
        user = userDAO.getUserByUsername("Rolf");
        if (user == null) {
        	System.out.println("No such user in DB!");
        }
        else {
        	System.out.println("Yay!");
        }
        
//        user.setUsername("Florian");
//        user.setPassword("booja");
//        user.setRole("ADMIN");
//        
//        try {
//        	contactDAO.saveUser(user);
//        } catch (RollbackException e) { 
//        	System.out.println("Fehler: Username already in DB!");
//        }
        
//        Contact contact = contactDAO.getContact(1);
//        List<Address> addresses = new ArrayList<Address>();
//        addresses.addAll(contact.getAddresses());
//		Address address = addresses.get(0);
//		
//		System.out.println(contact.getFirstName()+", ");
//		System.out.println(address.getStreet());
//		
//		address.setStreet("Bier Str. 666");
//		contactDAO.updateAddress(address);
		
		//contact.setFirstName("1");
		//address.setStreet("Maple Str. 999");
		//address.setZipcode(98733);
		//address.setCity("New York");
		//address.setCountry("America");
		//address.setIsWorkAddress(false);
		//contact.addAddress(address);
		//contactDAO.saveContact(contact);
        
        //Contact contact = contactDAO.getContact(1);
        //contact.setName("fred");
        //contactDAO.updateContact(contact);
        
        /*if (!contact.getAddresses().isEmpty()) {
        	Address address = (Address)contact.getAddresses().toArray()[0];
        	System.out.println(address.getStreet());
            contactDAO.deleteAddress(address);
    	}*/
        
        //Address address = contact.getAddresses().get(0);
		//System.out.println(address.getStreet());
       /* address.setStreet("Lange Str. 15");
		address.setZipcode(29851);
		address.setCity("Del");
		System.out.println(address.getStreet());*/
		
		//contactDAO.updateAddress(address);	
		
		//contact.addAddress(address);
		//contactDAO.updateContact(contact);
        
        
        System.out.println("------------------------------- :) ");
        
        /*List<Contact> contacts = contactDAO.getContacts();
        for (Contact contact2 : contacts) {
        	System.out.println(contact2.getName() + ", addresses.size(): " + contact2.getAddresses().size());
        }*/
        
        //contactDAO.deleteContact(contactDAO.getContact(1));
    }
}