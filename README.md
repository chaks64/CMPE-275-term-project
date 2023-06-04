# CMPE-275-term-project

## Architecture diagram

![WhatsApp Image 2022-05-15 at 3 03 27 PM](https://user-images.githubusercontent.com/20749933/170433142-bdc98680-7e0b-469f-b159-07802c32f653.jpeg)


## Tech Stack
Frontend: React.js Backend: Java with Spring Boot Database: MySql

## Features
1. Users as event participants and event organizers.
2. Email verification for users.
3. Google authentication login.
4. Creating and cancelling an event.
5. Searching an event with multiple filters.
6. Register for an event, get approval and pay fees.
7. Event discussion forum.
8. Separate forum for event participants.
9. Organizer and participants can review each other.
10. Analysis available in the form of User report and Organizer report

Instructions to run the application: 

1. Download the submitted zipped folder.
2. Unzip it to a root folder. ( After unzipping it should contain a folder CMPE-275-term-project, which should contain a folder for frontend) 
3. Open the terminal at frontend folder.
4. On the terminal, run "npm install". Wait for the installation of all the dependencies.
5. Once everything is installed, run "npm start"
6. Open another terminal at root folder, run mvn clean install.
7. It will create a target folder with a jar file.
8. To run, execute the following command by replacing the correct name for the generated jar file- "nohup java -jar demo-0.0.1-SNAPSHOT.jar.jar &"
10. Open browser and enter the URL as http://localhost:3000/ 
11. Attend events!
