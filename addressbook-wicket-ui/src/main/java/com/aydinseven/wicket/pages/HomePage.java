package com.aydinseven.wicket.pages;

import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.aydinseven.hibernate.ContactDAO;
import com.aydinseven.hibernate.ContactDAOImpl;
import com.aydinseven.hibernate.model.Contact;
import com.aydinseven.hibernate.model.User;
import com.aydinseven.wicket.application.SignIn;
import com.aydinseven.wicket.application.SignInSession;
import com.aydinseven.wicket.pages.contact.CreateNewContact;
import com.aydinseven.wicket.pages.contact.EditContact;
import com.aydinseven.wicket.pages.contact.ShowContact;
import com.aydinseven.wicket.pages.contact.ShowContactModalWindow;
import com.aydinseven.wicket.pages.user.EditUsers;

/**
 * The applications homepage. Shows a list of contacts
 */
//@AuthorizeAction(action = "RENDER", roles = { "SIGNED_IN" })
public class HomePage extends WebPage {

	/**
	 * Page constructor
	 */
	public HomePage() {

		final SignInSession session = (SignInSession) getSession();
		
		// Show user's name and role:
		add(new Label("userInfo", getUserInfo(session)));

		// Add a FeedbackPanel for displaying our messages
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		add(feedbackPanel);

		// Add sign out button:
		add(signOutLink("signOut"));

		// Add Edit Users button:
		add(new EditUsersLink("editUsers", EditUsers.class));

		// List all Contacts
		final ContactDAO contactDAO = new ContactDAOImpl();
		final List<Contact> contacts = contactDAO.getContacts();
		final PageableListView<Contact> listView;

		add(listView = new PageableListView<Contact>("contactList", contacts, 10) {

			@Override
			protected void populateItem(ListItem<Contact> contactItem) {

				final Contact contact = contactItem.getModelObject();

				contactItem.add(new Label("firstName", contact.getFirstName()));
				contactItem.add(new Label("lastName", contact.getLastName()));
				contactItem.add(new Label("dateOfBirth", new PropertyModel(
						contactItem.getModel(), "dateOfBirth")));

				// The old "normal" link:
				// contactItem.add(showContactLink("showContact", contact));

				// Modal window for ShowContact:
				// -----------------------------------------------------------------------------
				final ModalWindow modal;
				add(modal = new ModalWindow("modalWindow"));
				modal.setCookieName("modal-1");
				modal.setResizable(true);
				modal.setInitialWidth(880);
				modal.setInitialHeight(650);
				modal.setWidthUnit("px");
				modal.setHeightUnit("px");

				modal.setPageCreator(new ModalWindow.PageCreator() {
					public Page createPage() {
						// Use this constructor to pass a reference of this
						// page.
						return new ShowContactModalWindow(HomePage.this
								.getPageReference(), modal, contact);
					}
				});
				modal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
					public void onClose(AjaxRequestTarget target) {
						// target.add(border);
						modal.close(target);
					}
				});
				modal.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {
					public boolean onCloseButtonClicked(AjaxRequestTarget target) {
						modal.close(target);
						return true;
					}
				});

				// Add the link that opens the modal window.
				contactItem.add(new AjaxLink<Void>("showContact") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						modal.show(target);
					}
				});
				contactItem.add(modal);
				// Modal window end
				// -----------------------------------------------------------------------------------------

				contactItem.add(editContactLink("editContact", contact));

				// Link for remove confirmation:
				Link<Void> removeContactLink = removeContactLink("removeContact", contact);
				contactItem.add(removeContactLink);
			}

		});

		add(new PagingNavigator("pageNavigator", listView));

		// Create new Contact link
		add(new Link("create_new_contact_link") {
			@Override
			public void onClick() {

				setResponsePage(new CreateNewContact());
			}
		});

	}
	
	protected String getUserInfo(final SignInSession session) {
		final User user = session.getUser();
		if (null != user) {
			return "User: " + user.getUsername() + " || Role: "+ user.getRole();
		} else {
			return "No user data available.";
		}
	}
	
	@AuthorizeAction(action = "ENABLE", roles = { "ADMIN" })
	public class EditUsersLink extends BookmarkablePageLink {
	    	
	    public EditUsersLink(String id, Class pageClass) {
	                    super(id, pageClass);
	    }
	} 

	public static Link<Void> showContactLink(final String name,
			final Contact contact) {

		return new Link<Void>(name) {
			/**
			 * @see org.apache.wicket.markup.html.link.Link#onClick()
			 */
			@Override
			public void onClick() {
				setResponsePage(new ShowContact(contact));
			}
		};
	}

	public static Link<Void> removeContactLink(final String name,
			final Contact contact) {

		return new Link<Void>(name) {
			private static final long serialVersionUID = 7487367604406288782L;

			/**
			 * @see org.apache.wicket.markup.html.link.Link#onClick()
			 */
			@Override
			public void onClick() {
				setResponsePage(new RemoveConfirmation(contact,
						contact.getFirstName()));
			}
			// Check the user's role:
			@Override
			public boolean isEnabled() {
				return isAdminOrUser(getWebSession());
			}
		};
	}

	public static Link<Void> editContactLink(final String name,
			final Contact contact) {

		return new Link<Void>(name) {
			/**
			 * @see org.apache.wicket.markup.html.link.Link#onClick()
			 */
			@Override
			public void onClick() {
				setResponsePage(new EditContact(contact));
			}
			// Check the user's role:
			@Override
			public boolean isEnabled() {
				return isAdminOrUser(getWebSession());
			}
		};
	}

	public static Link<Void> signOutLink(final String name) {

		return new Link<Void>(name) {
			/**
			 * @see org.apache.wicket.markup.html.link.Link#onClick()
			 */
			@Override
			public void onClick() {

				// Log out:
				getSession().invalidate();
				setResponsePage(SignIn.class);
			}
		};
	}

	public static Boolean isAdminOrUser(WebSession session) {
		if (session instanceof SignInSession) {
			final SignInSession signInSession = (SignInSession) session;
			return signInSession.getRoles().hasRole("ADMIN") || signInSession.getRoles().hasRole("USER");
		}
		return true; //TODO: Why?
	}
}