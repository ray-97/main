package life.calgo.model.day;

import static life.calgo.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;

import life.calgo.model.food.Food;

/**
 * A data structure that stores a map of Food to portion and Food to rating for a certain date where food is consumed.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class DailyFoodLog {
    // Constants
    public static final int RATING_DUMMY_VALUE = -1;

    // Data fields
    private final LinkedHashMap<Food, Double> foods;
    private final LinkedHashMap<Food, ArrayList<Integer>> ratings;
    private final LocalDate localDate;

    public DailyFoodLog() {
        foods = new LinkedHashMap<>();
        ratings = new LinkedHashMap<>();
        localDate = LocalDate.now();
    }

    /**
     * Functions as constructor when you have attributes you wish to set.
     *
     * @param foods LinkedHashMap representing the mapping of Food to its portion.
     * @param ratings LinkedHashMap representing the mapping of Food to its ratings.
     * @param localDate LocalDate associated to the DailyFoodLog.
     */
    public DailyFoodLog(LinkedHashMap<Food, Double> foods,
                        LinkedHashMap<Food, ArrayList<Integer>> ratings, LocalDate localDate) {
        requireAllNonNull(foods, ratings, localDate);
        this.foods = foods;
        this.localDate = localDate;
        this.ratings = ratings;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    /**
     * Returns a new DailyFoodLog with same data fields, but different date.
     * @param date Date you wish to set the log to.
     * @return Replica of this DailyFoodLog, with different date.
     */
    public DailyFoodLog setDate(LocalDate date) {
        return new DailyFoodLog(copyFoods(), copyRatings(), date);
    }

    /**
     * Adds food into foods LinkedHashMap.
     *
     * @param food Food that has been consumed.
     * @param quantity Number of portions of food that has been consumed.
     * @return Updated DailyFoodLog object.
     */
    public DailyFoodLog consume(Food food, double quantity) {
        return new DailyFoodLog(this.add(food, quantity), copyRatings(), localDate);
    }

    /**
     * Acts as a helper method to consume method.
     *
     * @param foodToAdd Food object to be added to LinkedHashMap as key.
     * @param quantity Double representing portion, to be stored as value in LinkedHashMap.
     * @return LinkedHashMap containing the Food and portion as key-value pairs.
     */
    private LinkedHashMap<Food, Double> add(Food foodToAdd, double quantity) {
        LinkedHashMap<Food, Double> foods = copyFoods();
        if (foods.containsKey(foodToAdd)) {
            foods.put(foodToAdd, quantity + foods.get(foodToAdd));
        } else {
            foods.put(foodToAdd, quantity);
            ratings.put(foodToAdd, new ArrayList<>());
        }
        return foods;
    }

    /**
     * Decreases portion of food consumed in the key value pair, or totally remove the entry.
     *
     * @param food Food that is to be removed.
     * @param quantity Number of portions of food that should be removed.
     * @return Updated DailyFoodLog object.
     */
    public DailyFoodLog vomit(Food food, OptionalDouble quantity) {
        return new DailyFoodLog(this.remove(food, quantity), copyRatings(), localDate);
    }

    /**
     * Acts as a helper method to vomit method.
     *
     * @param foodToRemove Food object to be possibly removed LinkedHashMap's key.
     * @param quantity Double representing portion to reduce by.
     * @return LinkedHashMap containing the Food and portion as key-value pairs.
     */
    private LinkedHashMap<Food, Double> remove(Food foodToRemove, OptionalDouble quantity) {
        LinkedHashMap<Food, Double> foods = copyFoods();
        boolean shouldRemoveCompletely = quantity.isEmpty() || quantity.getAsDouble() >= foods.get(foodToRemove);
        if (!foods.containsKey(foodToRemove)) {
            throw new IllegalArgumentException();
        } else if (shouldRemoveCompletely) {
            foods.remove(foodToRemove);
            ratings.put(foodToRemove, new ArrayList<>());
        } else {
            foods.put(foodToRemove, foods.get(foodToRemove) - quantity.getAsDouble());
        }
        return foods;
    }

    /**
     * Replaces food in current DailyFoodLog with updated food.
     *
     * @param newFood Food with updated attribute(s).
     * @return Updated DailyFoodLog object.
     */
    public DailyFoodLog updateFoodWithSameName(Food newFood) {
        LinkedHashMap<Food, Double> foods = copyFoods();
        LinkedHashMap<Food, ArrayList<Integer>> ratings = copyRatings();
        OptionalDouble portion = OptionalDouble.empty();
        ArrayList<Integer> rating = new ArrayList<>();
        for (Food food: this.foods.keySet()) {
            if (food.isSameFood(newFood)) {
                portion = OptionalDouble.of(foods.remove(food));
                rating = new ArrayList<>(this.ratings.get(food));
                ratings.remove(food);
            }
        }
        if (portion.isPresent()) {
            foods.put(newFood, portion.getAsDouble());
            ratings.put(newFood, rating);
        }
        return new DailyFoodLog(foods, ratings, localDate);
    }

    /**
     * Acts as an accessor method to get the set of food objects in the data structure.
     *
     * @return Set of food objects.
     */
    public Set<Food> getFoods() {
        LinkedHashMap<Food, Double> foods = new LinkedHashMap<>();
        for (Food food: this.foods.keySet()) {
            foods.put(food, this.foods.get(food));
        }
        return foods.keySet();
    }

    /**
     * Retrieves a Food object by its position in the LinkedHashMap.
     *
     * @param index Zero based index of the food object.
     * @return Food object within an optional wrapper.
     * @throws IndexOutOfBoundsException If given index is invalid.
     */
    public Optional<Food> getFoodByIndex(int index) throws IndexOutOfBoundsException {
        ArrayList<Food> temp = new ArrayList<>(foods.keySet());
        Food food = (Food) temp.get(index);
        return Optional.of(food);
    }

    /**
     * Acts as an accessor method to get the portion consumed of a given food object.
     *
     * @param food Food consumed.
     * @return Portion of food consumed in DailyFoodLog.
     */
    public double getPortion(Food food) {
        if (!foods.containsKey(food)) {
            return 0.0;
        }
        return foods.get(food);
    }

    /**
     * Calculates the total number of calories consumed based on all food objects stored in DailyFoodLog object.
     *
     * @return total number of calories consumed in this DailyFoodLog object.
     */
    public double getTotalCalories() {
        double totalCalories = 0.0;
        for (Food food : foods.keySet()) {
            totalCalories += Integer.parseInt(food.getCalorie().value) * foods.get(food);
        }
        return totalCalories;
    }

    /**
     * Adds an Integer into the list of ratings related to a Food object.
     *
     * @param food Food that receives rating.
     * @param rating Rating given to the food.
     * @return DailyFoodLog object with rating added.
     */
    public DailyFoodLog addRating(Food food, int rating) {
        LinkedHashMap<Food, ArrayList<Integer>> ratings = copyRatings();
        ratings.get(food).add(rating);
        return new DailyFoodLog(copyFoods(), ratings, localDate);
    }

    /**
     * Acts as accessor method to get rating that is to be displayed for a given food object.
     *
     * @param food Food that rating is for.
     * @return Double representing the rating to display.
     */
    public double getRating(Food food) {
        return getMeanRating(food);
    }

    /**
     * Acts as a helper method to consume method.
     *
     * @param foodToAdd Food object to be added to LinkedHashMap as key.
     * @param quantity Double representing portion, to be stored as value in LinkedHashMap.
     * @return LinkedHashMap containing the Food and portion as key-value pairs.
     */

    /**
     * Acts as a helper method to calculate mean rating for getRating method.
     *
     * @param food Food that you want rating of.
     * @return Double which is the average rating of food, calculated based on it's ratings array.
     */
    private double getMeanRating(Food food) {
        OptionalDouble average = ratings.get(food).stream().mapToInt(i -> i).average();
        if (average.isEmpty()) {
            return RATING_DUMMY_VALUE;
        } else {
            return average.getAsDouble();
        }
    }

    /**
     * Returns a copy of this DailyFoodLog's ratings.
     */
    public LinkedHashMap<Food, ArrayList<Integer>> copyRatings() {
        LinkedHashMap<Food, ArrayList<Integer>> ratings = new LinkedHashMap<>();
        for (Food food: this.ratings.keySet()) {
            ratings.put(food.copy(), new ArrayList<>(this.ratings.get(food)));
        }
        return ratings;
    }

    /**
     * Returns a copy of this DailyFoodLog's foods.
     */
    public LinkedHashMap<Food, Double> copyFoods() {
        LinkedHashMap<Food, Double> foods = new LinkedHashMap<>();
        for (Food food: this.foods.keySet()) {
            foods.put(food.copy(), this.foods.get(food));
        }
        return foods;
    }

    public DailyFoodLog copy() {
        return new DailyFoodLog(copyFoods(), copyRatings(), localDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DailyFoodLog)) {
            return false;
        }

        DailyFoodLog otherFoodLog = (DailyFoodLog) other;

        return otherFoodLog.getLocalDate().equals(getLocalDate())
                && otherFoodLog.copyFoods().equals(copyFoods())
                && otherFoodLog.copyRatings().equals(copyRatings());
    }

}
