package com.aydinseven.wicket.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

import com.aydinseven.hibernate.ContactDAO;
import com.aydinseven.hibernate.ContactDAOImpl;
import com.aydinseven.hibernate.model.Address;
import com.aydinseven.hibernate.model.Contact;
import com.aydinseven.wicket.pages.contact.EditContact;

public class RemoveConfirmation extends WebPage{
	
	private static final long serialVersionUID = 961069910549425350L;

	public RemoveConfirmation(final Object object, String objectName) {
		
		add(new Label("notification", "Are you sure you want to remove the "+object.getClass().getSimpleName()+" '"+objectName+"'?"));
		
		if (object instanceof Contact) {
			
			add(confirmRemoveContactLink("confirmRemove", (Contact)object));
			
			Link<Void> denyRemoveContactLink = denyRemoveContactLink("denyRemove");
			add(denyRemoveContactLink);
			
		} else {
			
			add(confirmRemoveAddressLink("confirmRemove",(Address)object));
			
			add(denyRemoveAddressLink("denyRemove", (Address)object));
		}
		
	}
	
    public static Link<Void> confirmRemoveContactLink(final String name, final Contact contact) {
    	
        return new Link<Void>(name)
        {
            /**
             * @see org.apache.wicket.markup.html.link.Link#onClick()
             */
            @Override
            public void onClick()
            {
            	final ContactDAO contactDAO = new ContactDAOImpl();
            	contactDAO.deleteContact(contact);
            	
    			//Pass success message to next page:
    			getSession().info("The Contact '"+contact.getFirstName()+"' was removed!");
            	
                setResponsePage(new HomePage());
            }
        };
    }
    
public static Link<Void> confirmRemoveAddressLink(final String name, final Address address) {
    	
        return new Link<Void>(name)
        {
            /**
             * @see org.apache.wicket.markup.html.link.Link#onClick()
             */
            @Override
            public void onClick()
            {
            	Contact contact = address.getContact();
            	final ContactDAO contactDAO = new ContactDAOImpl();
            	contactDAO.deleteAddress(address);
            	
    			//Pass success message to next page:
    			getSession().info("The Address in '"+address.getCity()+"' was removed!");
            	
                setResponsePage(new EditContact(contact));
            }
        };
    }
    
    public static Link<Void> denyRemoveContactLink(final String name) {
    	
        return new Link<Void>(name)
        {
            /**
             * @see org.apache.wicket.markup.html.link.Link#onClick()
             */
            @Override
            public void onClick()
            {
    			//Pass message to next page:
    			getSession().info("Remove canceled!");
    			
                setResponsePage(new HomePage());
            }
        };
    }
    
public static Link<Void> denyRemoveAddressLink(final String name, final Address address) {
    	
        return new Link<Void>(name)
        {
            /**
             * @see org.apache.wicket.markup.html.link.Link#onClick()
             */
            @Override
            public void onClick()
            {
            	//Pass message to next page:
    			getSession().info("Remove canceled!");
            	
                setResponsePage(new EditContact(address.getContact()));
            }
        };
    }
}