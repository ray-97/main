package f11_1.calgo.ui;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

import f11_1.calgo.commons.core.LogsCenter;
import f11_1.calgo.model.day.Day;
import f11_1.calgo.model.food.ConsumedFood;
import f11_1.calgo.model.food.ConsumedFood;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

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


    class DailyListViewCell extends ListCell<ConsumedFood> {
        @Override
        protected void updateItem(ConsumedFood food, boolean empty) {
            super.updateItem(food, empty);

            if (empty || food == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ConsumedFoodCard(food, getIndex() + 1).getRoot());
            }
        }
    }

}
