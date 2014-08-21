package com.aydinseven.wicket.pages.user;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.StringValidator;

import com.aydinseven.hibernate.UserDAO;
import com.aydinseven.hibernate.UserDAOImpl;
import com.aydinseven.hibernate.model.User;
import com.aydinseven.wicket.application.SignIn;

public class RegisterUser extends WebPage {
	
	/**
	 * Constructor that is invoked when page is invoked without a session.
	 *
	 */
	public RegisterUser() {
		
		User u = new User();
		CompoundPropertyModel<User> userModel = new CompoundPropertyModel<User>(u);
		setDefaultModel(userModel);

		// Create and add feedback panel to page
		add(new FeedbackPanel("feedback"));

		// Add a create Contact form to the page
		add(new CreateUserForm("createUserForm", userModel));

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
                setResponsePage(SignIn.class);
            }
        };
    }

	@SuppressWarnings("serial")
	public static final class CreateUserForm extends Form<User> {

		public CreateUserForm(final String id, IModel<User> userModel) { 
			super(id, userModel);

			// User text fields:
			TextField<String> usernameField = new TextField<String>("username");
			usernameField.setRequired(true);
			usernameField.add(StringValidator.maximumLength(30));
			add(usernameField);

			TextField<String> passwordField = new TextField<String>("password");
			passwordField.setRequired(true);
			passwordField.add(StringValidator.maximumLength(30));
			add(passwordField);

			add(backLink("backLink"));
		}

		@Override
		public final void onSubmit() {
			
			final User u = getModelObject();
			u.setRole("GUEST");
			final UserDAO userDAO = new UserDAOImpl();
			Boolean saveSuccessful = userDAO.saveUser(u);
			
			if (saveSuccessful) {
				
				//Pass success message to next page:
				getSession().info("Thank you '"+u.getUsername()+"' for registering! You may now sign in with your password.");
				
				setResponsePage(SignIn.class);
			} else {
				info("'"+u.getUsername()+"' is already in use!");
			}
			
		}
	}

}