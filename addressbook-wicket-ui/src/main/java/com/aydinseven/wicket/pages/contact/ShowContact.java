package com.aydinseven.wicket.pages.contact;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;

import com.aydinseven.hibernate.model.Address;
import com.aydinseven.hibernate.model.Contact;
import com.aydinseven.wicket.pages.HomePage;

public class ShowContact extends WebPage{

	public ShowContact(final Contact contact) {
		
		//Contac's details
		add(new Label("firstName", contact.getFirstName()));
		add(new Label("lastName", contact.getLastName()));
		if (contact.getDateOfBirth() != null) {
			add(new Label("dateOfBirth", contact.getDateOfBirth()));
		} else {
		    add(new Label("dateOfBirth", "Not specified"));
		}
		
		// List the contact's addresses
        List<Address> addresses = new ArrayList<Address>();
        addresses.addAll(contact.getAddresses());
        
        final PageableListView<Address> listView;
        
        add(listView = new PageableListView<Address>("addressList", addresses, 10) { 
        	
        	@Override 
        	protected void populateItem(ListItem<Address> addressItem) { 
        		
        		final Address address = addressItem.getModelObject();
        		
        		addressItem.add(new Label("street", address.getStreet()));
        		addressItem.add(new Label("zipcode", address.getZipcode()));
        		addressItem.add(new Label("city", address.getCity()));
        		addressItem.add(new Label("country", address.getCountry()));
        		if (address.getIsWorkAddress()) {
        			addressItem.add(new Label("isWorkAddress", "Yes"));
        		} else {
        		    addressItem.add(new Label("isWorkAddress", "No"));
        		}

        	}
        });
        
        add(new PagingNavigator("pageNavigator", listView));
        
        add(backLink("backLink"));
	}
	
	public static Link<Void> backLink(final String name) {
    	
        return new Link<Void>(name)
        {
            /**
             * @see org.apache.wicket.markup.html.link.Link#onClick()
             */
            @Override
            public void onClick()
            {
                setResponsePage(new HomePage());
            }
        };
    }
	
}