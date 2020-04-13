package life.calgo.model.food.predicates;

import java.util.function.Predicate;

import life.calgo.model.food.Food;

/**
 * Tests that a {@code Food}'s {@code Calorie} matches the {@code Calorie} keyword given.
 */
public class FoodRecordContainsFoodNamePredicate implements Predicate<Food> {
    private final String foodName;

    public FoodRecordContainsFoodNamePredicate(String foodName) {
        this.foodName = foodName;
    }

    @Override
    public boolean test(Food food) {
        boolean foodStartsWithInputFoodName = food.getName().fullName.toLowerCase()
                .startsWith(foodName.toLowerCase().trim());
        boolean inputFoodNameStartsWithFood = foodName.toLowerCase().trim()
                .startsWith(food.getName().fullName.toLowerCase());

        return foodStartsWithInputFoodName || inputFoodNameStartsWithFood;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FoodRecordContainsFoodNamePredicate
                && foodName.equals(((FoodRecordContainsFoodNamePredicate) other).foodName));
    }

}
