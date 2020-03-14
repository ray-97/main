package f11_1.calgo.model.day;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.OptionalDouble;

import f11_1.calgo.model.food.Food;

public class DailyFoodLog {

    private final HashMap<Food, Double> foods;;

    public DailyFoodLog() {
        foods = new HashMap<>();
    }

    public DailyFoodLog(HashMap<Food, Double> foods) {
        this.foods = foods;
    }

    // it is this log's job to check if items with same food name. + portion if same.
    // adapted from Vineeth.
    public DailyFoodLog add(Food foodToAdd, double quantity) {
        HashMap<Food, Double> foods = new HashMap<Food, Double>();
        for (Food food : this.foods.keySet()) {
            foods.put(food.copy(), this.foods.get(food));
        }
        if (foods.containsKey(foodToAdd)) {
            foods.put(foodToAdd, quantity + foods.get(foodToAdd));
        } else {
            foods.put(foodToAdd, quantity);
        }
        return new DailyFoodLog(foods);
    }

    public DailyFoodLog remove(Food foodToRemove, OptionalDouble quantity) {
        HashMap<Food, Double> foods = new HashMap<>();
        for (Food food : this.foods.keySet()) {
            foods.put(food.copy(), this.foods.get(food));
        }
        if (!foods.containsKey(foodToRemove)) {
            // exceptions food not in list.
        }
        else if (quantity.isEmpty()) {
            foods.remove(foodToRemove);
        }
        else if (quantity.getAsDouble() >= foods.get(foodToRemove)) {
            foods.remove(foodToRemove);
        } else {
            foods.put(foodToRemove, foods.get(foodToRemove) - quantity.getAsDouble());
        }
        return new DailyFoodLog(foods);
    }

    public double getPortion(Food food) {
        return foods.get(food);
    }

    // adapted from Vineeth.
    public DailyFoodLog copy() {
        HashMap<Food, Double> foods = new HashMap<>();
        for (Food food: this.foods.keySet()) {
            foods.put(food, this.foods.get(food));
        }
        return new DailyFoodLog(foods);
    }

}
