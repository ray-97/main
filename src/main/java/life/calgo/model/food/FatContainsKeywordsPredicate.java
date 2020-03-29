package life.calgo.model.food;

import java.util.function.Predicate;

import life.calgo.commons.util.StringUtil;

/**
 * Tests that a {@code Food}'s {@code Fat} matches the {@code Fat} keyword given.
 */
public class FatContainsKeywordsPredicate implements Predicate<Food> {
    private final String keyword;

    public FatContainsKeywordsPredicate(Fat fat) {
        this.keyword = fat.value;
    }

    @Override
    public boolean test(Food food) {
        return StringUtil.containsNutritionalValueEqualTo(food.getFat().value, keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FatContainsKeywordsPredicate
                && keyword.equals(((FatContainsKeywordsPredicate) other).keyword));
    }

}
