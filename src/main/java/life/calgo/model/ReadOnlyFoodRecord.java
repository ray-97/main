package life.calgo.model;

import javafx.collections.ObservableList;
import life.calgo.model.food.Food;

/**
 * Unmodifiable view of a food record.
 */
public interface ReadOnlyFoodRecord {

    /**
     * Returns an unmodifiable view of the food record.
     * This list will not contain any duplicate foods.
     */
    ObservableList<Food> getFoodList();

}
