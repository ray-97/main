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

    @Override
    public boolean test(Food food) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(food.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
