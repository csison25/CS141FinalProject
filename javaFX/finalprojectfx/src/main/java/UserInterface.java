import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/*
    Class for creating and styling the UI components
*/

class UserInterface {

    //method that creates all Labels for guiding the users input, displayed in the UI
    public static Label createLabel(final String prompt) {
        final Label label = new Label(prompt);
        return label;
    }

    //method that creates all the TextFields displayed in the UI, takes in the users input
    public static TextField createATextField(final String prompt) {
        final TextField textField = new TextField();
        textField.setMaxWidth(200);
        textField.setPromptText(prompt);
        return textField;
    }

    //method for creating the Label that displays the results of the application to the user
    public static Label resultsText() {
        final Label result = new Label();
        result.setPadding(new Insets(10,0,0,0));
        return result;
    }

    //method that creates the drop down menu, has predefined values for the user to choose from
    public static ComboBox<String> createComboBox() {
        final ComboBox<String> boxMenu = new ComboBox<>();
        boxMenu.setPromptText("Select Type");
        boxMenu.getItems().addAll(//assignment options
            "Homework",
            "Quiz",
            "Test",
            "Exam");
        return boxMenu;
    }

    //method for creating the buttons for the UI
    public static Button createButton(final String prompt){
        final Button button = new Button();
        button.setText(prompt);
        return button;
    }

    //method that styles the VBox of the UI
    public static void configureVBoxStyling(final VBox box) {
        box.setSpacing(10);
        box.setPadding(new Insets(50,0,0,0));
        box.setAlignment(Pos.TOP_CENTER);
        box.setStyle("-fx-background-color: #f4f4f4; -fx-font-family:'Helvetica';");
    }

    //method that styles the HBox of the UI
    public static void configureHBoxStyling(final HBox box) {
        box.setSpacing(80);
        box.setAlignment(Pos.CENTER);
    }

    /*
    method that restyles the reults text whenever needed, method need after calculating a grade
    calculations of the application changes results text color
    the method getPercentStyling() changes the color of results depending on the calculated grade. location: GradeCalculator
    */

    public static void errorTextConfiguration(final Label results) {
        results.setStyle("-fx-text-fill: black");
    }
}
