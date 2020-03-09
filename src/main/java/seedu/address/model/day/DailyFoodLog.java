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
    public DailyFoodLog add(Food foodToAdd) {
        List<Food> foods = new ArrayList<>();
        for (Food food:this.foods) {
            foods.add(food.copy());
        }
        if (foods.contains(foodToAdd)) {
            Food existingFood = foods.get(foods.indexOf(foodToAdd));
            double difference = foodToAdd.getPortion() - existingFood.getPortion();
            foods.set(foods.indexOf(foodToAdd), existingFood.setPortion(difference));
        } else {
            foods.add(foodToAdd);
        }
        return new DailyFoodLog(foods);
    }

    public DailyFoodLog copy() {
        List<Food> foods = new ArrayList<>();
        for (Food food:this.foods) {
            foods.add(food.copy());
        }
        return new DailyFoodLog(foods);
    }

}
