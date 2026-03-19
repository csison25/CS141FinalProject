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

/*
    Class that checks for proper user input
*/

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
