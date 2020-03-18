package life.calgo.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.day.UniqueDateToLogMap;
import life.calgo.model.food.ConsumedFood;
import life.calgo.model.food.Food;
import life.calgo.model.food.Name;
import life.calgo.model.food.UniqueFoodList;

/**
 * Wraps all data at the food record level
 * Duplicates are not allowed (by .isSameFood comparison)
 */
public class FoodRecord implements ReadOnlyFoodRecord {

    private final UniqueFoodList foodList;
    private final UniqueDateToLogMap dateToLogMap;
    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        foodList = new UniqueFoodList();
        dateToLogMap = new UniqueDateToLogMap();
    }

    public FoodRecord() {}

    /**
     * Creates a FoodRecord using the Food objects in the {@code toBeCopied}
     */
    public FoodRecord(ReadOnlyFoodRecord toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the food list with {@code foods}.
     * {@code foods} must not contain duplicate foods.
     */
    public void setFoodList(List<Food> foods) {
        this.foodList.setFoods(foods);
    }

    /**
     * Resets the existing data of this {@code FoodRecord} with {@code newData}.
     */
    public void resetData(ReadOnlyFoodRecord newData) {
        requireNonNull(newData);

        setFoodList(newData.getFoodList());
    }

    //// food-level operations

    /**
     * Returns true if a food with the same identity as {@code food} exists in the food record.
     */
    public boolean hasFood(Food food) {
        requireNonNull(food);
        return foodList.contains(food);
    }

    /**
     * Adds a food to the food record.
     * The food must not already exist in the food record.
     */
    public void addFood(Food p) {
        foodList.add(p);
    }

    /**
     * Replaces the given food {@code target} in the FoodRecord's foodList with {@code editedFood}.
     * {@code target} must exist in the food record.
     * The food identity of {@code editedFood} must not be the same as another existing food in the food record.
     */
    public void setFood(Food target, Food editedFood) {
        requireNonNull(editedFood);

        foodList.setFood(target, editedFood);
    }

    /**
     * Removes {@code key} from this {@code FoodRecord}.
     * {@code key} must exist in the food record.
     */
    public void removeFood(Food key) {
        foodList.remove(key);
    }

    /**
     * Returns the existing Food object in Food Record
     */
    public Food getExistingFood(Food toGet) {
        requireNonNull(toGet);
        return foodList.getExistingFood(toGet);
    }

    //// util methods

    public Optional<Food> getFoodByName(Name name) {
        return foodList.getFoodByName(name);
    }

    public void addLog(DailyFoodLog foodLog) {
        dateToLogMap.addLog(foodLog);
    }

    public boolean hasLogWithSameDate(DailyFoodLog foodLog) {
        return dateToLogMap.hasLogWithSameDate(foodLog);
    }

    public DailyFoodLog getLogByDate(LocalDate date) {
        return dateToLogMap.getLogByDate(date);
    }

    public void updateLog(DailyFoodLog logAfterConsumption) {
        dateToLogMap.updateLog(logAfterConsumption);
    }

    @Override
    public String toString() {
        return foodList.asUnmodifiableObservableList().size() + " foods";
        // TODO: refine later
    }

    @Override
    public ObservableList<Food> getFoodList() {
        return foodList.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<ConsumedFood> getDailyList() {
        return dateToLogMap.asUnmodifiableDailyList();
    }

    @Override
    public void setDailyList(LocalDate date) {
        dateToLogMap.setDailyList(date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FoodRecord // instanceof handles nulls
                && foodList.equals(((FoodRecord) other).foodList));
    }

    @Override
    public int hashCode() {
        return foodList.hashCode();
    }
}
