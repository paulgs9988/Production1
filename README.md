PRODUCTION PROJECT by Paul Sullivan

Description:
This project is a demonstration of Object Oriented Programming in Java and was built throughout the Fall of 2020 and is intended to be continually
enhanced and improved going forward. It is a simple project that interacts with two SQL databases and allows a user to add new "Products" to a database,
and then allows him/her to create "Production Runs" that are documented in another database. There is also an Employee tab that currently has limited
functionality, but enables a user to create his/her username and email address and enter a desired password.

Visuals:
Please see the "docs" folder in the Githup Repository for gif files of the program running (i.e. Production_produce_productionlog_tabs.gif).

Installation:
Currently, to use this program, the user must clone/pull the project from Github and run it using an IDE. The program was written in IntelliJ and 
uses Gradle and SQL databases.

Usage:
The program opens displaying the "Employee" Tab. The user can enter his/her first and last name and a desired password (Password must contain at least
one capital letter, one lowercase letter, and one special character). After clicking "Submit," the user is given the created username, email address,
and the entered password is displayed if all criteria are properly met.
The next tab is the "Product Line" tab. Here, the user can enter "Products" into a database that is displayed at the bottom of the tab. The user must enter
the Product's Name, Manufacturer, and choose its Type from the drop menu and click "Submit." After, the Product is added to the PRODUCT Database.
The next tab is the "Produce" tab. In this tab, the user can select a Product from the List View, and enter a desired quantity for production. After clicking
Submit, a "Production Record" is made that documents which Product was created, how many were created, and saves the Serial Numbers for each Product and the
Date of production. This information is displayed in the "Production Log" tab along with the information relating to all past Production Records.

Support:
For any questions or for issues relating to this program, please email Paul at psullivan4467@eagle.fgcu.edu

Roadmap:
There are many obvious improvements and additions that will be made to this program, including but not limited to the following:
-Enhancement of User Interface aesthetics
-Ability to create unique instances of Products (For example, MoviePlayer and AudioPlayer objects will be created instead of just general "Products")
-The Employee Tab will include encryption and a new way will be devised to give the user his or her password instead of displaying it on the screen (additionally
 when the user enters his desired password, it could come up as asterisks for additional security)
-A more efficient way of creating serial numbers and tracking them will be employed

Contributing:
Anyone who is interested in contributing to this project is happily welcome to and you can contact Paul at psullivan4467@eagle.fgcu.edu to talk about
working together on this or any other projects.

Authors and acknowledgment:
Project created by Paul Sullivan under the guidance of Professor Vanselow at FGCU. I received assistance from Professor Vanselow and some TAs, most
notably Vlad Hardy. Additionally, I did garner help from Stack Overflow, for example, in employing a series of different kind of notifications, I
found the following helpful: https://stackoverflow.com/questions/55173323/how-do-i-create-a-popup-message-to-alert-the-user-of-incomplete-fields
I also found the various video tutorials by "Bucky" posted under the username "thenewboston" on Youtube to be very helpful throughout the development
of this project. Finally, I also referenced various other sources such as geeksforgeeks.com and the Oracle Academy in developing this project and learning
techniques to accomplish certain desired outcomes.

License:
There are no licensing requirements for interacting with this program in any way.

Project status:
This project will continually be developed at least until the above Roadmap items are accomplished.
