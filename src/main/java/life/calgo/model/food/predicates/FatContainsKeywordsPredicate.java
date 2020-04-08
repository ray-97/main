package life.calgo.model.food.predicates;

import java.util.function.Predicate;

import life.calgo.commons.util.StringUtil;
import life.calgo.model.food.Fat;
import life.calgo.model.food.Food;

/**
 * Tests that a {@code Food}'s {@code Fat} matches the {@code Fat} keyword given.
 */
public class FatContainsKeywordsPredicate implements Predicate<Food> {
    private final String keyword;

    public FatContainsKeywordsPredicate(Fat fat) {
        this.keyword = fat.value;
    }

    /**
     * Checks if the Food specified has an exact match in the Fat value.
     *
     * @param food the Food to check against.
     * @return whether the Food specified has an exact match in the Fat value.
     */
    @Override
    public boolean test(Food food) {
        return StringUtil.containsNutritionalValueEqualTo(food.getFat().value, keyword);
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
                || (other instanceof FatContainsKeywordsPredicate
                && keyword.equals(((FatContainsKeywordsPredicate) other).keyword));
    }

}
