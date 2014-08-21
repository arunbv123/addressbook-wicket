package com.aydinseven.wicket.pages.error;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

import com.aydinseven.wicket.application.SignIn;

public class ExpiredPage extends WebPage {
	
	public ExpiredPage() {
		
	    add(new Label("notification", "Your session is expired!"));
	    
	    add(new Link("back"){
        	@Override
        	public void onClick() {

        		setResponsePage(new SignIn());
        	}
        });
	}

}