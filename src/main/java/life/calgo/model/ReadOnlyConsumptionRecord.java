package life.calgo.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import javafx.collections.ObservableList;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.food.DisplayFood;

/**
 * Unmodifiable view of a consumption record.
 */
public interface ReadOnlyConsumptionRecord {

    /**
     * Returns an unmodifiable view of displayFood in consumption record.
     * This list will not contain any duplicate foods.
     */
    ObservableList<DisplayFood> getDailyList();

    /**
     * Returns a collection of LocalDate to DailyFoodLog key-value pairs in consumption record.
     * This HashMap will not contain any duplicate dates.
     */
    HashMap<LocalDate, DailyFoodLog> getDateToLogMap();

    List<DailyFoodLog> getDailyFoodLogs();
}
