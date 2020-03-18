package life.calgo.model.day;

import static life.calgo.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.OptionalDouble;
import java.util.Set;

import life.calgo.model.food.Food;

/**
 * A data structure that stores food objects as keys and their corresponding portions consumed as the value
 */
public class DailyFoodLog {

    private final LinkedHashMap<Food, Double> foods;
    private final LocalDate localDate;

    public DailyFoodLog() {
        foods = new LinkedHashMap<>();
        localDate = LocalDate.now();
    }

    public DailyFoodLog(LinkedHashMap<Food, Double> foods, LocalDate localDate) {
        requireAllNonNull(foods, localDate);
        this.foods = foods;
        this.localDate = localDate;
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
        LinkedHashMap<Food, Double> foods = new LinkedHashMap<Food, Double>();
        for (Food food : this.foods.keySet()) {
            foods.put(food.copy(), this.foods.get(food));
        }
        if (foods.containsKey(foodToAdd)) {
            foods.put(foodToAdd, quantity + foods.get(foodToAdd));
        } else {
            foods.put(foodToAdd, quantity);
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
        LinkedHashMap<Food, Double> foods = new LinkedHashMap<>();

        for (Food food : this.foods.keySet()) {
            foods.put(food.copy(), this.foods.get(food));
        }
        // todo: add assertion that foods.containsKey(foodToRemove) is always true
        // todo: handle exception at VomitCommand#execute level
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

    public DailyFoodLog setDate(LocalDate date) {
        LinkedHashMap<Food, Double> foods = new LinkedHashMap<>();
        for (Food food: this.foods.keySet()) {
            foods.put(food, this.foods.get(food));
        }
        return new DailyFoodLog(foods, date);
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

    public LocalDate getLocalDate() {
        return localDate;
    }

    public DailyFoodLog consume(Food food, double quantity) {
        return new DailyFoodLog(this.add(food, quantity), localDate);
    }

    public DailyFoodLog vomit(Food food, OptionalDouble quantity) {
        return new DailyFoodLog(this.remove(food, quantity), localDate);
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

    /**
     * A method to obtain copy of existing data structure
     * @return a copy of existing DailyFoodLog
     */
    public DailyFoodLog copy() {
        LinkedHashMap<Food, Double> foods = new LinkedHashMap<>();
        for (Food food : this.foods.keySet()) {
            foods.put(food, this.foods.get(food));
        }
        return new DailyFoodLog(foods, localDate);
    }
}
