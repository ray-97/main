package life.calgo.model.food.predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import life.calgo.commons.util.StringUtil;
import life.calgo.model.food.Food;
import life.calgo.model.tag.Tag;

/**
 * Tests that any of a {@code Food}'s {@code Tag} matches any of the {@code Tag} keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Food> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<Tag> tagList) {
        this.keywords = new ArrayList<>();
        for (Tag t : tagList) {
            this.keywords.add(t.tagName);
        }
    }

    /**
     * Returns true if the keyword(s) mentioned appear as part of any Tag of the Food.
     *
     * @param food the Food to check for Tags.
     * @return whether the keyword(s) mentioned appear as part of any Tag of the Food.
     */
    @Override
    public boolean test(Food food) {
        return keywords.stream().anyMatch(keyword ->
            food.getTags().stream().anyMatch(singleTag ->
                StringUtil.containsWordIgnoreCase(singleTag.tagName, keyword)
            )
        );
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
                || (other instanceof TagContainsKeywordsPredicate
                && keywords.equals(((TagContainsKeywordsPredicate) other).keywords));
    }

}
