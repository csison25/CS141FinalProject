/*
  TO RUN THIS PROGRAM
  mvn clean javafx:run
  Path to open in terminal: ~/dev_java/Ch04/javaFX/finalprojectfx
  Author: Cj Sison
*/
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.collections.*;
import java.io.*;
import javafx.geometry.*;
import java.nio.file.*;

public class FinalProject extends Application {
    private GradeResults record;//Creation of the GradeResults object 'record' ,
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
        Label header = UserInterface.createLabel("This program checks for the a needed grade on an\nassignment \\ test \\ exam");

        Label headerClassName = UserInterface.createLabel("Enter the Class's Type: (e.g. CIS)");
        TextField className = UserInterface.createATextField("Enter Class Type");//user input

        Label headerDropMenu = UserInterface.createLabel("Select Assignment Type: ");
        ComboBox<String> comboBox = UserInterface.createComboBox();//user input, has defined selections for the user to choose

        Label headerTaskName = UserInterface.createLabel("Enter the Assignment's Name:");
        TextField taskName = UserInterface.createATextField("Enter Assignment Name");//user input

        Label headerCurrentGrade = UserInterface.createLabel("Enter your current Grade: (e.g. 99)");
        TextField currentGrade = UserInterface.createATextField("Enter current grade");//user input

        Label headerPercentWeight = UserInterface.createLabel("Enter weight in percent: (e.g. 18)");
        TextField percentWeight = UserInterface.createATextField("Enter weight in percent");//user input

        Label headerWantedGrade = UserInterface.createLabel("Enter desired final grade: (e.g. 95)");
        TextField wantedGrade = UserInterface.createATextField("Enter grade wanted");//user input

        Button calcButton = UserInterface.createButton("Calculate");//button that does the calculations of the application
        Button saveButton = UserInterface.createButton("Save");//button to save the calculated information to a text file
        Label results = UserInterface.resultsText();//Label that communicates calculated information to the user

        HBox buttonBox = new HBox();//allows the buttons to be next to each other rather than stacked on top one another
        UserInterface.configureHBoxStyling(buttonBox);
        buttonBox.getChildren().addAll(calcButton, saveButton);
        VBox displayOrder = new VBox();//Creates the Vertical layout of the application
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
      public static GradeResults performCalculation(Validate userInput, Label results) {

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
          GradeResults myResults = new GradeResults(userInput.getCourseName(), userInput.getAssignmentName(), userInput.getDropMenuSelect(), userGrade, examWeight, userWantedGrade, grade, letterGrade);
          results.setStyle(GradeCalculator.getPercentStyling(grade));//Styling for displayed text of the resulsting calculation
          results.setText(myResults.toString());//prints the calculation to the screen
          return myResults;
    }

    //method used to save user input and calculations to a text file, files take the name of the course given by the user
    public static void saveToFile(GradeResults record, Label results) {
        String filename = record.getClassName() + ".txt";
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

//class that has the methods that deal with manipulating the user data, method that does the calculations
class GradeCalculator {

    //method for calculating a needed grade on an assignment to achieve a desired final grade
    public static double gradeCalc(double current, double weight, int want){
        double percentGrade = (want - ((1 - (weight / 100.0)) * current)) / (weight / 100.0);
        Validate.gradeRangeChecker(percentGrade);
        return percentGrade;
    }

    //method for determining the letter grade associated to the calculated percentGrade from the method gradeCalc()
    public static String letterCalc(double percent){
        String letter;
        if (percent >= 90) {
            letter = "A";
        } else if (percent >= 80) {
            letter = "B" ;
        } else if (percent >= 70) {
            letter = "C";
        } else if (percent >= 60) {
            letter = "D" ;
        } else {
            letter = "F";
        }
        return letter;
    }

    //method for styling (changing color) of the Label 'results', based on pass/fail
    public static String getPercentStyling(double percent) {
        if (percent >= 60.0) {
            return "-fx-text-fill: green;";
        } else {
            return "-fx-text-fill: red;";
        }
    }
}

//class that holds the calculated information based off the user's input
class GradeResults {
    //Class to create the Grade object
    private String className;
    private String assignmentName;
    private String assignmentType;
    private double userGrade;
    private double examWeight;
    private int userWantedGrade;
    private double grade;
    private String letterGrade;

    //method that constructs the objects with the given values
    public GradeResults (
        String className,
        String assignmentName,
        String assignmentType,
        double userGrade,
        double examWeight,
        int userWantedGrade,
        double grade,
        String letterGrade) {
            this.className = className;
            this.assignmentName = assignmentName;
            this.assignmentType = assignmentType;
            this.userGrade = userGrade;
            this.examWeight = examWeight;
            this.userWantedGrade = userWantedGrade;
            this.grade = grade;
            this.letterGrade = letterGrade;
    }

    //accessor method for getting the className value
    public String getClassName(){
        return className;
    }

    //method for formatting output to be displayed on the UI
    public String toString() {
        return
                "For your " + className + " \"" + assignmentName + "\" " + assignmentType +
                "\nYou need to get a(n): " + String.format("%.2f%%",grade) +
                "\nWhich is a(n): " + letterGrade +
                "\nFor a final grade of: " + userWantedGrade;
    }

    //method for formatting the output to be saved to a file
    public String toFile() {
        return
                "Class Type: " + className +
                " | Assignment Name: " + assignmentName +
                " | Assignment Type: " + assignmentType +
                " | Current Grade: " + String.format("%.2f%%",userGrade) +
                " | Needed Grade: " + String.format("%.2f%%",grade) +
                " | Letter Grade: " + letterGrade +
                " | Target Grade: " + userWantedGrade;
    }
}

//class that checks for proper user input
class Validate {
    private String courseName;
    private String assignmentName;
    private String dropMenuSelect;
    private String userGradeString;
    private String examWeightString;
    private String userWantedGradeString;

    //method that constructs the objects with the given values
    public Validate(TextField course, TextField assignment, ComboBox<String> drop,TextField grade, TextField exam, TextField user) {
        this.courseName = course.getText().trim().toUpperCase();
        fileNameChecker(this.courseName);
        this.assignmentName = assignment.getText();
        this.dropMenuSelect = drop.getValue();
        this.userGradeString = grade.getText();
        this.examWeightString = exam.getText();
        this.userWantedGradeString = user.getText();
    }

    //accessor method for a value of the object
    public String getCourseName() {
        return courseName;
    }

    //accessor method for a value of the object
    public String getAssignmentName() {
        return assignmentName;
    }

    //accessor method for a value of the object
    public String getDropMenuSelect() {
        return dropMenuSelect;
    }

    //accessor method for a value of the object
    public Double getUserGradeString() {
        return Double.parseDouble(userGradeString);
    }

    //accessor method for a value of the object
    public Double getExamWeightString() {
        return Double.parseDouble(examWeightString);
    }

    //accessor method for a value of the object
    public Integer getUserWantedGradeString(){
        return Integer.parseInt(userWantedGradeString);
    }

    //accessor method checks for null input by the user
    public static boolean nullChecker(Validate input, Label results) {
        if( input.courseName.isEmpty() ||
            input.assignmentName.isEmpty() ||
            input.dropMenuSelect == null ||
            input.userGradeString.isEmpty() ||
            input.examWeightString.isEmpty() ||
            input.userWantedGradeString.isEmpty()) {
            results.setText("Please fill in all fields properly");
            return false;
        }
        return true;
    }

    //accessor method that checks for valid grade ranges, Ex: Cannot get more than 100% on a test
    public static void gradeRangeChecker(double percentGrade) {
        if (percentGrade > 100.0) {
            throw new IllegalArgumentException("Not possible with current grade, will need over 100% on final.");
        } else if (percentGrade < 0) {
            throw new IllegalArgumentException("Not possible, will need a negative percent on the final.");
        }
    }

    //accessor method, ensures that the course name given by the user has only valid characters for file naming
    public static void fileNameChecker(String courseName) {
        if (!courseName.matches("[a-zA-Z0-9_-]+")) {
            throw new IllegalArgumentException("Invalid file name. Only letters, numbers, spaces, _ and - allowed.");
        }
    }
}

//Class for creating and styling the UI components
class UserInterface {

    //method that creates all Labels for guiding the users input, displayed in the UI
    public static Label createLabel(String prompt) {
        Label label = new Label(prompt);
        return label;
    }

    //method that creates all the TextFields displayed in the UI, takes in the users input
    public static TextField createATextField(String prompt) {
        TextField textField = new TextField();
        textField.setMaxWidth(200);
        textField.setPromptText(prompt);
        return textField;
    }

    //method for creating the Label that displays the results of the application to the user
    public static Label resultsText() {
        Label result = new Label();
        result.setPadding(new javafx.geometry.Insets(10,0,0,0));
        return result;
    }

    //method that creates the drop down menu, has predefined values for the user to choose from
    public static ComboBox<String> createComboBox() {
        ComboBox<String> boxMenu = new ComboBox<>();
        boxMenu.setPromptText("Select Type");
        boxMenu.getItems().addAll(//assignment options
            "Homework",
            "Quiz",
            "Test",
            "Exam");
        return boxMenu;
    }

    //method for creating the buttons for the UI
    public static Button createButton (String prompt){
        Button button = new Button();
        button.setText(prompt);
        return button;
    }

    //method that styles the VBox of the UI
    public static void configureVBoxStyling(VBox box) {
        box.setSpacing(10);
        box.setPadding(new javafx.geometry.Insets(50,0,0,0));
        box.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        box.setStyle("-fx-background-color: #f4f4f4; -fx-font-family:'Helvetica';");
    }

    //method that styles the HBox of the UI
    public static void configureHBoxStyling(HBox box) {
        box.setSpacing(80);
        box.setAlignment(javafx.geometry.Pos.CENTER);
    }

    /*
    method that restyles the reults text whenever needed, method need after calculating a grade
    calculations of the application changes results text color
    the method getPercentStyling() changes the color of results depending on the calculated grade. location: GradeCalculator
    */

    public static void errorTextConfiguration (Label results) {
        results.setStyle("-fx-text-fill: black");
    }
}
