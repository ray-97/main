package life.calgo.model.food.predicates;

import java.util.function.Predicate;

import life.calgo.commons.util.StringUtil;
import life.calgo.model.food.Carbohydrate;
import life.calgo.model.food.Food;

/**
 * Tests that a {@code Food}'s {@code Carbohydrate} matches the {@code Carbohydrate} keyword given.
 */
public class CarbohydrateContainsKeywordsPredicate implements Predicate<Food> {
    private final String keyword;

    public CarbohydrateContainsKeywordsPredicate(Carbohydrate carbohydrate) {
        this.keyword = carbohydrate.value;
    }

    /**
     * Checks if the Food specified has an exact match in the Carbohydrate value.
     *
     * @param food the Food to check against.
     * @return whether the Food specified has an exact match in the Carbohydrate value.
     */
    @Override
    public boolean test(Food food) {
        return StringUtil.containsNutritionalValueEqualTo(food.getCarbohydrate().value, keyword);
    }

    /**
     * Checks for equality between the current and the other predicate, using their keyword, or identity.
     *
     * @param other the other predicate to check against.
     * @return whether the current and the other predicate can be considered equal.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof CarbohydrateContainsKeywordsPredicate
                && keyword.equals(((CarbohydrateContainsKeywordsPredicate) other).keyword));
    }

}
