package com.aydinseven.wicket.pages.contact;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.StringValidator;

import com.aydinseven.hibernate.ContactDAO;
import com.aydinseven.hibernate.ContactDAOImpl;
import com.aydinseven.hibernate.model.Contact;

/**
 * Homepage with a form to create a new contact
 */
@AuthorizeAction(action = "RENDER", roles = {"ADMIN"})
public class CreateNewContact extends WebPage {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 *
	 */
	public CreateNewContact() {
		
		Contact contact = new Contact();
		CompoundPropertyModel<Contact> contactModel = new CompoundPropertyModel<Contact>(contact);
		setDefaultModel(contactModel);

		// Create and add feedback panel to page
		add(new FeedbackPanel("feedback"));

		// Add a create Contact form to the page
		add(new CreateContactForm("createContactForm", contactModel));

	}

	@SuppressWarnings("serial")
	public static final class CreateContactForm extends Form<Contact> {

		public CreateContactForm(final String id, IModel<Contact> contactModel) { 
			super(id, contactModel);

			// Contact text fields:
			TextField<String> firstNameField = new TextField<String>("firstName");
			firstNameField.setRequired(true);
			firstNameField.add(StringValidator.maximumLength(30));
			add(firstNameField);

			TextField<String> lastNameField = new TextField<String>("lastName");
			lastNameField.setRequired(true);
			lastNameField.add(StringValidator.maximumLength(30));
			add(lastNameField);

			DateTextField dateOfBirthField = new DateTextField("dateOfBirth",
					new PatternDateConverter("MM/dd/yyyy", false));
			
			// Add a date picker to the date of birth field:
			DatePicker datePicker = new DatePicker();
		    datePicker.setShowOnFieldClick(true);
			datePicker.setAutoHide(true); dateOfBirthField.add(datePicker);
			
			add(dateOfBirthField);
			add(ShowContact.backLink("backLink"));
		}

		@Override
		public final void onSubmit() {
			
			final Contact contact = getModelObject();
			final ContactDAO contactDAO = new ContactDAOImpl();
			contactDAO.saveContact(contact);
			
			//Pass success message to next page:
			getSession().info("The Contact '"+contact.getFirstName()+"' was saved!");
			
			setResponsePage(new EditContact(contact));
		}
	}
}