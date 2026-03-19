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
    Class for creating and styling the UI components
*/

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
