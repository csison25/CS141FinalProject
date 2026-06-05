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
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This class is the main class for running the program 
 */
public class FinalProject extends Application {
    private GradeResults record = null;
    private ObservableList<ResultItem> results;
    private ResultsListView resultsPanel;
    private Label messageLabel;

    /**
     * Launches the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Sets up the UI for the application 
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/calculator.png")));
        stage.setTitle("Grade Checker");

        // Initialize results list and panel
        results = FXCollections.observableArrayList();
        resultsPanel = new ResultsListView(results);

        // === LEFT PANEL: Input Controls ===
        final Label header = UserInterface.createLabel("This program checks for the a needed grade on an\nassignment \\ test \\ exam");
        
        final Label headerClassName = UserInterface.createLabel("Enter the Class's Type: (e.g. CIS)");
        final TextField className = UserInterface.createATextField("Enter Class Type");

        final Label headerDropMenu = UserInterface.createLabel("Select Assignment Type: ");
        final ComboBox<String> comboBox = UserInterface.createComboBox();

        final Label headerTaskName = UserInterface.createLabel("Enter the Assignment's Name:");
        final TextField taskName = UserInterface.createATextField("Enter Assignment Name");

        final Label headerCurrentGrade = UserInterface.createLabel("Enter your current Grade: (e.g. 99)");
        final TextField currentGrade = UserInterface.createATextField("Enter current grade");

        final Label headerPercentWeight = UserInterface.createLabel("Enter weight in percent: (e.g. 18)");
        final TextField percentWeight = UserInterface.createATextField("Enter weight in percent");

        final Label headerWantedGrade = UserInterface.createLabel("Enter desired final grade: (e.g. 95)");
        final TextField wantedGrade = UserInterface.createATextField("Enter grade wanted");

        final Button calcButton = UserInterface.createButton("Calculate");
        final Button saveButton = UserInterface.createButton("Save");
        messageLabel = UserInterface.resultsText();

        final HBox buttonBox = new HBox();
        UserInterface.configureHBoxStyling(buttonBox);
        buttonBox.getChildren().addAll(calcButton, saveButton);

        // LEFT PANEL: VBox containing all input controls
        final VBox inputPanel = new VBox();
        UserInterface.configureVBoxStyling(inputPanel);
        inputPanel.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 1;");
        inputPanel.setPrefWidth(320);
        inputPanel.getChildren().addAll(
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
            messageLabel
        );

        // === ROOT LAYOUT: HBox with LEFT (inputs) and RIGHT (results) ===
        final HBox rootLayout = new HBox();
        rootLayout.setSpacing(20);
        rootLayout.setPadding(new Insets(30));
        rootLayout.setAlignment(Pos.TOP_CENTER);
        rootLayout.getChildren().addAll(inputPanel, resultsPanel);
        HBox.setHgrow(resultsPanel, Priority.ALWAYS);

        // Center the root layout
        VBox centeredWrapper = new VBox();
        centeredWrapper.setAlignment(Pos.TOP_CENTER);
        centeredWrapper.getChildren().add(rootLayout);
        VBox.setVgrow(rootLayout, Priority.ALWAYS);

        Scene scene = new Scene(centeredWrapper, 900, 650);
        stage.setScene(scene);
        stage.show();

        // === EVENT HANDLERS ===
        
        // Calculate Button: Add result to list (NOT to file yet)
        calcButton.setOnAction(e -> {
            try {
                Validate userInput = new Validate(className, taskName, comboBox, currentGrade, percentWeight, wantedGrade);
                record = performCalculation(userInput, messageLabel);
                if (record != null) {
                    results.add(new ResultItem(record));
                    messageLabel.setText("Result added to list. Click 'Save' to persist to file.");
                    UserInterface.errorTextConfiguration(messageLabel);
                }
            } catch (Exception ex) {
                UserInterface.errorTextConfiguration(messageLabel);
                messageLabel.setText(ex.getMessage());
                record = null;
            }
        });

        // Save Button: Persist current record to file
        saveButton.setOnAction(e -> {
            if (record != null) {
                saveToFile(record, messageLabel);
            } else {
                UserInterface.errorTextConfiguration(messageLabel);
                messageLabel.setText("Calculate a grade first!");
            }
        });

        // Delete Selected Button: Remove checked items from list AND file
        resultsPanel.getDeleteSelectedBtn().setOnAction(e -> {
            List<ResultItem> toDelete = results.filtered(item -> item.isSelected()).stream().toList();
            if (toDelete.isEmpty()) {
                showInfoDialog("No Selection", "Please check items to delete.");
                return;
            }

            for (ResultItem item : toDelete) {
                try {
                    ResultPersistence.deleteResultFromFile(item.getGradeResults());
                    results.remove(item);
                } catch (Exception ex) {
                    showErrorDialog("Delete Failed", "Could not delete result: " + ex.getMessage());
                }
            }

            if (!toDelete.isEmpty()) {
                messageLabel.setText("Deleted " + toDelete.size() + " result(s) from list and file.");
                UserInterface.errorTextConfiguration(messageLabel);
            }
        });

        // Delete All Button: Clear list and associated files
        resultsPanel.getDeleteAllBtn().setOnAction(e -> {
            if (results.isEmpty()) {
                showInfoDialog("Empty List", "No results to delete.");
                return;
            }

            for (ResultItem item : results) {
                try {
                    ResultPersistence.deleteResultFromFile(item.getGradeResults());
                } catch (Exception ex) {
                    showErrorDialog("Delete Failed", "Could not delete result: " + ex.getMessage());
                }
            }
            results.clear();
            messageLabel.setText("All results deleted from list and files.");
            UserInterface.errorTextConfiguration(messageLabel);
        });

        // Import Results Button: Load results from file
        resultsPanel.getImportResultsBtn().setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Result File to Import");
            fileChooser.setInitialDirectory(new java.io.File(System.getProperty("user.dir")));
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
            );

            java.io.File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                try {
                    List<GradeResults> importedResults = ResultPersistence.parseExistingResultsFile(selectedFile.getName());
                    for (GradeResults result : importedResults) {
                        results.add(new ResultItem(result));
                    }
                    messageLabel.setText("Imported " + importedResults.size() + " result(s) from " + selectedFile.getName());
                    UserInterface.errorTextConfiguration(messageLabel);
                } catch (Exception ex) {
                    showErrorDialog("Import Failed", "Could not import results: " + ex.getMessage());
                }
            }
        });
    }

    /**
     * Calculates the needed assignment grade based on validated user input and updates the UI label.
     *
     * @param userInput the validated user input values
     * @param results   the label used to display the calculation result
     * @return a GradeResults instance containing the computed values
     */
    private static GradeResults performCalculation(Validate userInput, Label results) {
        if (!Validate.nullChecker(userInput, results)) {
            throw new IllegalArgumentException("Please fill all fields");
        }

        double userGrade = userInput.getUserGradeString();
        double examWeight = userInput.getExamWeightString();
        int userWantedGrade = userInput.getUserWantedGradeString();

        double grade = GradeCalculator.gradeCalc(userGrade, examWeight, userWantedGrade);
        String letterGrade = GradeCalculator.letterCalc(grade);

        final GradeResults myResults = new GradeResults(
            userInput.getCourseName(),
            userInput.getAssignmentName(),
            userInput.getDropMenuSelect(),
            userGrade,
            examWeight,
            userWantedGrade,
            grade,
            letterGrade
        );
        results.setStyle(GradeCalculator.getPercentStyling(grade));
        results.setText(myResults.toString());
        return myResults;
    }

    /**
     * Appends the current grade result to a text file based on the class name.
     *
     * @param record       the result record to persist
     * @param messageLabel the UI label used to display save status
     */
    private static void saveToFile(final GradeResults record, final Label messageLabel) {
        final String filename = record.getClassName() + ".txt";
        try {
            Files.writeString(
                Paths.get(filename),
                record.toFile() + "\n",
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
            UserInterface.errorTextConfiguration(messageLabel);
            messageLabel.setText("Saved to " + filename);
        } catch (IOException ex) {
            UserInterface.errorTextConfiguration(messageLabel);
            messageLabel.setText("Error saving file.");
        }
    }

    /**
     * Displays an error dialog with the provided title and message.
     *
     * @param title   the dialog title
     * @param message the error message to display
     */
    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays an informational dialog with the provided title and message.
     *
     * @param title   the dialog title
     * @param message the informational message to display
     */
    private void showInfoDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
