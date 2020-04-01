package life.calgo.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import javafx.collections.ObservableList;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.food.ConsumedFood;

/**
 * Unmodifiable view of a consumption record.
 */
public interface ReadOnlyConsumptionRecord {

    /**
     * Returns an unmodifiable view of consumedFood in consumption record.
     * This list will not contain any duplicate foods.
     */
    ObservableList<ConsumedFood> getDailyList();

    /**
     * Returns a collection of LocalDate to DailyFoodLog key-value pairs in consumption record.
     * This HashMap will not contain any duplicate dates.
     */
    HashMap<LocalDate, DailyFoodLog> getDateToLogMap();

    List<DailyFoodLog> getDailyFoodLogs();
}
