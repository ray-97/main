package life.calgo.ui;

import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import life.calgo.commons.core.LogsCenter;
import life.calgo.model.food.DisplayFood;

/**
 * Responsible for displaying food consumed in a given day.
 */
public class DailyListPanel extends UiPart<Region> {

    private static final String FXML = "DailyListPanel.fxml";
    private static final String NO_FOOD = "You haven't consumed anything today!";
    private final Logger logger = LogsCenter.getLogger(DailyListPanel.class);

    @FXML
    private TableView<DisplayFood> dailyListView;

    public DailyListPanel(ObservableList<DisplayFood> dailyList) {
        super(FXML);
        setUpColumns();
        Text text = new Text(NO_FOOD);
        text.setFill(Color.WHITE);
        text.setFont(Font.font ("Segoe UI Semibold", 13));
        dailyListView.setPlaceholder(text);
        dailyListView.setItems(dailyList);
    }

    @SuppressWarnings("unchecked") // suppressed an inevitable unchecked warning due to use of varargs
    private void setUpColumns() {
        TableColumn<DisplayFood, Void> index = setUpIndexColumn();
        TableColumn<DisplayFood, String> foodName = setUpFoodNameColumn();
        TableColumn<DisplayFood, String> portion = setUpPortionColumn();
        TableColumn<DisplayFood, String> rating = setUpRatingColumn();
        dailyListView.getColumns().addAll(index, foodName, portion, rating);
    }

    private TableColumn<DisplayFood, Void> setUpIndexColumn() {
        TableColumn<DisplayFood, Void> index = new TableColumn<>("");
        index.setCellFactory(tableColumn -> new IndexTableCell());
        index.prefWidthProperty().bind(dailyListView.widthProperty().multiply(0.05));
        index.setResizable(false);
        return index;
    }

    private TableColumn<DisplayFood, String> setUpRatingColumn() {
        TableColumn<DisplayFood, String> rating = new TableColumn<>("Avg Rating");
        rating.setCellValueFactory(param -> new ReadOnlyObjectWrapper<String>(param.getValue().getRating()));
        rating.setCellFactory(tableColumn -> new RatingTableCell());
        rating.prefWidthProperty().bind(dailyListView.widthProperty().multiply(0.15));
        rating.setResizable(false);
        rating.setMinWidth(60.0);
        return rating;
    }

    private TableColumn<DisplayFood, String> setUpFoodNameColumn() {
        TableColumn<DisplayFood, String> foodName = new TableColumn<>("Food Name");
        foodName.setCellValueFactory(param -> new ReadOnlyObjectWrapper<String>(param.getValue().getName().fullName));
        foodName.setCellFactory(tableColumn -> new NameTableCell());
        foodName.prefWidthProperty().bind(dailyListView.widthProperty().multiply(0.60));
        foodName.setResizable(false);
        return foodName;
    }

    private TableColumn<DisplayFood, String> setUpPortionColumn() {
        TableColumn<DisplayFood, String> portion = new TableColumn<>("Portion");
        portion.setCellValueFactory(param -> new ReadOnlyObjectWrapper<String>(
                Double.toString(param.getValue().getPortion())));
        portion.setCellFactory(tableColumn -> new PortionTableCell());
        portion.prefWidthProperty().bind(dailyListView.widthProperty().multiply(0.15));
        portion.setResizable(false);
        return portion;
    }

    /**
     * Responsible for displaying index of Food item in the list.
     */
    class IndexTableCell extends TableCell<DisplayFood, Void> {
        @Override
        public void updateIndex(int index) {
            super.updateIndex(index);
            if (isEmpty() || index < 0) {
                setText(null);
            } else {
                setText(Integer.toString(index + 1) + ".");
            }
        }
    }



    /**
     * Custom {@code RatingTableCell} that displays the graphics of a {@code TableCell} using a {@code DisplayFood}.
     */
    class RatingTableCell extends TableCell<DisplayFood, String> {
        @Override
        protected void updateItem(String rating, boolean empty) {
            super.updateItem(rating, empty);
            if (empty || rating == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RatingLabel(rating).getPane());
                setStyle("-fx-padding: 0 0 0 0");
            }
        }
    }

    /**
     * Custom {@code NameTableCell} that displays the graphics of a {@code TableCell} using a {@code DisplayFood}.
     */
    class NameTableCell extends TableCell<DisplayFood, String> {
        @Override
        protected void updateItem(String food, boolean empty) {
            super.updateItem(food, empty);
            if (empty || food == null) {
                setGraphic(null);
                setText(null);
            } else {
                Label label = new Label();
                label.setText(food);
                label.setStyle("-fx-text-fill: white");
                setGraphic(label);
            }

        }

    }

    /**
     * Custom {@code PortionTableCell} that displays the graphics of a {@code TableCell} using a {@code portion}.
     */
    class PortionTableCell extends TableCell<DisplayFood, String> {
        @Override
        protected void updateItem(String portion, boolean empty) {
            super.updateItem(portion, empty);
            if (empty || portion == null) {
                setGraphic(null);
                setText(null);
            } else {
                Label label = new Label();
                label.setText(portion);
                label.getStyleClass().add("portionLabel");
                StackPane pane = new StackPane();
                pane.getChildren().add(label);
                pane.setAlignment(Pos.CENTER_LEFT);
                setGraphic(pane);
            }

        }
    }
}
