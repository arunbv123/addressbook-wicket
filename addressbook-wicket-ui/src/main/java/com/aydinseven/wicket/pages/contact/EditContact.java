package com.aydinseven.wicket.pages.contact;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.CheckBox;
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
import org.apache.wicket.validation.validator.StringValidator;

import com.aydinseven.hibernate.ContactDAO;
import com.aydinseven.hibernate.ContactDAOImpl;
import com.aydinseven.hibernate.model.Address;
import com.aydinseven.hibernate.model.Contact;
import com.aydinseven.wicket.pages.HomePage;
import com.aydinseven.wicket.pages.RemoveConfirmation;
import com.aydinseven.wicket.pages.address.AddAddress;

public class EditContact extends WebPage{ 
	
	private static final long serialVersionUID = 8592212784624072042L; //TODO: Google

	public EditContact(final Contact contact) {
		
		CompoundPropertyModel<Contact> contactModel = new CompoundPropertyModel<Contact>(contact);
		setDefaultModel(contactModel);

		// Add a create Contact form to the page
		add(new EditContactForm("editContactForm", contactModel));
		
		 // Add a FeedbackPanel for displaying our messages
        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);
		
		// Addresses list view
		List<Address> addresses = new ArrayList<Address>();
        addresses.addAll(contact.getAddresses());
        
        final PageableListView<Address> listView;
        
        add(listView = new PageableListView<Address>("addressList", addresses, 10) { 
        	
        	@Override 
        	protected void populateItem(ListItem<Address> addressItem) { 
        		
        		final Address address = addressItem.getModelObject();
        		
        		final CompoundPropertyModel<Address> addressCompoundPropModel = new CompoundPropertyModel<Address>(addressItem.getModelObject());
        		addressItem.setDefaultModel(addressCompoundPropModel);
        		
        		// Add a form to the list view
        		Form<Address> form = new UpdateAddressForm("form", addressCompoundPropModel, contact);
        		add(form);
        		
        		// Add the address' fields to the listview
        		TextField<String> streetField = new TextField<String>("street");
        		streetField.setRequired(true);
        		streetField.setDefaultModel(new PropertyModel<Address>(address, "street"));
        		addressItem.add(streetField);
        		form.add(streetField);
        		
        		TextField<Integer> zipcodeField = new TextField<Integer>("zipcode");
        		zipcodeField.setRequired(true);
        		zipcodeField.setDefaultModel(new PropertyModel<Address>(address, "zipcode"));
        		addressItem.add(zipcodeField);
        		form.add(zipcodeField);
        		
        		TextField<String> cityField = new TextField<String>("city");
        		cityField.setRequired(true);
        		cityField.setDefaultModel(new PropertyModel<Address>(address, "city"));
        		addressItem.add(cityField);
        		form.add(cityField);
        		
        		List<String> countries = new ArrayList<String>();
                countries.add("Germany");
                countries.add("America");
                countries.add("Spain");
                countries.add("Denmark");
                countries.add("Mexico");

                DropDownChoice<String> countryDDC = new DropDownChoice<String>("country", countries);
                countryDDC.setRequired(true);
                countryDDC.setDefaultModel(new PropertyModel<Address>(address, "country"));
                add(countryDDC);
                form.add(countryDDC);
        		
        		CheckBox isWorkAddressCB = new CheckBox("isWorkAddress", new PropertyModel<Boolean>(addressItem.getModel(), "isWorkAddress"));
        		isWorkAddressCB.setDefaultModel(new PropertyModel<Address>(address, "isWorkAddress"));
        		addressItem.add(isWorkAddressCB);
        		form.add(isWorkAddressCB);
        		
        		// Add a link to remove the chosen address
        		addressItem.add(removeAddressLink("removeAddress", address));
        		
        		addressItem.add(form);
        	}
        });
        
        // Page navigator for the pageable listview
        add(new PagingNavigator("pageNavigator", listView));
        
        add(addAddressLink("addAddressLink", contact));
        add(backLink("backLink"));
        
	}
	
	public static Link<Void> backLink(final String name) {
    	
        return new Link<Void>(name) {
            /**
             * @see org.apache.wicket.markup.html.link.Link#onClick()
             */
            @Override
            public void onClick() {
                setResponsePage(new HomePage());
            }
        };
    }
	
	public static Link<Void> addAddressLink(final String name, final Contact contact) {
    	
        return new Link<Void>(name)
        {
            /**
             * @see org.apache.wicket.markup.html.link.Link#onClick()
             */
            @Override
            public void onClick()
            {
            	setResponsePage(new AddAddress(contact));
            }
        };
    }
	
	public static Link<Void> removeAddressLink(final String name, final Address address) {
    	
        return new Link<Void>(name)
        {
            /**
             * @see org.apache.wicket.markup.html.link.Link#onClick()
             */
            @Override
            public void onClick()
            {
            	setResponsePage(new RemoveConfirmation(address, address.getStreet()));
            }
        };
    }
	
	public static class UpdateAddressForm extends Form<Address> {

		public UpdateAddressForm(String id, IModel<Address> addressModel, Contact contact) {
			super(id, addressModel);
		}
		
		@Override
		public  void onSubmit() {

			final Address a = getModelObject();
			final ContactDAO contactDAO = new ContactDAOImpl();
			contactDAO.updateAddress(a);
			
			// A message stored with getSession().info("") gets automatically picked up by the target page's feedback panel:
			getSession().info("Address saved!");
		}
	}
	
	@SuppressWarnings("serial")
	public static final class EditContactForm extends Form<Contact> {

		private TextField<String> firstNameField;
		private TextField<String> lastNameField;
		private DateTextField dateOfBirthField;

		public EditContactForm(final String id, IModel<Contact> contactModel) {
			super(id, contactModel);

			// Contact text fields:
			firstNameField = new TextField<String>("firstName");
			firstNameField.setRequired(true);
			firstNameField.add(StringValidator.maximumLength(30));
			add(firstNameField);

			lastNameField = new TextField<String>("lastName");
			lastNameField.setRequired(true);
			lastNameField.add(StringValidator.maximumLength(30));
			add(lastNameField);

			dateOfBirthField = new DateTextField("dateOfBirth",
					new PatternDateConverter("MM/dd/yyyy", false));
			
			// Add a date picker to the date of birth field:
			DatePicker datePicker = new DatePicker();
		    datePicker.setShowOnFieldClick(true);
			datePicker.setAutoHide(true); dateOfBirthField.add(datePicker);
			 
			add(dateOfBirthField);
		}

		@Override
		public final void onSubmit() {

			final Contact contact = getModelObject();
			final ContactDAO contactDAO = new ContactDAOImpl();
			contactDAO.updateContact(contact);
			
			info("Contact '"+contact.getFirstName()+"' saved!");
		}

	}
}