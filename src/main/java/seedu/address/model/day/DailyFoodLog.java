package seedu.address.model.day;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.food.Food;

public class DailyFoodLog {

    private final List<Food> foods;

    public DailyFoodLog() {
        foods = new ArrayList<>();
    }

    public DailyFoodLog(List<Food> foods) {
        this.foods = foods;
    }

    // it is this log's job to check if items with same food name. + portion if same.
    public DailyFoodLog add(Food food) {
        List<Food> newFoodList = new ArrayList<>(foods);
        // if newFoodList have food with same name/description, then increase the portion
        // else
        newFoodList.add(food);
        return new DailyFoodLog(newFoodList);
    }

    public DailyFoodLog copy() {
        List<Food> foods = new ArrayList<>();
        for (Food food:this.foods) {
            foods.add(food);
            // foods.add(food.copy()); when zx done.
        }
        return new DailyFoodLog(foods);
    }

}
