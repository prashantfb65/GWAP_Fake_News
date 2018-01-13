__________________________________________________________________________________________

GWAP Application - Fake News Version 1.0 21/04/2017
__________________________________________________________________________________________


------------------------------------------------------------------------------------------
General Usage Notes
------------------------------------------------------------------------------------------
- This documents will help you to run Fake News GWAP application
- The GWAP application is developed using JAVA FX 
- The data collection is done using Web API and CSV data retrieval technique of Python
- The Game simply acts as an classifier to classify the news into Biased, Conspiracy, Satire 
	and Genuine categories
- The Game is interesting and has Sound Effects to attract the players
- The Game helps the system to identify the Sources which emit Fake News too.
- The application consists of following Screens
	1. Login Screen
	2. Register Screen
	3. Game Startup Screen
	4. Game Play Screen
	
	The game also contains a Bonus Questions for Reputated Users after each round.
	With every Round of Play, the time to complete the level decreases, and the penalty  
	increases in the form of loss of energy
	
- If the user runs out of energy he can't continue playing the game untill his energy refills
- MySQL server 5.7 is used as the back-end for the project	


------------------------------------------------------------------------------------------
System Requiements:
-------------------------------------------------------------------------------------------
- Java JRE 8 installed [ Mandatory ]

- Java JRE 8 system Requirement for different operating systems can be found 
  from the below link
	https://www.java.com/en/download/help/sysreq.xml
	
	
- Eclipse IDE with Java FX extension [ Mandatory ]
	https://www.eclipse.org/efxclipse/install.html
  
  Note: It is mandatory to load this extension to run Java FX application in eclipse
  
- MySQL Server 5 or Upper [ Mandatory ]
- Anaconda 4.3.0  with Python 3.6.0  [ Mandatory ]
- After Installation of pyprind and pymysql as they are not present in Anaconda by default
  
  Windows Command [cmd]:  [ Mandatory ]
  -----------------
  1. pip install pyprind
  2. pip install pymysql


---------------------------------------------------------------------------------------------
Steps to complete before Importing the source code and Running the Executable
---------------------------------------------------------------------------------------------
  
Step 1: (Optional) 
		
		News Articles (Fake, Genuine and News with unknown category) are retrieved 
		using iPython Notebooks. This step is optional as the data collected is already stored 
		in the database 'GWAP', table 'NEWS_DATA'. 
		
		We need to simply restore the dump sql files in order to access that data from the Application.
		The already ran source code of iPython Notebook can be found in the Folder "Python Source Code"
		
		In order to Run the code, the dataset and the iPython notebooks need to be imported from
		google drive via link.
		
		************************************************************************************
		https://drive.google.com/file/d/0B9RYT5NzhuwGOUhOV251dWJQUXc/view?usp=sharing
		************************************************************************************

		1. Download "GWAP - PYTHON CODE.zip"
		2. Extract the Zip File
		3. Open the command prompt or Terminal
		4. Navigate to the Location
			GWAP - PYTHON CODE > GWAP - PYTHON CODE > Python Notebooks > notebooks
		5. Type "jupyter notebook"
		6. The Web Browser will open 
		
		Notebook1:
		7. From the GUI click on "Collecting_Storing_Fake_News.ipynb" iPython notebook 
		8. Select Cell>Run All to Run the iPython
			This Notebook processes the Fake Data excel and 
			stores it in Database table "News_data"
		
		Notebook2
		9. From the GUI click on "Collecting_Storing_Test_News.ipynb" iPython notebook 
		10. Select Cell>Run All to Run the iPython
			This Notebook collects and processes News from Different sources
			using web api and stores it in the same table "News_data"
		
		Note: This step is optional as the collected data is already stored in MySQL database
		The other folder in the downloaded zip comprises of data set extracted using these iPython Notebooks

		
Step 2: (Mandatory)
		
		1. Install MySQL Server if not installed
		
		2. Import the Dump SQL Files from the Folder "MySQL Scripts"

			Files to reload are: 
				gwap_bonus_answer.sql
				gwap_news_data.sql
				gwap_routines.sql
				gwap_user_info.sql
				gwap_users.sql

			It does the following
				a. Create Database GWAP
				b. Create 4 tables: USERS, USER_INFO, NEWS_DATA, BONUS_ANSWER
				c. Create Trigger to insert data in USER_INFO, as soon as the Player Register himself
				d. Create Event to Refill the Energy after 24 hours
				e. Import Data of NEWS_DATA table
			
				
			
		
-------------------------------------------------------------------------------------------
Running Application and Importing Source Code
-------------------------------------------------------------------------------------------
1. Unzip the 16201447.zip file. (The Extracted Folder 16201447 will contains 
	4 sub-folders and a README file)

3. "Python Source Code" comprises of two html files which represent the code and output, when the 
	iPython notebooks were run
	
4. "MySQL Scripts" folder consists of sql dump files which need to be imported in order 
	to Run the Application
	
5. The "GWAP" folder within "Java Source Code" folder consists of actual Java Code Implementation and can be 
	imported as "Existing Projects into Workspace" in Eclipse or any other IDE
	
	The Source Code Structure is as Follows:
	
	a. Package [com.ci.fakenews] 
		Main.java 
			Main File for the GWAP Application - The Program Starts from here
		
	b. Package [com.ci.fakenews.bean]
		
		KeyValueDouble.java
			Pojo File handling String Key and Double Value Pair
			
		NewsDetails.java
			Pojo File handling News Data
			
		UserGameInfo.java
			Pojo File handling User Info
		
		UserInfo.java
			Pojo for User Login Data
		
	c. Package [com.ci.fakenews.comparor]
	
		CompareSort.java
			Implements Comparator Interface to sort Multiple String keys, Double value pairs
			
	d. Package [com.ci.fakenews.constant]
	
		Constant.java
			Stores Constants used in PlayGame controller Class
			
	d. Package [com.ci.fakenews.controller]
		
		LoginController.java
			This is the controller file for Login Window. All the actions taken from Login Window are 
			handled here
						  
		PlayGameController.java
			This is the controller file for Main Game Play Window. All the actions taken from Game Window are handled here.
						  
		RegisterController.java
			This is the controller file for Register Window. All the actions taken from Game Window are handled here.

		StartupScreenController.java
			This is the controller file for Startup Screen Window. All the actions taken from Game Startup Window are handled here.
						  
	e. Package [com.ci.fakenews.sdo]
	
		OperationFile.java
			Service Data Objects - Operation File. This file stores the methods which involve operation
			with database.
	
	f. Package [com.ci.fakenews.ui]
		FXML Files for Generating UI
		
	g. Package [com.jdbc.connection]
		
		ConnectionFactory.java
			This file provides the Application with JDBC connection to perform action on database tables
			by providing connection.
					
						  
						  
5. Running the application:	
	
	Locate the Main.java file in Package [com.ci.fakenews]
	Right Click on it and Run it as Application.
	
	Note: Text File "database_configuration.txt"  present in the folder consists JDBC configuration 
	parameter such as DB name, Username and Password
	
	Change the username and password
	The default username and password useed is root
	
	If the MySQL server you are using has different username and password
	change username and password in the file

6. The video "GWAP.mp4" is present in the "Screencast" folder presents the Game Play process


-------------------------------------------------------------------------------------------		
GWAP application can be reached at:
-------------------------------------------------------------------------------------------
Voice : 353-89211-3858
Web Site: http://www.ucd.ie/
E-mail: prashant.garg@ucdconnect.ie 
Linkedin: https://www.linkedin.com/in/prashantucd

-------------------------------------------------------------------------------------------