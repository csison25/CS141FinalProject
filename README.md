# Grader Checker
#### Author: Cj Sison 
## Overview:  
Grade Checker is a JavaFX application that helps students determine the minimum grade they must earn on an assignment, quiz, test, or exam in order to achieve a desired final course grade. The application provides a GUI where the users can input their course information, current grade, assignement percent weight, and a desried final grade. The program calculates the required percent and its corresponding letter grade to achieve on the assignment to get the deisred final course grade. The results of the program can also be saved to a text file for future reference/review.
## Features:  
	- JavaFX GUI
	- Input validation for user data
	- Grade calculations based on percent weight
	- Letter grade conversion of the calculated grade
	- Results can be saved to a text file
	- Visual feedback using colored styling depending on the output
## Technology Used:
	- Java
	- JavaFX
	- Maven
## How to Run the program:  
### Option #1: Run with Maven
Navigate to the project's directory in terminal and run this command:  
	`mvn clean javafx:run`
### Option #2: Run via compiled JAR
Must have terminal open on correct folder and run this command:  
	`java -jar GradeChecker.jar`
## Project Inputs:
	- Course Name
	- Assignment Type
	- Assignment Name
	- Current Grade
	- Assignment Percent Weight
	- Desired Final Grade
The program then calculates the grade required on the assignment to achieve the desired final grade.
## Project Structure:  
JavaFX/finalprojectfx/src/main/java  
	- FinalProject.java  
	- Launcher.java  
	- UserInterface.java  
	- Validate.java  
	- GradeCalculator.java  
	- GradeResults.java  
	- ResultItem.java  
	- ResultItemCell.java  
	- ResultsListView.java  
  docs/  
	- classDiagramGradeChecker.png  
	- GradeCheckerClassRelations.png  
JavaFX/finalprojectfx/  
	- This is where the text files (.txt) will be saved to when you save something in the application 
## License:

