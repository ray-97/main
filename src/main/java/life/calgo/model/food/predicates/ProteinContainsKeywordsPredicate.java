package life.calgo.model.food.predicates;

import java.util.function.Predicate;

import life.calgo.commons.util.StringUtil;
import life.calgo.model.food.Food;
import life.calgo.model.food.Protein;

/**
 * Tests that a {@code Food}'s {@code Protein} matches the {@code Protein} keyword given.
 */
public class ProteinContainsKeywordsPredicate implements Predicate<Food> {
    private final String keyword;

    public ProteinContainsKeywordsPredicate(Protein protein) {
        this.keyword = protein.value;
    }

    /**
     * Checks if the Food specified has an exact match in the Protein value.
     *
     * @param food the Food to check against.
     * @return whether the Food specified has an exact match in the Protein value.
     */
    @Override
    public boolean test(Food food) {
        return StringUtil.containsNutritionalValueEqualTo(food.getProtein().value, keyword);
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
                || (other instanceof ProteinContainsKeywordsPredicate
                && keyword.equals(((ProteinContainsKeywordsPredicate) other).keyword));
    }

}
