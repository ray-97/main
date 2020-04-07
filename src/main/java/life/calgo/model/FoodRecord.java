package life.calgo.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import life.calgo.model.food.Food;
import life.calgo.model.food.Name;
import life.calgo.model.food.UniqueFoodList;

/**
 * Contains all Food entries, ensuring they are in lexicographic order and without duplicates.
 * Duplicates are not allowed by .isSameFood comparison.
 */
public class FoodRecord implements ReadOnlyFoodRecord {

    private final UniqueFoodList foodList;
    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        foodList = new UniqueFoodList();
    }

    public FoodRecord() {}

    /**
     * Creates a FoodRecord using the Food objects in the {@code toBeCopied}.
     */
    public FoodRecord(ReadOnlyFoodRecord toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    // list overwrite operations

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

    // food-level operations

    /**
     * Returns true if a food with the same identity as {@code food} exists in the FoodRecord.
     *
     * @param food the Food to check for existence in the current FoodRecord.
     * @return whether the current FoodRecord contains this Food.
     */
    public boolean hasFood(Food food) {
        requireNonNull(food);
        return foodList.contains(food);
    }

    /**
     * Adds a Food to the FoodRecord.
     * The Food must not already exist in the FoodRecord.
     *
     * @param food the Food to add.
     */
    public void addFood(Food food) {
        foodList.add(food);
    }

    /**
     * Replaces the given Food {@code target} in the FoodRecord's foodList with {@code editedFood}.
     * {@code target} must exist in the FoodRecord.
     * The food identity of {@code editedFood} must not be the same as another existing food in the food record.
     *
     * @param target the Food to be replaced.
     * @param editedFood the new updated Food.
     */
    public void setFood(Food target, Food editedFood) {
        requireNonNull(editedFood);

        foodList.setFood(target, editedFood);
    }

    /**
     * Removes {@code key} from this {@code FoodRecord}.
     * {@code key} must exist in the FoodRecord.
     *
     * @param key the Food to remove from the FoodRecord.
     */
    public void removeFood(Food key) {
        foodList.remove(key);
    }

    /**
     * Returns the existing Food object in FoodRecord.
     *
     * @param toGet the Food to obtain from within the FoodRecord.
     * @return the Food of interest.
     */
    public Food getExistingFood(Food toGet) {
        requireNonNull(toGet);
        return foodList.getExistingFood(toGet);
    }

    // utility methods

    public Optional<Food> getFoodByName(Name name) {
        return foodList.getFoodByName(name);
    }

    /**
     * Returns a line-by-line representation of the FoodRecord, displaying all its details.
     *
     * @return the line-by-line representation of the FoodRecord.
     */
    @Override
    public String toString() {
        String result = "";
        for (Food f: foodList.asUnmodifiableObservableList()) {
            result += f + "\n";
        }
        return result;
    }

    /**
     * Returns the ObservableList representation of the current FoodRecord.
     * This should not be used for modification purposes.
     *
     * @return the ObservableList representation of the current FoodRecord.
     */
    @Override
    public ObservableList<Food> getFoodList() {
        return foodList.asUnmodifiableObservableList();
    }

    /**
     * Checks for equivalence between 2 FoodRecords.
     *
     * @param other the other FoodRecord.
     * @return whether the 2 FoodRecords can be considered equivalent.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FoodRecord
                && foodList.equals(((FoodRecord) other).foodList));
    }

    /**
     * Returns the hashcode of the FoodRecord.
     *
     * @return the supposedly unique hashcode of the FoodRecord.
     */
    @Override
    public int hashCode() {
        return foodList.hashCode();
    }
}
