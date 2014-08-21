package com.aydinseven.wicket.pages;

import org.apache.wicket.PageReference;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;

import com.aydinseven.hibernate.ContactDAO;
import com.aydinseven.hibernate.ContactDAOImpl;
import com.aydinseven.hibernate.model.Address;
import com.aydinseven.hibernate.model.Contact;


public class RemoveConfirmationModalWindow extends WebPage{
	
	private static final long serialVersionUID = 961069910549425350L;

	public RemoveConfirmationModalWindow(final PageReference modalWindowPage,  
            final ModalWindow modal, final Object object, String objectName) {
		
		add(new Label("notification", "Are you sure you want to remove the "+object.getClass().getSimpleName()+" '"+objectName+"'?"));
		
		if (object instanceof Contact) {
			
			add(confirmRemoveContactLink("confirmRemove", ((Contact)object), modal));
			
			AjaxLink<Void> denyRemoveContactLink = denyRemoveContactLink("denyRemove", modal);
			add(denyRemoveContactLink);
			
		} else {
			
			add(confirmRemoveAddressLink("confirmRemove",(Address)object));
			
			add(denyRemoveAddressLink("denyRemove", (Address)object));
		}
		
	}
	
    public static AjaxLink<Void> confirmRemoveContactLink(final String name, final Contact contact, final ModalWindow modal) {
    	
        return new AjaxLink<Void>(name)
        {
            /**
             * @see org.apache.wicket.markup.html.link.Link#onClick()
             */
            @Override
            public void onClick(AjaxRequestTarget target) {
            	
            	final ContactDAO contactDAO = new ContactDAOImpl();
            	contactDAO.deleteContact(contact);
            	
    			//Pass success message to next page:
    			getSession().info("The Contact '"+contact.getFirstName()+"' was removed!");
    			modal.close(target);
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
//                setResponsePage(new EditContact(contact));
            }
        };
    }
    
    public static AjaxLink<Void> denyRemoveContactLink(final String name, final ModalWindow modal) {
    	
        return new AjaxLink<Void>(name)
        {
            /**
             * @see org.apache.wicket.markup.html.link.Link#onClick()
             */
            @Override
            public void onClick(AjaxRequestTarget target)
            {
    			//Pass message to next page:
    			getSession().info("Remove canceled!");
    			modal.close(target);
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
//                setResponsePage(new EditContact(address.getContact()));
            }
        };
    }
}