# My Personal Project
This application will run as an exclusive online 
housing market for both renting and listings. The usage
is directed to both potential tenants and owners, where
tenants can browse for available housings from anywhere.
Listings will show location, a provided image and base 
pricing in (rent/month). I think this is a project I can
dedicate my interests to as housing is not only an issue
I face, but many other UBC students as well. Be it rent
is too high, or searching across many websites/services
is too tedious and occasionally outdated. This will also 
to keep track of when rent is due, or for potential
tenants;when house viewings have been scheduled.

## A subtitle

A *bulleted* list:
- item 1
- item 2
- item 3

An example of text with **bold** and *italic* fonts. 

## User Stories
- As a user, I want to be able to add a house viewing to my schedule
- As a user, I want to be able to remove a viewing from my schedule
- As a user, I want to be able to see how many viewings I have 
in my schedule
- As a user, I want to be able to efficiently compare prices 
and locations between two housings
- As a user, I want to be able to safe my schedule of appointments
- As a user, I want to be able to reload a pre-existing schedule that I saved previously


# Instructions for Grader

- You can generate the first required event related to adding Xs to a Y by filling in the required
text fields next to the "Add Appointment button" then pressing
the button to add a new Appointment to the user schedule
- You can generate the second required event related to adding Xs to a Y by filling in the field
next to the Remove Appointment button, then pressing the button
to remove an appointment with the specified ID from our schedule
- You can locate my visual component by looking at the main background of the JFrame
or alternatively locating the source image through: Project/src/main/ui/HousingBackground.png
- You can save the state of my application by pressing the "Save Current State" button at the 
bottom right of the window.
- You can reload the state of my application by pressing the "Load Previous State" button also
at the bottom right of the window.

# Phase 4: Task 2
Log example featuring adding and removing appointment with ID:"abc"
Thu Dec 01 22:57:17 PST 2022
Added new appointment with assigned ID: abc

Thu Dec 01 22:57:21 PST 2022
Removed appointment with associated ID: abc

# Phase 4: Task 3

Possible Improvements:
- Guarding user inputs with exception handling
- For ease of visually resizing the window, change the 
background image coordinates, to match a scalar of the frame's x and y
coordinates
- In regard to logging events, I was only able to implement the bare
minimum of removing or adding x from/to y. With JSON
loading or saving states, it should also be logged when
a user exits the application.
- As a final and more realistic refactor, as a user I would
like to be able to run multiple instances rather than
constantly overwriting over the previously saved data, perhaps a login
method or simply the option to switch users in the application.


