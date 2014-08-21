package com.aydinseven.wicket.pages.error;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

import com.aydinseven.wicket.pages.HomePage;

public class NotAllowedPage extends WebPage {
	
	public NotAllowedPage() {
		
	    add(new Label("notification", "You are not allowed to view this page!"));
	    
	    add(new Link("back"){
        	@Override
        	public void onClick() {

        		setResponsePage(new HomePage());
        	}
        });
	}

}