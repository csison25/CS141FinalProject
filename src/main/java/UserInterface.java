import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Utility methods for building and styling JavaFX controls.
 */
class UserInterface {

    /**
     * Creates a label for use in the application UI.
     *
     * @param prompt the text to display in the label
     * @return a configured Label instance
     */
    public static Label createLabel(final String prompt) {
        final Label label = new Label(prompt);
        return label;
    }

    /**
     * Creates a text field for collecting user input.
     *
     * @param prompt the prompt text to display inside the field
     * @return a configured TextField instance
     */
    public static TextField createATextField(final String prompt) {
        final TextField textField = new TextField();
        textField.setMaxWidth(200);
        textField.setPromptText(prompt);
        return textField;
    }

    /**
     * Creates the label used to display result messages in the UI.
     *
     * @return a configured Label for result messages
     */
    public static Label resultsText() {
        final Label result = new Label();
        result.setPadding(new Insets(10,0,0,0));
        return result;
    }

    /**
     * Creates the assignment type dropdown menu.
     *
     * @return a ComboBox pre-populated with assignment types
     */
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

    /**
     * Creates a button with the provided label text.
     *
     * @param prompt the button text
     * @return a configured Button instance
     */
    public static Button createButton(final String prompt) {
        final Button button = new Button();
        button.setText(prompt);
        return button;
    }

    /**
     * Applies standard spacing, padding, and styling to a VBox.
     *
     * @param box the VBox to configure
     */
    public static void configureVBoxStyling(final VBox box) {
        box.setSpacing(10);
        box.setPadding(new Insets(50,0,0,0));
        box.setAlignment(Pos.TOP_CENTER);
        box.setStyle("-fx-background-color: #f4f4f4; -fx-font-family:'Helvetica';");
    }

    /**
     * Applies standard spacing and alignment to an HBox.
     *
     * @param box the HBox to configure
     */
    public static void configureHBoxStyling(final HBox box) {
        box.setSpacing(80);
        box.setAlignment(Pos.CENTER);
    }

    /**
     * Resets the results label text color to the default style.
     *
     * @param results the label whose style should be reset
     */
    public static void errorTextConfiguration(final Label results) {
        results.setStyle("-fx-text-fill: black");
    }
}
