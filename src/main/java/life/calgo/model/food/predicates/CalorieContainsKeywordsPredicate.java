package life.calgo.model.food.predicates;

import java.util.function.Predicate;

import life.calgo.commons.util.StringUtil;
import life.calgo.model.food.Calorie;
import life.calgo.model.food.Food;

/**
 * Tests that a {@code Food}'s {@code Calorie} matches the {@code Calorie} keyword given.
 */
public class CalorieContainsKeywordsPredicate implements Predicate<Food> {
    private final String keyword;

    public CalorieContainsKeywordsPredicate(Calorie calorie) {
        this.keyword = calorie.value;
    }

    @Override
    public boolean test(Food food) {
        return StringUtil.containsNutritionalValueEqualTo(food.getCalorie().value, keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof CalorieContainsKeywordsPredicate
                && keyword.equals(((CalorieContainsKeywordsPredicate) other).keyword));
    }

}
