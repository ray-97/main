package f11_1.calgo.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import f11_1.calgo.commons.core.GuiSettings;
import f11_1.calgo.model.food.Food;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Food> PREDICATE_SHOW_ALL_FOODS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' food record file path.
     */
    Path getFoodRecordFilePath();

    /**
     * Sets the user prefs' food record file path.
     */
    void setFoodRecordFilePath(Path addressBookFilePath);

    /**
     * Replaces food record data with the data in {@code foodRecord}.
     */
    void setFoodRecord(ReadOnlyFoodRecord foodRecord);

    /** Returns the FoodRecord */
    ReadOnlyFoodRecord getFoodRecord();

    /**
     * Returns true if a food with the same identity as {@code food} exists in the address book.
     */
    boolean hasFood(Food food);

    /**
     * Deletes the given food.
     * The person must exist in the food record.
     */
    void deleteFood(Food target);

    /**
     * Adds the given food.
     * {@code food} must not already exist in the food record.
     */
    void addFood(Food food);

    /**
     * Replaces the given food {@code target} with {@code editedFood}.
     * {@code target} must exist in the food record.
     * The food identity of {@code editedFood} must not be the same as another existing food in the food record.
     */
    void setFood(Food target, Food editedFood);

    /** Returns an unmodifiable view of the filtered food record. */
    ObservableList<Food> getFilteredFoodRecord();

    /**
     * Updates the filter of the filtered food record to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFoodRecord(Predicate<Food> predicate);
}
