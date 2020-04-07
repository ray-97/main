package life.calgo.model.food.predicates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import life.calgo.commons.util.StringUtil;
import life.calgo.model.food.Food;
import life.calgo.model.food.Name;

/**
 * Tests that a {@code Food}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Food> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(Name name) {
        String[] nameKeywords = name.fullName.split("\\s+");
        ArrayList<String> keywords = new ArrayList<>(Arrays.asList(nameKeywords));
        this.keywords = keywords;
    }

    /**
     * Checks if the Food specified contains the keyword(s) in any part of its Name.
     *
     * @param food the Food to check against.
     * @return whether the Food specified has any matches of the keyword(s) in its Name.
     */
    @Override
    public boolean test(Food food) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(food.getName().fullName, keyword));
    }

    /**
     * Checks for equality between the current and the other predicate, using their keywords, or identity.
     *
     * @param other the other predicate to check against.
     * @return whether the current and the other predicate can be considered equal.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof NameContainsKeywordsPredicate
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords));
    }

}
