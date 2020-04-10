package life.calgo.ui;

import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import javafx.scene.layout.StackPane;
import life.calgo.commons.core.LogsCenter;
import life.calgo.model.food.DisplayFood;

/**
 * Responsible for displaying food consumed in a given day.
 */
public class DailyListPanel extends UiPart<Region> {

    private static final String FXML = "DailyListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DailyListPanel.class);

    @FXML
    private TableView<DisplayFood> dailyListView;

    public DailyListPanel(ObservableList<DisplayFood> dailyList) {
        super(FXML);
        setUpColumns();
        dailyListView.setItems(dailyList);
    }


    private void setUpColumns() {
        TableColumn<DisplayFood, String> foodName = setUpFoodNameColumn();
        TableColumn<DisplayFood, String> portion = setUpPortionColumn();
        dailyListView.getColumns().addAll(foodName, portion);
    }

    private TableColumn<DisplayFood, String> setUpFoodNameColumn() {
        TableColumn<DisplayFood, String> foodName = new TableColumn<>("Food Name");
        foodName.setCellValueFactory(param -> new ReadOnlyObjectWrapper<String>(param.getValue().getName().fullName));
        foodName.setCellFactory(tableColumn -> new NameTableCell());
        foodName.prefWidthProperty().bind(dailyListView.widthProperty().multiply(0.7));
        foodName.setResizable(false);
        return foodName;
    }

    private TableColumn<DisplayFood, String> setUpPortionColumn() {
        TableColumn<DisplayFood, String> portion = new TableColumn<>("Portion");
        portion.setCellValueFactory(param -> new ReadOnlyObjectWrapper<String>(Double.toString(param.getValue().getPortion())));
        portion.setCellFactory(tableColumn -> new PortionTableCell());
        portion.prefWidthProperty().bind(dailyListView.widthProperty().multiply(0.2));
        portion.setResizable(false);
        return portion;
    }

    class NameTableCell extends TableCell<DisplayFood, String> {
        @Override
        protected void updateItem(String foodName, boolean empty) {
            super.updateItem(foodName, empty);
            if (empty || foodName == null) {
                setGraphic(null);
                setText(null);
            } else {
                Label label = new Label();
                label.setText(foodName);
                StackPane pane = new StackPane();
                pane.getChildren().add(label);
                pane.setAlignment(Pos.CENTER_LEFT);
                setGraphic(pane);
            }

        }

    }
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
                StackPane pane = new StackPane();
                pane.getChildren().add(label);
                pane.setAlignment(Pos.CENTER_LEFT);
                setGraphic(pane);
            }

        }

    }




    /**
//     * Responsible for containing the display of each food item consumed in the given day.
//     */
//    class DailyListViewCell extends ListCell<DisplayFood> {
//        @Override
//        protected void updateItem(DisplayFood food, boolean empty) {
//            super.updateItem(food, empty);
//
//            if (empty || food == null) {
//                setGraphic(null);
//                setText(null);
//            } else {
//                setGraphic(new DisplayFoodCard(food, getIndex() + 1).getRoot());
//            }
//        }
//    }
}

