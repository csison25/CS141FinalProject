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
    private GradeResults record;
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/calculator.png")));//https://www.flaticon.com/free-icon/calculator_10806845?related_id=10806845&origin=search
        stage.setTitle("Grade Checker");

        Label header = UserInterface.createLabel("This program checks for the a needed grade on an\nassignment \\ test \\ exam");

        Label headerClassName = UserInterface.createLabel("Enter the Class's Type: (e.g. CIS)");
        TextField className = UserInterface.createATextField("Enter Class Type");

        Label headerDropMenu = UserInterface.createLabel("Select Assignment Type: ");
        ComboBox<String> comboBox = UserInterface.createComboBox();

        Label headerTaskName = UserInterface.createLabel("Enter the Assignment's Name:");
        TextField taskName = UserInterface.createATextField("Enter Assignment Name");

        Label headerCurrentGrade = UserInterface.createLabel("Enter your current Grade: (e.g. 99)");
        TextField currentGrade = UserInterface.createATextField("Enter current grade");

        Label headerPercentWeight = UserInterface.createLabel("Enter weight in percent: (e.g. 18)");
        TextField percentWeight = UserInterface.createATextField("Enter weight in percent");

        Label headerWantedGrade = UserInterface.createLabel("Enter desired final grade: (e.g. 95)");
        TextField wantedGrade = UserInterface.createATextField("Enter grade wanted");

        Button calcButton = UserInterface.createButton("Calculate");
        Button saveButton = UserInterface.createButton("Save");
        Label results = UserInterface.resultsText();

        HBox buttonBox = new HBox();
        UserInterface.configureHBoxStyling(buttonBox);
        buttonBox.getChildren().addAll(calcButton, saveButton);
        VBox displayOrder = new VBox();
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

        Scene scene = new Scene(displayOrder, 500,600);
        stage.setScene(scene);
        stage.show();

        calcButton.setOnAction( e -> {
            try{
            Validate userInput = new Validate (className, taskName, comboBox, currentGrade, percentWeight, wantedGrade);
            record = performCalculation(userInput, results);
        } catch (Exception oopsies) {
            UserInterface.errorTextConfiguration(results);
            results.setText(oopsies.getMessage());
            record = null;
        }
        });
        saveButton.setOnAction( e -> {
            if (record != null){
                saveToFile(record, results);
            }else {
                UserInterface.errorTextConfiguration(results);
                results.setText("Calculate a grade first!");
            }
        });
    }
      public static GradeResults performCalculation(Validate userInput, Label results) {
          if (!Validate.nullChecker(userInput, results)){
              throw new IllegalArgumentException("Please fill all fields");
          }
          double userGrade = userInput.getUserGradeString();
          double examWeight = userInput.getExamWeightString();
          int userWantedGrade = userInput.getUserWantedGradeString();
          double grade = GradeCalculator.gradeCalc(userGrade, examWeight, userWantedGrade);
          String letterGrade = GradeCalculator.letterCalc(grade);
          GradeResults myResults = new GradeResults(userInput.getCourseName(), userInput.getAssignmentName(), userInput.getDropMenuSelect(), userGrade, examWeight, userWantedGrade, grade, letterGrade);
          results.setStyle(GradeCalculator.getPercentStyling(grade));
          results.setText(myResults.toString());
          return myResults;
    }
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
class GradeCalculator {
    public static double gradeCalc(double current, double weight, int want){
        double percentGrade = (want - ((1 - (weight / 100.0)) * current)) / (weight / 100.0);
        Validate.gradeRangeChecker(percentGrade);
        return percentGrade;
    }
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
    public static String getPercentStyling(double percent) {
        if (percent >= 60.0) {
            return "-fx-text-fill: green;";
        } else {
            return "-fx-text-fill: red;";
        }
    }
}
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
    public String getClassName(){
        return className;
    }
    public String toString() {
        return
                "For your " + className + " \"" + assignmentName + "\" " + assignmentType +
                "\nYou need to get a(n): " + String.format("%.2f%%",grade) +
                "\nWhich is a(n): " + letterGrade +
                "\nFor a final grade of: " + userWantedGrade;
    }
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
class Validate {
    private String courseName;
    private String assignmentName;
    private String dropMenuSelect;
    private String userGradeString;
    private String examWeightString;
    private String userWantedGradeString;

    public Validate(TextField course, TextField assignment, ComboBox<String> drop,TextField grade, TextField exam, TextField user) {
        this.courseName = course.getText().trim().toUpperCase();
        fileNameChecker(this.courseName);
        this.assignmentName = assignment.getText();
        this.dropMenuSelect = drop.getValue();
        this.userGradeString = grade.getText();
        this.examWeightString = exam.getText();
        this.userWantedGradeString = user.getText();
    }
    public String getCourseName() {
        return courseName;
    }
    public String getAssignmentName() {
        return assignmentName;
    }
    public String getDropMenuSelect() {
        return dropMenuSelect;
    }
    public Double getUserGradeString() {
        return Double.parseDouble(userGradeString);
    }
    public Double getExamWeightString() {
        return Double.parseDouble(examWeightString);
    }
    public Integer getUserWantedGradeString(){
        return Integer.parseInt(userWantedGradeString);
    }
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
    public static void gradeRangeChecker(double percentGrade) {
        if (percentGrade > 100.0) {
            throw new IllegalArgumentException("Not possible with current grade, will need over 100% on final.");
        } else if (percentGrade < 0) {
            throw new IllegalArgumentException("Not possible, will need a negative percent on the final.");
        }
    }
    public static void fileNameChecker(String courseName) {
        if (!courseName.matches("[a-zA-Z0-9_-]+")) {
            throw new IllegalArgumentException("Invalid file name. Only letters, numbers, spaces, _ and - allowed.");
        }
    }
}
class UserInterface {
    public static Label createLabel(String prompt) {
        Label label = new Label(prompt);
        return label;
    }
    public static TextField createATextField(String prompt) {
        TextField textField = new TextField();
        textField.setMaxWidth(200);
        textField.setPromptText(prompt);
        return textField;
    }
    public static Label resultsText() {
        Label result = new Label();
        result.setPadding(new javafx.geometry.Insets(10,0,0,0));
        return result;
    }
    public static ComboBox<String> createComboBox() {
        ComboBox<String> boxMenu = new ComboBox<>();
        boxMenu.setPromptText("Select Type");
        boxMenu.getItems().addAll(
            "Homework",
            "Quiz",
            "Test",
            "Exam");
        return boxMenu;
    }
    public static Button createButton (String prompt){
        Button button = new Button();
        button.setText(prompt);
        return button;
    }
    public static void configureVBoxStyling(VBox box) {
        box.setSpacing(10);
        box.setPadding(new javafx.geometry.Insets(50,0,0,0));
        box.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        box.setStyle("-fx-background-color: #f4f4f4; -fx-font-family:'Helvetica';");
    }
    public static void configureHBoxStyling(HBox box) {
        box.setSpacing(80);
        box.setAlignment(javafx.geometry.Pos.CENTER);
    }
    public static void errorTextConfiguration (Label results) {
        results.setStyle("-fx-text-fill: black");
    }
}
