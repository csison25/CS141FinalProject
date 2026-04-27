/*
  TO RUN THIS PROGRAM
  mvn clean javafx:run
  Path to open in terminal: ~/dev_java/Ch04/javaFX/finalprojectfx
  Author: Cj Sison
*/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FinalProject extends Application {
    private GradeResults record = null;//Creation of the GradeResults object 'record' ,
    public static void main(String[] args) {
        launch();//launches the application
    }

    @Override
    public void start(Stage stage) {
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/calculator.png")));//https://www.flaticon.com/free-icon/calculator_10806845?related_id=10806845&origin=search
        stage.setTitle("Grade Checker");//title of the application

        /*
        All Labels , TextFields, Buttons , ComboBox are created using a helper method(s) in the UserInterface Class (Last class listed in the file)
        Each item listed in start() method, have the input that go into each of the defined types, easier to edit and view information that will be displayed by the application
        */
        final Label header = UserInterface.createLabel("This program checks for the a needed grade on an\nassignment \\ test \\ exam");

        final Label headerClassName = UserInterface.createLabel("Enter the Class's Type: (e.g. CIS)");
        final TextField className = UserInterface.createATextField("Enter Class Type");//user input

        final Label headerDropMenu = UserInterface.createLabel("Select Assignment Type: ");
        final ComboBox<String> comboBox = UserInterface.createComboBox();//user input, has defined selections for the user to choose

        final Label headerTaskName = UserInterface.createLabel("Enter the Assignment's Name:");
        final TextField taskName = UserInterface.createATextField("Enter Assignment Name");//user input

        final Label headerCurrentGrade = UserInterface.createLabel("Enter your current Grade: (e.g. 99)");
        final TextField currentGrade = UserInterface.createATextField("Enter current grade");//user input

        final Label headerPercentWeight = UserInterface.createLabel("Enter weight in percent: (e.g. 18)");
        final TextField percentWeight = UserInterface.createATextField("Enter weight in percent");//user input

        final Label headerWantedGrade = UserInterface.createLabel("Enter desired final grade: (e.g. 95)");
        final TextField wantedGrade = UserInterface.createATextField("Enter grade wanted");//user input

        final Button calcButton = UserInterface.createButton("Calculate");//button that does the calculations of the application
        final Button saveButton = UserInterface.createButton("Save");//button to save the calculated information to a text file
        final Label results = UserInterface.resultsText();//Label that communicates calculated information to the user

        final HBox buttonBox = new HBox();//allows the buttons to be next to each other rather than stacked on top one another
        UserInterface.configureHBoxStyling(buttonBox);
        buttonBox.getChildren().addAll(calcButton, saveButton);
        final VBox displayOrder = new VBox();//Creates the Vertical layout of the application
        UserInterface.configureVBoxStyling(displayOrder);
        displayOrder.getChildren().addAll(
              header,
              headerClassName,
              className,
              headerDropMenu,
              comboBox,
              headerTaskName,
              taskName,
              headerCurrentGrade,
              currentGrade,
              headerPercentWeight,
              percentWeight,
              headerWantedGrade,
              wantedGrade,
              buttonBox,
              results);

        Scene scene = new Scene(displayOrder, 500,600);// Creates the scene for the application to be displayed on
        stage.setScene(scene);
        stage.show();

        //an event set for the calcButton, Creates the Validate userInput object, which is used to check for valid inputs of user information
        calcButton.setOnAction( e -> {
            try{

                //stores the input data from the user, object creation
                Validate userInput = new Validate (className, taskName, comboBox, currentGrade, percentWeight, wantedGrade);
                record = performCalculation(userInput, results);// Supplies the data that goes into the GradeResults object 'record'
            } catch (Exception oopsies) {
                UserInterface.errorTextConfiguration(results);//this method resets the color of the text
                results.setText(oopsies.getMessage());
                record = null;
            }
            });

        //an event set for the saveButton, saves the data inputs of the GradeResults object 'record' to a file, uses a "toString" method called saveToFile to format the information in a string
        saveButton.setOnAction( e -> {
            if (record != null){
                saveToFile(record, results);
            }else {
                UserInterface.errorTextConfiguration(results);
                results.setText("Calculate a grade first!");
            }
        });
    }

      //method that returns a GradeResults object, Takes the validated user input, calculates the needed grade percent and letter grade of that percent to achieve a desired final grade
      //Change made 4/26/26: Changed the method to be private, as it is only used within the FinalProject class, and does not need to be accessed outside of it. This is an example of encapsulation, as it hides the implementation details of how the calculation is performed from other classes.
      private static GradeResults performCalculation(Validate userInput, Label results) {

          //ensures all fields are filled
          if (!Validate.nullChecker(userInput, results)){
              throw new IllegalArgumentException("Please fill all fields");
          }
          //retrieves the numeric values of the user input
          double userGrade = userInput.getUserGradeString();
          double examWeight = userInput.getExamWeightString();
          int userWantedGrade = userInput.getUserWantedGradeString();

          //performs calculations
          double grade = GradeCalculator.gradeCalc(userGrade, examWeight, userWantedGrade);//calculates the needed grade corresponding to user input
          String letterGrade = GradeCalculator.letterCalc(grade);//calculates the letter grade associated to the needed grade on an assignment

          //Stores the results of the information, object creation
          final GradeResults myResults = new GradeResults(userInput.getCourseName(), userInput.getAssignmentName(), userInput.getDropMenuSelect(), userGrade, examWeight, userWantedGrade, grade, letterGrade);
          results.setStyle(GradeCalculator.getPercentStyling(grade));//Styling for displayed text of the resulsting calculation
          results.setText(myResults.toString());//prints the calculation to the screen
          return myResults;
    }

    //method used to save user input and calculations to a text file, files take the name of the course given by the user
    public static void saveToFile(final GradeResults record, final Label results) {
        final String filename = record.getClassName() + ".txt";
        try{
            Files.writeString(
                Paths.get(filename),record.toFile() + "\n",
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
        UserInterface.errorTextConfiguration(results);
        results.setText("Saved to " + filename);
        } catch (IOException ex){
            UserInterface.errorTextConfiguration(results);
            results.setText("Error saving file.");
        }
    }
}
