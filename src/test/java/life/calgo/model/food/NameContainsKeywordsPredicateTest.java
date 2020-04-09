package life.calgo.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import life.calgo.model.food.predicates.NameContainsKeywordsPredicate;
import life.calgo.testutil.Assert;
import life.calgo.testutil.FoodBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Name firstPredicateKeywordsName = new Name("Kiwi");
        Name secondPredicateKeywordsName = new Name("Kiwi Jam");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordsName);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordsName);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy =
                new NameContainsKeywordsPredicate(firstPredicateKeywordsName);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(new Name("Apple"));
        assertTrue(predicate.test(new FoodBuilder().withName("Apple Banana").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(new Name("Apple Banana"));
        assertTrue(predicate.test(new FoodBuilder().withName("Apple Banana").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(new Name("Banana Chocolate"));
        assertTrue(predicate.test(new FoodBuilder().withName("Apple Chocolate").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(new Name("aPPle bANANA"));
        assertTrue(predicate.test(new FoodBuilder().withName("Apple Banana").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        Assert.assertThrows(IllegalArgumentException.class,
                "Names should only contain alphanumeric characters and spaces, "
                + "and it should not be blank.", () -> new NameContainsKeywordsPredicate(new Name("")));

        // Non-matching keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(new Name("Chocolate"));
        assertFalse(predicate.test(new FoodBuilder().withName("Apple Banana").build()));

    }
}
