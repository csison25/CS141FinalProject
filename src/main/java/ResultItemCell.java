import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

/**
 * Custom ListCell that renders a ResultItem as an HBox with CheckBox + result label.
 * CheckBox state is independent of item selection (can highlight without checking).
 */
public class ResultItemCell extends ListCell<ResultItem> {

    /**
     * Updates the cell content to display a {@link ResultItem} with a checkbox.
     *
     * @param item  the item for this cell
     * @param empty whether the cell is empty
     */
    @Override
    protected void updateItem(ResultItem item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
            setText(null);
        } else {
            // Create checkbox for selection
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(item.isSelected());

            // Bind checkbox state to ResultItem
            checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
                item.setSelected(newVal);
            });

            // Create label with full result summary
            Label resultLabel = new Label(item.toString());
            resultLabel.setWrapText(false);
            resultLabel.setStyle("-fx-font-size: 11;");

            // Create HBox container: [CheckBox] [Label]
            HBox container = new HBox(10);
            container.setAlignment(Pos.CENTER_LEFT);
            container.getChildren().addAll(checkBox, resultLabel);

            setGraphic(container);
            setText(null);
        }
    }
}
