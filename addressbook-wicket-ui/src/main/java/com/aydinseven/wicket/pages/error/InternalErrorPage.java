package com.aydinseven.wicket.pages.error;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

import com.aydinseven.wicket.pages.HomePage;

public class InternalErrorPage extends WebPage {
	
	public InternalErrorPage() {
		
	    add(new Label("notification", "An error occured. Please contact the system administrator!"));
	    
	    add(new Link("back"){
        	@Override
        	public void onClick() {

        		setResponsePage(new HomePage());
        	}
        });
	}

}