package life.calgo.model;

import java.time.LocalDate;

import life.calgo.model.food.ConsumedFood;
import life.calgo.model.food.Food;
import javafx.collections.ObservableList;

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
