package life.calgo.model.food;

import java.util.function.Predicate;

import life.calgo.commons.util.StringUtil;

/**
 * Tests that a {@code Food}'s {@code Carbohydrate} matches the {@code Carbohydrate} keyword given.
 */
public class CarbohydrateContainsKeywordsPredicate implements Predicate<Food> {
    private final String keyword;

    public CarbohydrateContainsKeywordsPredicate(Carbohydrate carbohydrate) {
        this.keyword = carbohydrate.value;
    }

    @Override
    public boolean test(Food food) {
        return StringUtil.containsNutritionalValueEqualTo(food.getCarbohydrate().value, keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof CarbohydrateContainsKeywordsPredicate
                && keyword.equals(((CarbohydrateContainsKeywordsPredicate) other).keyword));
    }

}
