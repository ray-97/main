package life.calgo.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import life.calgo.commons.core.LogsCenter;
import life.calgo.model.food.ConsumedFood;

/**
 * Responsible for displaying food consumed in a given day.
 */
public class DailyListPanel extends UiPart<Region> {

    private static final String FXML = "DailyListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DailyListPanel.class);

    @FXML
    private ListView<ConsumedFood> dailyListView;

    public DailyListPanel(ObservableList<ConsumedFood> dailyList) {
        super(FXML);
        dailyListView.setItems(dailyList);
        dailyListView.setCellFactory(listView -> new DailyListPanel.DailyListViewCell());
    }

    /**
     * Responsible for containing the display of each food item consumed in the given day.
     */
    class DailyListViewCell extends ListCell<ConsumedFood> {
        @Override
        protected void updateItem(ConsumedFood food, boolean empty) {
            super.updateItem(food, empty);

            if (empty || food == null) {
                setGraphic(null);
                setText(null);
            } else {
                if (getIndex() % 2 == 1) {
                    this.setStyle("-fx-background-color:CORAL");
                } else {
                    this.setStyle("-fx-background-color:TOMATO");
                }
                setGraphic(new ConsumedFoodCard(food, getIndex() + 1).getRoot());
            }
        }
    }
}

