package com.aydinseven.wicket.pages.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

import com.aydinseven.hibernate.ContactDAO;
import com.aydinseven.hibernate.ContactDAOImpl;
import com.aydinseven.hibernate.HibernateUtil;
import com.aydinseven.hibernate.UserDAO;
import com.aydinseven.hibernate.UserDAOImpl;
import com.aydinseven.hibernate.model.Address;
import com.aydinseven.hibernate.model.User;
import com.aydinseven.wicket.application.SignInSession;
import com.aydinseven.wicket.pages.HomePage;

//@AuthorizeInstantiation("ADMIN")
//@AuthorizeAction(action = "RENDER", roles = {"ADMIN"})
public class EditUsers extends WebPage {
	
	private static CompoundPropertyModel<User> userCompoundPropModel;
	final static UserDAO userDAO = new UserDAOImpl();
	
	public EditUsers() {
		
		 // Add a FeedbackPanel for displaying our messages
        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);
		
		// Addresses list view
		List<User> userList = new ArrayList<User>();
        userList.addAll(userDAO.getUsers());
        
        final PageableListView<User> listView;
        
        add(listView = new PageableListView<User>("userList", userList, 10) { 
        	
        	@Override 
        	protected void populateItem(ListItem<User> userItem) { 
        		
        		final User u = userItem.getModelObject();
        		
        		userCompoundPropModel = new CompoundPropertyModel<User>(userItem.getModelObject());
        		userItem.setDefaultModel(userCompoundPropModel);
        		
        		// Add a form for every User in the list view
        		Form<User> form = new UpdateUserForm("form", userCompoundPropModel);
        		add(form);
        		
        		TextField<String> usernameField = new TextField<String>("username");
        		usernameField.setRequired(true);
        		usernameField.setDefaultModel(new PropertyModel<Address>(u, "username"));
        		userItem.add(usernameField);
        		form.add(usernameField);
        		
        		TextField<String> passwordField = new TextField<String>("password");
        		passwordField.setRequired(true);
        		passwordField.setDefaultModel(new PropertyModel<Address>(u, "password"));
        		userItem.add(passwordField);
        		form.add(passwordField);
        		
        		List<String> userRoles = new ArrayList<String>(); // TODO: Read Roles from database
                userRoles.add("USER");
                userRoles.add("GUEST");
                userRoles.add("ADMIN");

                DropDownChoice<String> rolesDDC = new DropDownChoice<String>("role", userRoles);
                rolesDDC.setRequired(true);
                rolesDDC.setDefaultModel(new PropertyModel<Address>(u, "role"));
                add(rolesDDC);
                form.add(rolesDDC);
        		
        		final SignInSession session = (SignInSession) getSession();
                final User userInSession = session.getUser();
                
                // Add a link to remove the chosen user if it's not the current active user
                if (u.getUsername().equalsIgnoreCase(userInSession.getUsername())) {
        		userItem.add(removeUserLink("removeUser", u).setEnabled(false));
                } else {
                	userItem.add(removeUserLink("removeUser", u));
                }
                
        		userItem.add(form);
                
        	}
        });
        
        // Page navigator for the pageable listview
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
	
	public static Link<Void> removeUserLink(final String name, final User u) {
    	
        return new Link<Void>(name)
        {
            /**
             * @see org.apache.wicket.markup.html.link.Link#onClick()
             */
            @Override
            public void onClick()
            {
            	final ContactDAO contactDAO = new ContactDAOImpl();
            	HibernateUtil.delete(u);
            	
    			//Pass success message to next page:
    			getSession().info("The User '"+u.getUsername()+"' was removed!");
            	setResponsePage(new EditUsers());
            }
        };
    }
	
	public static class UpdateUserForm extends Form<User> {

		public UpdateUserForm(String id, IModel<User> userModel) {
			super(id, userModel);
		}
		
		@Override
		public  void onSubmit() {

			final User u = getModelObject();
			final ContactDAO contactDAO = new ContactDAOImpl();
			userDAO.updateUser(u);
			
			// A message stored with getSession().info("...") gets automatically picked up by the target page's feedback panel:
			getSession().info("User saved!");
			setResponsePage(new EditUsers());
		}
		
	}
}