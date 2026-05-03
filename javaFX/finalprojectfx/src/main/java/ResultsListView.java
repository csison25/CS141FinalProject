import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Custom widget for displaying and managing calculation results.
 * Encapsulates ListView + action buttons in a reusable VBox component.
 * Delegates event handling to parent class (FinalProject).
 */
public class ResultsListView extends VBox {
    private final ListView<ResultItem> resultsList;
    private final Button deleteSelectedBtn;
    private final Button deleteAllBtn;
    private final Button importResultsBtn;

    public ResultsListView(ObservableList<ResultItem> results) {
        this.setSpacing(10);
        this.setPadding(new Insets(0));
        this.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 1;");

        // Header label
        Label header = new Label("Calculation Results");
        header.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        // ListView with custom cell renderer
        resultsList = new ListView<>(results);
        resultsList.setCellFactory(param -> new ResultItemCell());
        resultsList.setPrefHeight(300);
        resultsList.setStyle("-fx-font-size: 11;");
        VBox.setVgrow(resultsList, Priority.ALWAYS);

        // Action buttons row
        deleteSelectedBtn = UserInterface.createButton("Delete Selected");
        deleteAllBtn = UserInterface.createButton("Delete All");
        importResultsBtn = UserInterface.createButton("Import Results");

        HBox actionButtons = new HBox(10);
        actionButtons.setAlignment(Pos.CENTER);
        actionButtons.setPadding(new Insets(10, 0, 0, 0));
        actionButtons.getChildren().addAll(deleteSelectedBtn, deleteAllBtn, importResultsBtn);

        // Add all components to VBox
        this.getChildren().addAll(header, resultsList, actionButtons);
    }

    public ListView<ResultItem> getResultsList() {
        return resultsList;
    }

    public Button getDeleteSelectedBtn() {
        return deleteSelectedBtn;
    }

    public Button getDeleteAllBtn() {
        return deleteAllBtn;
    }

    public Button getImportResultsBtn() {
        return importResultsBtn;
    }
}
