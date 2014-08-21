package com.aydinseven.wicket.pages.address;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.INullAcceptingValidator;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.StringValidator;

import com.aydinseven.hibernate.ContactDAO;
import com.aydinseven.hibernate.ContactDAOImpl;
import com.aydinseven.hibernate.model.Address;
import com.aydinseven.hibernate.model.Contact;
import com.aydinseven.wicket.pages.contact.EditContact;

/**
 * Page with a form to create a new address
 */
public class AddAddress extends WebPage {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 */
	public AddAddress(Contact contact) {

		CompoundPropertyModel<Contact> contactModel = new CompoundPropertyModel<Contact>(contact);
		setDefaultModel(contactModel);

		// Create and add feedback panel to page
		add(new FeedbackPanel("feedback"));

		// Add a create Contact form to the page
		add(new CreateAddressForm("createAddressForm", contactModel));

	}

	@SuppressWarnings("serial")
	public static final class CreateAddressForm extends Form<Contact> {

		final Address address = new Address();
		
		public CreateAddressForm(final String id, IModel<Contact> contactModel) {
			super(id, contactModel);

			// Address text fields: -- NOTE: all following address fields are bound to a defaultModel, which is bound to the address 
			TextField<String> streetField = new TextField<String>("street");
			streetField.setDefaultModel(new PropertyModel<Address>(address, "street"));
			streetField.setRequired(true);
			streetField.add(StringValidator.maximumLength(30));
			add(streetField);

			TextField<Integer> zipcodeField = new TextField<Integer>("zipcode"); // TODO: 5 digits: default nothing --> String entgegen nehmen, dann int
			zipcodeField.setDefaultModel(new PropertyModel<Address>(address, "zipcode"));
			zipcodeField.setRequired(true);
			zipcodeField.add(new RangeValidator<Integer>(10000, 99999));
			add(zipcodeField);
			
			TextField<String> cityField = new TextField<String>("city");
			cityField.setDefaultModel(new PropertyModel<Address>(address, "city"));
			cityField.setRequired(true);
			cityField.add(StringValidator.maximumLength(30));
			add(cityField);
			
            List<String> countries = new ArrayList<String>();
            countries.add("Germany");
            countries.add("America");
            countries.add("Spain");
            countries.add("Denmark");
            countries.add("Mexico");

            IModel<String> dropdownModel = new Model<String>("");
            DropDownChoice<String> countryDDC = new DropDownChoice<String>("country",  dropdownModel, countries);
            countryDDC.setDefaultModel(new PropertyModel<Address>(address, "country"));
            countryDDC.setRequired(true);
            add(countryDDC);
			
            CheckBox isWorkAddressCB = new CheckBox("isWorkAddress", new Model<Boolean>());
            isWorkAddressCB.setDefaultModel(new PropertyModel<Address>(address, "isWorkAddress"));
            add(isWorkAddressCB);
			
			add(backLink("backLink", contactModel.getObject()));
		}

		@Override
		public final void onSubmit() {
		
			Contact contact = getModelObject();
			final ContactDAO contactDAO = new ContactDAOImpl();
			contact.addAddress(address);
			contactDAO.updateContact(contact);

			getSession().info("Address added to Contact '"+contact.getFirstName()+"'!");
			setResponsePage(new AddAddress(contact));
		}
		
		public static Link<Void> backLink(final String name, final Contact contact) {
	    	
	        return new Link<Void>(name)
	        {
	            /**
	             * @see org.apache.wicket.markup.html.link.Link#onClick()
	             */
	            @Override
	            public void onClick()
	            {
	                setResponsePage(new EditContact(contact));
	            }
	        };
	    }
	}
}