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
        return food.getName().fullName.startsWith(foodName.trim())
                || foodName.trim().startsWith(food.getName().fullName);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FoodRecordContainsFoodNamePredicate
                && foodName.equals(((FoodRecordContainsFoodNamePredicate) other).foodName));
    }

}
