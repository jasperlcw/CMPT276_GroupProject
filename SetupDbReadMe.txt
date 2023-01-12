This application requires a database that is hosted externally outside of this app to function.
Due to a lack of free services for hosting of MySQL servers that support external communications, for this project, the MySQL database will have to be stored on a locally hosted server.
This server will run on the local machine, and the Android program will connect to the server through localhost on port 3308.
There is a file called cmpt_276.sql on the root directory of the Iteration_2 branch (the submission branch). This file contains the MySQL necessary to set up the database.

Setup on Windows of Wampserver to store the database:
For Windows, the group has opted to use Wampserver to host a local server to store the database requried for the application to function.
Wampserver uses phpMyAdmin as a user interface for MySQL. Once Wampserver is installed, on your internet browser type in "localhost". This will bring you to the homepage of Wampserver.
On the homepage of Wampserver, locate "phpmyadmin" on the left side of the page and click on it. Alternatively you can type in "localhost/phpmyadmin/". This will prompt a login page.
To login, the username is root, and the password is blank (no password). Make sure server choice is set to MySQL as it uses port 3308 on default.
Once logged in, on the top navigation bar, you will see a button called "Import" in the middle. Clicking on it will bring you to a screen where it will prompt you to upload a .sql file.
Download cmpt_276.sql from the root directory of the submission branch and upload to phpMyAdmin, then press the "Go" button at the bottom.
The MySQL will then be set up for the application.

Setup of XAMPP on Windows, Mac, Linux:
XAMPP is a package that contains phpMyAdmin for MySQL that is available on Windows, Mac, and Linux. XAMPP can be downloaded from https://www.apachefriends.org/index.html.
Then follow the instructions listed for how to change the port for MySQL to 3308 in the following link: https://iqubex.com/softwares/change-xampp-server-ports-mysql-apache/
Please refer to the section for Wampserver regarding importing the SQL database, since both applications use phpMyAdmin to manage MySQL.
