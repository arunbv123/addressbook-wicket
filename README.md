    Address Book Web Application with Apache Wicket, Java, Hibernate and HSQL

This is a small web application build using with Apache Wicket Framework, Hibernate and HSQL. It contains two sub-projects and the HSQL database located in the following folders:

    - addressbook-wicket-ui: Renders the user interface in the web browser
    - addressbook-wicket-hibernate: Contains the hibernate data models and interfaces for 
      data manipulation
    - hsqldb: The database used by the address book

Before running the Wicket Address Book, the following requirements must be met:

    - Maven 3 must be installed
    - GIT must be installed
    - Tomcat Server must be installed (I use version 7.0.53)
    

To run the Wicket Address Book, follow these steps:

    1. Start the database by running startDB_addressbook-wicket-hibernate.bat in the 
       hsqldb folder
    2. git clone git@github.com:aydinseven/addressbook-wicket.git to checkout the project
    3. go into the addressbook-wicket folder
    4. type "mvn clean install" in the shell to build the project 
    5. Start your Tomcat Server and deploy the addressbook-wicket-ui web application
    6. Open the browser at http://localhost:8080/addressbook-wicket-ui
    
Once the application started you can register a new user and then login.
