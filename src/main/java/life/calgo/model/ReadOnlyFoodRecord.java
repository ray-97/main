package life.calgo.model;

import javafx.collections.ObservableList;
import life.calgo.model.food.Food;

/**
 * Unmodifiable view of a FoodRecord in lexicographic order without duplicates.
 */
public interface ReadOnlyFoodRecord {

    /**
     * Returns an unmodifiable view of the FoodRecord, in lexicographic order.
     * This list will not contain any duplicate foods.
     */
    ObservableList<Food> getFoodList();

}
