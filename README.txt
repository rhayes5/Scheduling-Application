Title and Purpose
-------------------------------------------------------------------------------------------------
Scheduling Applicaton

The purpose of this application is to schedule appointments for customers making sure that appointments do not overlap and are within business hours.  The user can add, update, and delete customers and appointments, all of which are updated in the database.  The application also keeps track of the contacts associated with the appointments and the user who created/updated the appointments or customers.

Author: Rychell Hayes
Contact Information: rhayes5@wgu.edu

Version: V1
Date: December 27, 2021

IDE: IntelliJ IDEA 2021.1.3 (Community Edition)
JDK: Java SE 11.0.13
JavaFX:JavaFX-SDK-11.0.2

--------------------------------------------------------------------------------------------------

Directions: 

Launch the application. 

Log in with username: test and password: test

The main menu will have a list of all current customers and appointments.  The top table is the customers table and the bottom is appointments.  To view appointments for a single customer, click on that customer in the customer table.  To remove your selection, click the "Clear Selection" button under the customer table.  Each table has Add, Update, and Delete buttons.  

Filter by Month or Week: Appointments can be filtered by month and week by clicking on the This Month and This Week radio buttons.  The month filter will show appointments for all customers in the current month.  The week filter will show appointments for all customers in a week starting from the current day.  You can view all appointments again by clicking on the All Dates radio button.

Search: The search box at the top of the page can be used to search for a customer name.  Enter at least part of the customer name to filter the results in the table.

Add Customer: Click the add button beneath the customer table.  All fields are required to add a new customer.  Add city information to the address field with the street address (example: 1234 Main St, London).  The first level division combo box will be available after you select a country.  Click "Save" when finished to add a new customer.  Click "Cancel" to return to the main menu.

Update Customer: Click on the customer to update in the table and click the update button beneath the customer table.  All fields are required to update a customer.  All current data on the customer will pre-populate.  The customer id field cannot be changed. Add city information to the address field with the street address (example: 1234 Main St, London).  The first level division combo box will upate if you select a different country.  Click "Save" when finished to update the customer.  Click "Cancel" to return to the main menu without saving changes.

Delete Customer: A customer can only be deleted if they have no appointments scheduled.  Delete any appointments for that customer before deleting the customer.  To delete a customer, click the customer in the customer table, then click delete below the customer table and confirm you wish to delete.  

Add Appointment: Click the add button beneath the appointment table.  All fields are required to add a new appointment.  Appointments must be scheduled within business hours and a customer cannot have overlapping appointments.  Click "Save" when finished to add a new appointment. Click "Cancel" to return to the main menu.

Update Appointment: Click on the appointment to update in the table and click the update button beneath the appointment table.  All fields are required to update an appointment. All current data on the appointment will pre-populate. The appointment id field cannot be changed.  Appointments must be scheduled within business hours and a customer cannot have overlapping appointments.  Click "Save" when finished to update the appointment. Click "Cancel" to return to the main menu.

Delete Appointment: To delete an appointment, click the appointment in the appointment table, then click delete below the appointment table and confirm you wish to delete.  

View Reports: Click the View Reports button at the bottom of the main menu.  You can view appontments by month, week, type, country, contact, and see login attempts.  Click the tabs at the top to switch between reports.  Click back to return to the main menu.

    Appointments by Month And Type: Enter a 4-digit year and select a month from the combo box,  select the type from the type combo box, and click search. The table will update to appointments of that type in that month and year and show the total number of appointments.

    Appointments by Dates: Select the beginning and ending dates in the date-pickers for the dates you'd like to view and then click Search.

    Appointments by Country: Select the country from the combo box and the table will update to appointments in that country and show the total number of appointments in that country

    Contact Schedule: Select the contact from the combo box and the table will update to appointments with that contact and show the total number of appointments for that contact.

    Login Attempts: See all login attempts, whether successful or failed, and dates/times.


A.3.f Description of Additional Report(s)

    Appointments by Dates: Allows the user to see appointments in any date range.  Select the beginning and ending dates in the date-pickers for the dates you'd like to view and then click Search.

Appointments by Country: Allows the user to see appointments by country, possibly to see which countries have the most success scheduling appointments.  Select the country from the combo box and the table will update to appointments in that country and show the total number of appointments in that country