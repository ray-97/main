package life.calgo.logic.commands;

import static life.calgo.logic.parser.CliSyntax.PREFIX_CALORIES;
import static life.calgo.logic.parser.CliSyntax.PREFIX_CARBOHYDRATE;
import static life.calgo.logic.parser.CliSyntax.PREFIX_FAT;
import static life.calgo.logic.parser.CliSyntax.PREFIX_NAME;
import static life.calgo.logic.parser.CliSyntax.PREFIX_PROTEIN;
import static life.calgo.logic.parser.CliSyntax.PREFIX_TAG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import life.calgo.commons.core.index.Index;
import life.calgo.logic.commands.exceptions.CommandException;
import life.calgo.model.FoodRecord;
import life.calgo.model.Model;
import life.calgo.model.food.Food;
import life.calgo.model.food.predicates.NameContainsKeywordsPredicate;
import life.calgo.testutil.Assert;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_APPLE = "Apple";
    public static final String VALID_NAME_BANANA = "Banana";
    public static final String VALID_CALORIE_APPLE = "100";
    public static final String VALID_CALORIE_BANANA = "200";
    public static final String VALID_PROTEIN_APPLE = "3";
    public static final String VALID_PROTEIN_BANANA = "1";
    public static final String VALID_CARBOHYDRATE_APPLE = "40";
    public static final String VALID_CARBOHYDRATE_BANANA = "50";
    public static final String VALID_FAT_APPLE = "1";
    public static final String VALID_FAT_BANANA = "12";
    public static final String VALID_TAG_HARD = "hard";
    public static final String VALID_TAG_SOFT = "soft";

    public static final String NAME_DESC_APPLE = " " + PREFIX_NAME + VALID_NAME_APPLE;
    public static final String NAME_DESC_BANANA = " " + PREFIX_NAME + VALID_NAME_BANANA;
    public static final String CALORIE_DESC_APPLE = " " + PREFIX_CALORIES + VALID_CALORIE_APPLE;
    public static final String CALORIE_DESC_BANANA = " " + PREFIX_CALORIES + VALID_CALORIE_BANANA;
    public static final String CARBOHYDRATE_DESC_APPLE = " " + PREFIX_CARBOHYDRATE + VALID_CARBOHYDRATE_APPLE;
    public static final String CARBOHYDRATE_DESC_BANANA = " " + PREFIX_CARBOHYDRATE + VALID_CARBOHYDRATE_BANANA;
    public static final String PROTEIN_DESC_APPLE = " " + PREFIX_PROTEIN + VALID_PROTEIN_APPLE;
    public static final String PROTEIN_DESC_BANANA = " " + PREFIX_PROTEIN + VALID_PROTEIN_BANANA;
    public static final String FAT_DESC_APPLE = " " + PREFIX_FAT + VALID_FAT_APPLE;
    public static final String FAT_DESC_BANANA = " " + PREFIX_FAT + VALID_FAT_BANANA;
    public static final String TAG_DESC_HARD = " " + PREFIX_TAG + VALID_TAG_HARD;
    public static final String TAG_DESC_SOFT = " " + PREFIX_TAG + VALID_TAG_SOFT;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "Choco&"; // '&' not allowed in names
    public static final String INVALID_CALORIE_DESC = " " + PREFIX_CALORIES + "1a"; // 'a' not allowed in calorie
    public static final String INVALID_PROTEIN_DESC = " " + PREFIX_PROTEIN; // empty string not allowed for protein
    public static final String INVALID_CARBOHYDRATE_DESC = " " + PREFIX_CARBOHYDRATE + "bob"; // only integers allowed
    public static final String INVALID_FAT_DESC = " " + PREFIX_FAT; // empty string not allowed for fat
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "#^#^#*#*"; // symbols not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";


    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        FoodRecord expectedFoodRecord = new FoodRecord(actualModel.getFoodRecord());
        List<Food> expectedFilteredList = new ArrayList<>(actualModel.getFilteredFoodRecord());

        Assert.assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedFoodRecord, actualModel.getFoodRecord());
        assertEquals(expectedFilteredList, actualModel.getFilteredFoodRecord());
    }
    /**
     * Updates {@code model}'s filtered list to show only the food at the given {@code targetIndex} in the
     * {@code model}'s Food Record.
     */
    public static void showFoodAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredFoodRecord().size());

        Food food = model.getFilteredFoodRecord().get(targetIndex.getZeroBased());
        model.updateFilteredFoodRecord(new NameContainsKeywordsPredicate(food.getName()));

        assertEquals(1, model.getFilteredFoodRecord().size());
    }

}
