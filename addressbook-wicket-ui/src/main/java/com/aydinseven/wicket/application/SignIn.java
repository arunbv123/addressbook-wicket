package com.aydinseven.wicket.application;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.aydinseven.wicket.pages.HomePage;
import com.aydinseven.wicket.pages.user.RegisterUser;

/**
 * Simple example of a sign in page. Even simpler, as shown in the authentication-2 example, is
 * using the SignInPanel from the auth-role package. Beside that this simple example does not
 * support "rememberMe".
 * 
 * @author Jonathan Locke
 */
public final class SignIn extends WebPage {
	
    SignInSession session = getMySession();
	
    /**
     * @return Session
     */
    private SignInSession getMySession() {
        return (SignInSession)getSession();
    }
	
    /**
     * Page Constructor
     */
    public SignIn() {
    	
    	// Create feedback panel and add to page
        add(new FeedbackPanel("feedback"));
        
        // Add register button:
        add(registerUserLink("registerUser"));
        
        //Check if there already is a session:
        if (session.isSignedIn()) {
        	// set feedback message and go to HomePage:
        	getSession().info("Welcome back '"+session.getUser().getUsername()+"'!");
        	setResponsePage(new HomePage());
        }
        else {
	        // Add sign-in form to the page:
	        add(new SignInForm("signInForm"));
        }
    }
    
    public static Link<Void> registerUserLink(final String name) {
    	
        return new Link<Void>(name)
        {
            /**
             * @see org.apache.wicket.markup.html.link.Link#onClick()
             */
            @Override
            public void onClick() {
            	
            	setResponsePage(new RegisterUser());
            }
        };
    }
    
    /**
     * Sign in form
     */
    public final class SignInForm extends Form<Void> {
    	
        TextField<String> usernameField;
        PasswordTextField passwordField;

        /**
         * Constructor
         */
        public SignInForm(final String id) {
        	
            super(id);

            usernameField = new TextField<String>("username", Model.of(" "));
            usernameField.setRequired(true);
            add(usernameField);
            
            passwordField = new PasswordTextField("password", Model.of(" "));
            passwordField.setRequired(true);
            add(passwordField);
        }

        /**
         * @see org.apache.wicket.markup.html.form.Form#onSubmit()
         */
        @Override
        public final void onSubmit() {
        	
	            // Sign the user in
	            if (session.signIn(usernameField.getModelObject(), passwordField.getModelObject())) {
	            	getSession().info("Welcome '"+usernameField.getModelObject()+"'!");
	            	setResponsePage(new HomePage());
	            }
	            else {
	                // Get the error message from the properties file associated with the Component
	                String errmsg = getString("loginError", null, "Login failed!");
	
	                // Register the error message with the feedback panel
	                error(errmsg);
	            }
        }

    }
}