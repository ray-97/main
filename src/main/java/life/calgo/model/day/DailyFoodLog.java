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
 * A data structure that stores food objects as keys and their corresponding portions consumed as the value
 */
public class DailyFoodLog {

    private final LinkedHashMap<Food, Double> foods;
    private final LinkedHashMap<Food, ArrayList<Integer>> ratings; // arraylist got nothing yet
    private final LocalDate localDate;

    public DailyFoodLog() {
        foods = new LinkedHashMap<>();
        ratings = new LinkedHashMap<>();
        localDate = LocalDate.now();
    }

    public DailyFoodLog(LinkedHashMap<Food, Double> foods,
                        LinkedHashMap<Food, ArrayList<Integer>> ratings, LocalDate localDate) {
        requireAllNonNull(foods, localDate);
        this.foods = foods;
        this.localDate = localDate;
        this.ratings = ratings;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Food food : foods.keySet()) {
            sb.append(food);
        }
        sb.append(localDate);
        return sb.toString();
    }

    /**
     * Increments portion of food consumed in the key value pair
     * @param foodToAdd food that has been consumed
     * @param quantity number of portions of foodToAdd that has been consumed
     * @return an updated DailyFoodLog object
     */
    public LinkedHashMap<Food, Double> add(Food foodToAdd, double quantity) {
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
     * Decreases portion of food consumed in the key value pair
     * @param foodToRemove food that is not to be consumed
     * @param quantity number of portions of foodToRemove that should be removed
     * @return an updated DailyFoodLog object
     */
    public LinkedHashMap<Food, Double> remove(Food foodToRemove, OptionalDouble quantity) {
        LinkedHashMap<Food, Double> foods = copyFoods();
        if (!foods.containsKey(foodToRemove)) {
            throw new IllegalArgumentException();
        } else if (quantity.isEmpty()) {
            foods.remove(foodToRemove);
        } else if (quantity.getAsDouble() >= foods.get(foodToRemove)) {
            foods.remove(foodToRemove);
        } else {
            foods.put(foodToRemove, foods.get(foodToRemove) - quantity.getAsDouble());
        }
        return foods;
    }

    public DailyFoodLog consume(Food food, double quantity) {
        return new DailyFoodLog(this.add(food, quantity), copyRatings(), localDate);
    }

    public DailyFoodLog vomit(Food food, OptionalDouble quantity) {
        return new DailyFoodLog(this.remove(food, quantity), copyRatings(),localDate);
    }

    public DailyFoodLog updateFoodWithSameName(Food newFood) {
        LinkedHashMap<Food, Double> foods = new LinkedHashMap<>();
        for (Food food: this.foods.keySet()) {
            if (food.isSameFood(newFood)) {
                foods.put(newFood, foods.get(food));
            } else {
                foods.put(food, foods.get(food));
            }
        }
        return new DailyFoodLog(foods, copyRatings(), localDate);
    }

    /**
     * An accessor method to get the set of food objects in the data structure.
     * @return set of food objects
     */
    public Set<Food> getFoods() {
        LinkedHashMap<Food, Double> foods = new LinkedHashMap<>();
        for (Food food: this.foods.keySet()) {
            foods.put(food, this.foods.get(food));
        }
        return foods.keySet();
    }

    public Optional<Food> getFoodByIndex(int index) throws IndexOutOfBoundsException {
        ArrayList<Food> temp = new ArrayList(foods.keySet());
        Food food = (Food) temp.get(index);
        return Optional.of(food);
    }

    public DailyFoodLog setDate(LocalDate date) {
        return new DailyFoodLog(copyFoods(), copyRatings(), date);
    }

    public DailyFoodLog addRating(Food food, int rating) {
        LinkedHashMap<Food, ArrayList<Integer>> ratings = copyRatings();
        ratings.get(food).add(rating);
        return new DailyFoodLog(copyFoods(), ratings, localDate);
    }

    /**
     * An accessor method to get the portion consumed of a given food object.
     * @param food food consumed
     * @return portion of food consumed in DailyFoodLog
     */
    public double getPortion(Food food) {
        if (!foods.containsKey(food)) {
            return 0.0;
        }
        return foods.get(food);
    }

    public double getRating(Food food) {
        return getMeanRating(food);
    }

    private double getMeanRating(Food food) {
        OptionalDouble average =  ratings.get(food).stream().mapToInt(i -> i).average();
        if (average.isEmpty()) {
            return -1;
        } else {
             return average.getAsDouble();
        }
    }

    public LocalDate getLocalDate() {
        return localDate;
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
                && otherFoodLog.getFoods().equals(getFoods());
    }

    /**
     * Checks if 2 DailyFoodLog objects have the same date
     * @param otherLog the DailyFoodLog object to compare to
     * @return true if both DailyFoodLog objects have same date
     */
    public boolean hasSameDate(DailyFoodLog otherLog) {
        if (otherLog == this) {
            return true;
        }

        return otherLog != null
                && otherLog.getLocalDate().equals(getLocalDate());
    }

    public LinkedHashMap<Food, ArrayList<Integer>> copyRatings() {
        LinkedHashMap<Food, ArrayList<Integer>> ratings = new LinkedHashMap<>();
        for (Food food: this.ratings.keySet()) {
            ratings.put(food.copy(), new ArrayList<>(this.ratings.get(food)));
        }
        return ratings;
    }

    public LinkedHashMap<Food, Double> copyFoods() {
        LinkedHashMap<Food, Double> foods = new LinkedHashMap<>();
        for (Food food: this.foods.keySet()) {
            foods.put(food.copy(), this.foods.get(food));
        }
        return foods;
    }

    /**
     * A method to obtain copy of existing data structure
     * @return a copy of existing DailyFoodLog
     */
    public DailyFoodLog copy() {
        return new DailyFoodLog(copyFoods(), copyRatings(), localDate);
    }

}