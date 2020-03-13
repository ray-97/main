package f11_1.calgo.model.day;

import java.util.HashMap;
import java.util.Set;

import f11_1.calgo.model.food.Food;

public class DailyFoodLog {

    private final HashMap<Food, Double> foods;

    public DailyFoodLog() {
        this.foods = new HashMap<>();
    }

    public DailyFoodLog(HashMap<Food, Double> foods) {
        this.foods = foods;
    }

    // it is this log's job to check if items with same food name. + portion if same.
    public DailyFoodLog add(Food foodToAdd, double quantity) {
        HashMap<Food, Double> foods = new HashMap<>();
        for (Food food: this.foods.keySet()) {
            foods.put(food, this.foods.get(food));
        }
        if (foods.containsKey(foodToAdd)) {
            foods.put(foodToAdd, quantity + foods.get(foodToAdd));
        } else {
            foods.put(foodToAdd, quantity);
        }
        return new DailyFoodLog(foods);
    }

    public DailyFoodLog copy() {
        HashMap<Food, Double> foods = new HashMap<>();
        for (Food food: this.foods.keySet()) {
            foods.put(food, this.foods.get(food));
        }
        return new DailyFoodLog(foods);
    }


    public Set<Food> getFoods() {
        return this.foods.keySet();
    }

    public double getQuantity(Food food) {
        return this.foods.get(food);
    }
}