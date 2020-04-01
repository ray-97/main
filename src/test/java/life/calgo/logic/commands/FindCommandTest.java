package life.calgo.logic.commands;

import static life.calgo.commons.core.Messages.MESSAGE_FOODS_LISTED_OVERVIEW;
import static life.calgo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import life.calgo.model.ConsumptionRecord;
import life.calgo.model.Model;
import life.calgo.model.ModelManager;
import life.calgo.model.UserPrefs;
import life.calgo.model.day.DailyGoal;
import life.calgo.model.food.Name;
import life.calgo.model.food.predicates.NameContainsKeywordsPredicate;
import life.calgo.testutil.Assert;
import life.calgo.testutil.TypicalFoodItems;


/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(TypicalFoodItems.getTypicalFoodRecord(), new ConsumptionRecord(),
            new UserPrefs(), new DailyGoal());
    private Model expectedModel = new ModelManager(
            TypicalFoodItems.getTypicalFoodRecord(), model.getConsumptionRecord(), new UserPrefs(), new DailyGoal());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(new Name("Roti John"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(new Name("Strawberry Jam Sandwich"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_nameExceptionThrown() {
        Assert.assertThrows(IllegalArgumentException.class,
                "Names should only contain alphanumeric characters and spaces, "
                        + "and it should not be blank", () -> preparePredicate(" "));
    }

    @Test
    public void execute_multipleKeywords_multipleFoodItemsFound() {
        String expectedMessage = String.format(MESSAGE_FOODS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Banana Milkshake Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFoodRecord(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalFoodItems.BANANA_MILKSHAKE), model.getFilteredFoodRecord());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(new Name(userInput));
    }
}
