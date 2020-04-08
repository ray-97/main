package life.calgo.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import life.calgo.commons.core.LogsCenter;
import life.calgo.model.food.DisplayFood;

/**
 * Responsible for displaying food consumed in a given day.
 */
public class DailyListPanel extends UiPart<Region> {

    private static final String FXML = "DailyListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DailyListPanel.class);

    @FXML
    private ListView<DisplayFood> dailyListView;

    public DailyListPanel(ObservableList<DisplayFood> dailyList) {
        super(FXML);
        dailyListView.setItems(dailyList);
        dailyListView.setCellFactory(listView -> new DailyListPanel.DailyListViewCell());
    }

    /**
     * Responsible for containing the display of each food item consumed in the given day.
     */
    class DailyListViewCell extends ListCell<DisplayFood> {
        @Override
        protected void updateItem(DisplayFood food, boolean empty) {
            super.updateItem(food, empty);

            if (empty || food == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DisplayFoodCard(food, getIndex() + 1).getRoot());
            }
        }
    }
}

