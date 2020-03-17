package f11_1.calgo.model;

import java.time.LocalDate;

import f11_1.calgo.model.day.Day;
import f11_1.calgo.model.food.ConsumedFood;
import f11_1.calgo.model.food.Food;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

/**
 * Unmodifiable view of a food record.
 */
public interface ReadOnlyFoodRecord {

    /**
     * Returns an unmodifiable view of the food record.
     * This list will not contain any duplicate foods.
     */
    ObservableList<Food> getFoodList();

    ObservableList<ConsumedFood> getDailyList(LocalDate date);
}
