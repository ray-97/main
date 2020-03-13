package f11_1.calgo.model;

import f11_1.calgo.model.food.Food;
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

}
