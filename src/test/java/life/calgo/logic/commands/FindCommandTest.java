package life.calgo.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static life.calgo.commons.core.Messages.MESSAGE_FOODS_LISTED_OVERVIEW;
import static life.calgo.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.Arrays;
import java.util.Collections;

import life.calgo.model.day.DailyGoal;
import life.calgo.testutil.TypicalFoodItems;
import life.calgo.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;

import life.calgo.model.Model;
import life.calgo.model.ModelManager;
import life.calgo.model.UserPrefs;
import life.calgo.model.food.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(TypicalFoodItems.getTypicalFoodRecord(), new UserPrefs(), new DailyGoal());
    private Model expectedModel = new ModelManager(TypicalFoodItems.getTypicalFoodRecord(), new UserPrefs(), new DailyGoal());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

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
    public void execute_zeroKeywords_noFoodFound() {
        String expectedMessage = String.format(MESSAGE_FOODS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFoodRecord(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredFoodRecord());
    }

    @Test
    public void execute_multipleKeywords_multipleFoodItemsFound() {
        String expectedMessage = String.format(MESSAGE_FOODS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Banana Milkshake Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFoodRecord(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalFoodItems.APPLE, TypicalFoodItems.BANANA_MILKSHAKE, TypicalFoodItems.CHOCOLATE_BAR), model.getFilteredFoodRecord());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
