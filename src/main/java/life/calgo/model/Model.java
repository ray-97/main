package life.calgo.model;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import life.calgo.commons.core.GuiSettings;
import life.calgo.logic.commands.exceptions.CommandException;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.day.DailyGoal;
import life.calgo.model.food.DisplayFood;
import life.calgo.model.food.Food;
import life.calgo.model.food.Name;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true. */
    Predicate<Food> PREDICATE_SHOW_ALL_FOODS = unused -> true;
    Predicate<DisplayFood> PREDICATE_SHOW_ALL_CONSUMED_FOODS = unused -> true;


    // User prefs related methods

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
    void setFoodRecordFilePath(Path foodRecordFilePath);

    // FoodRecord related methods

    // Getter methods

    /** Returns the FoodRecord */
    ReadOnlyFoodRecord getFoodRecord();

    /** Returns the existing Food item in FoodRecord. */
    Food getExistingFood(Food toAdd);

    Optional<Food> getFoodByName(Name parseName);

    /** Returns an unmodifiable view of the filtered food record. */
    ObservableList<Food> getFilteredFoodRecord();

    // Setter methods
    /**
     * Replaces food record data with the data in {@code foodRecord}.
     */
    void setFoodRecord(ReadOnlyFoodRecord foodRecord);

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

    /**
     * Updates the filter of the filtered food record to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFoodRecord(Predicate<Food> predicate);

    // Utility method
    /**
     * Returns true if a food with the same identity as {@code food} exists in the FoodRecord.
     */
    boolean hasFood(Food food);

    // Consumption Record related methods

    // Getter methods

    DailyFoodLog getLogByDate(LocalDate localDate);

    ReadOnlyConsumptionRecord getConsumptionRecord();

    ObservableList<DisplayFood> getCurrentFilteredDailyList();

    ArrayList<DailyFoodLog> getPastWeekLogs();

    // Setter methods

    void addLog(DailyFoodLog foodLog);

    void updateLog(DailyFoodLog logAfterConsumption);

    void updateCurrentFilteredDailyList(Predicate<DisplayFood> predicate, LocalDate date) throws CommandException;

    void updateConsumedLists(Food food);

    // Utility methods

    boolean hasLogWithSameDate(DailyFoodLog foodLog);

    boolean hasLogWithSameDate(LocalDate date);


    // Goal related methods

    // Getter methods

    DailyGoal getDailyGoal();

    double getRemainingCalories();

    LocalDate getDate();

    // Setter methods

    void updateDailyGoal(int targetDailyCalories);

    void updateDate(LocalDate date);

    // Utility method

    boolean isGoalMade();

}
