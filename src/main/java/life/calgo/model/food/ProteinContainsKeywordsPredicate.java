package life.calgo.model.food;

import java.util.function.Predicate;

import life.calgo.commons.util.StringUtil;

/**
 * Tests that a {@code Food}'s {@code Protein} matches the {@code Protein} keyword given.
 */
public class ProteinContainsKeywordsPredicate implements Predicate<Food> {
    private final String keyword;

    public ProteinContainsKeywordsPredicate(Protein protein) {
        this.keyword = protein.value;
    }

    @Override
    public boolean test(Food food) {
        return StringUtil.containsNutritionalValueEqualTo(food.getProtein().value, keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProteinContainsKeywordsPredicate // instanceof handles nulls
                && keyword.equals(((ProteinContainsKeywordsPredicate) other).keyword)); // state check
    }

}
