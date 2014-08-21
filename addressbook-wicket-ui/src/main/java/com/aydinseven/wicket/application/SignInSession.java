package com.aydinseven.wicket.application;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import com.aydinseven.hibernate.UserDAO;
import com.aydinseven.hibernate.UserDAOImpl;
import com.aydinseven.hibernate.model.User;

/**
 * Session class for signin example. Holds and authenticates users.
 * 
 * @author Jonathan Locke
 */
public final class SignInSession extends AuthenticatedWebSession {
	
    private User userInSession;
    
    /**
     * Constructor
     * 
     * @param request
     */
    protected SignInSession(Request request) {
    	
        super(request);
    }

    /**
     * Checks the given username and password, returning a User object if the username and
     * password identify a valid user.
     * 
     * @param username
     *            The username
     * @param password
     *            The password
     * @return True if the user was authenticated
     */
    @Override
    public final boolean authenticate(final String username, final String password) {
    	
        if (userInSession == null) {
        	
        	final UserDAO userDAO = new UserDAOImpl();
        	User u = userDAO.getUserByUsername(username);
        	
        	if (u != null) {
        		if (u.getPassword().equalsIgnoreCase(password)) {
        			userInSession = u;
        		}
        	}
        }

        return userInSession != null;
    }

    /**
     * @return User
     */
    public User getUser()
    {
        return userInSession;
    }

    /**
     * @param user
     *            New user
     */
    public void setUser(final User user)
    {
        this.userInSession = user;
    }

    /**
     * @see org.apache.wicket.authentication.AuthenticatedWebSession#getRoles()
     */
    @Override
    public Roles getRoles() {
    	
    	Roles roles = new Roles(); 
    	
    	//If user is signed in add the relative role: 
    	if(isSignedIn()) roles.add("SIGNED_IN"); 
    	
    	//Add the user's role:
    	if( userInSession != null && userInSession.getRole().equals("ADMIN")) { roles.add("ADMIN"); }
    	if( userInSession != null && userInSession.getRole().equals("USER")) { roles.add("USER"); }
    	if( userInSession != null && userInSession.getRole().equals("GUEST")) { roles.add("GUEST"); }
    	
    	return roles;
    }
    
	@Override
	public void signOut() {
		super.signOut();
		userInSession = null;
	}
}