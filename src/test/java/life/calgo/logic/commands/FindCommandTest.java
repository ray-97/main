package life.calgo.logic.commands;

import static life.calgo.commons.core.Messages.MESSAGE_FOODS_LISTED_OVERVIEW;
import static life.calgo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import life.calgo.model.ConsumptionRecord;
import life.calgo.model.Model;
import life.calgo.model.ModelManager;
import life.calgo.model.UserPrefs;
import life.calgo.model.day.DailyGoal;
import life.calgo.model.food.Calorie;
import life.calgo.model.food.Carbohydrate;
import life.calgo.model.food.Fat;
import life.calgo.model.food.Name;
import life.calgo.model.food.Protein;
import life.calgo.model.food.predicates.CalorieContainsKeywordsPredicate;
import life.calgo.model.food.predicates.CarbohydrateContainsKeywordsPredicate;
import life.calgo.model.food.predicates.FatContainsKeywordsPredicate;
import life.calgo.model.food.predicates.NameContainsKeywordsPredicate;
import life.calgo.model.food.predicates.ProteinContainsKeywordsPredicate;
import life.calgo.model.food.predicates.TagContainsKeywordsPredicate;
import life.calgo.model.tag.Tag;

import life.calgo.testutil.Assert;
import life.calgo.testutil.TypicalFoodItems;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(
            TypicalFoodItems.getTypicalFoodRecord(), new ConsumptionRecord(),
            new UserPrefs(), new DailyGoal());
    private Model expectedModel = new ModelManager(
            TypicalFoodItems.getTypicalFoodRecord(), new ConsumptionRecord(),
            new UserPrefs(), new DailyGoal());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate = prepareNameContainsKeywordsPredicate("Roti John");
        NameContainsKeywordsPredicate secondPredicate = prepareNameContainsKeywordsPredicate("Strawberry Sandwich");

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same predicate -> returns true
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
    public void execute_invalidNameKeywords_exceptionThrown() {

        // Name
        Assert.assertThrows(IllegalArgumentException.class,
                Name.MESSAGE_CONSTRAINTS, () -> prepareNameContainsKeywordsPredicate(""));
        Assert.assertThrows(IllegalArgumentException.class,
                Name.MESSAGE_CONSTRAINTS, () -> prepareNameContainsKeywordsPredicate(" "));
        Assert.assertThrows(IllegalArgumentException.class,
                Name.MESSAGE_CONSTRAINTS, () -> prepareNameContainsKeywordsPredicate("-1!!!"));
        Assert.assertThrows(IllegalArgumentException.class,
                Name.MESSAGE_CONSTRAINTS, () -> prepareNameContainsKeywordsPredicate("?#!"));
        Assert.assertThrows(IllegalArgumentException.class,
                Name.MESSAGE_CONSTRAINTS, () -> prepareNameContainsKeywordsPredicate("?# !"));
        Assert.assertThrows(IllegalArgumentException.class,
                Name.MESSAGE_CONSTRAINTS, () -> prepareNameContainsKeywordsPredicate("Chicken ?#!"));
    }

    @Test
    public void execute_invalidTagKeywords_exceptionThrown() {

        // Tag
        Assert.assertThrows(IllegalArgumentException.class,
                Tag.MESSAGE_CONSTRAINTS, () -> prepareTagContainsKeywordsPredicate(""));
        Assert.assertThrows(IllegalArgumentException.class,
                Tag.MESSAGE_CONSTRAINTS, () -> prepareTagContainsKeywordsPredicate("   "));
        Assert.assertThrows(IllegalArgumentException.class,
                Tag.MESSAGE_CONSTRAINTS, () -> prepareTagContainsKeywordsPredicate("GuiltFood!"));
        Assert.assertThrows(IllegalArgumentException.class,
                Tag.MESSAGE_CONSTRAINTS, () -> prepareTagContainsKeywordsPredicate("GuiltFood Haha"));
        Assert.assertThrows(IllegalArgumentException.class,
                Tag.MESSAGE_CONSTRAINTS, () -> prepareTagContainsKeywordsPredicate("!?!@#"));

    }

    @Test
    public void execute_invalidNutritionalValueKeywords_exceptionThrown() {

        // Calorie
        Assert.assertThrows(IllegalArgumentException.class,
                Calorie.MESSAGE_CONSTRAINTS, () -> prepareCalorieContainsKeywordsPredicate(""));
        Assert.assertThrows(IllegalArgumentException.class,
                Calorie.MESSAGE_CONSTRAINTS, () -> prepareCalorieContainsKeywordsPredicate("   "));
        Assert.assertThrows(IllegalArgumentException.class,
                Calorie.MESSAGE_CONSTRAINTS, () -> prepareCalorieContainsKeywordsPredicate("a"));
        Assert.assertThrows(IllegalArgumentException.class,
                Calorie.MESSAGE_CONSTRAINTS, () -> prepareCalorieContainsKeywordsPredicate("-1"));

        // Protein
        Assert.assertThrows(IllegalArgumentException.class,
                Protein.MESSAGE_CONSTRAINTS, () -> prepareProteinContainsKeywordsPredicate(""));
        Assert.assertThrows(IllegalArgumentException.class,
                Protein.MESSAGE_CONSTRAINTS, () -> prepareProteinContainsKeywordsPredicate("    "));
        Assert.assertThrows(IllegalArgumentException.class,
                Protein.MESSAGE_CONSTRAINTS, () -> prepareProteinContainsKeywordsPredicate("a"));
        Assert.assertThrows(IllegalArgumentException.class,
                Protein.MESSAGE_CONSTRAINTS, () -> prepareProteinContainsKeywordsPredicate("-1"));

        // Carbohydrate
        Assert.assertThrows(IllegalArgumentException.class,
                Carbohydrate.MESSAGE_CONSTRAINTS, () -> prepareCarbohydrateContainsKeywordsPredicate(""));
        Assert.assertThrows(IllegalArgumentException.class,
                Carbohydrate.MESSAGE_CONSTRAINTS, () -> prepareCarbohydrateContainsKeywordsPredicate("   "));
        Assert.assertThrows(IllegalArgumentException.class,
                Carbohydrate.MESSAGE_CONSTRAINTS, () -> prepareCarbohydrateContainsKeywordsPredicate("b"));
        Assert.assertThrows(IllegalArgumentException.class,
                Carbohydrate.MESSAGE_CONSTRAINTS, () -> prepareCarbohydrateContainsKeywordsPredicate("-1"));

        // Fat
        Assert.assertThrows(IllegalArgumentException.class,
                Fat.MESSAGE_CONSTRAINTS, () -> prepareFatContainsKeywordsPredicate(""));
        Assert.assertThrows(IllegalArgumentException.class,
                Fat.MESSAGE_CONSTRAINTS, () -> prepareFatContainsKeywordsPredicate("  "));
        Assert.assertThrows(IllegalArgumentException.class,
                Fat.MESSAGE_CONSTRAINTS, () -> prepareFatContainsKeywordsPredicate("a"));
        Assert.assertThrows(IllegalArgumentException.class,
                Fat.MESSAGE_CONSTRAINTS, () -> prepareFatContainsKeywordsPredicate("-1"));

    }

    @Test
    public void execute_multipleNameKeywordsSingleNamePrefix_multipleFoodItemsFound() {
        String expectedMessage = String.format(MESSAGE_FOODS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate = prepareNameContainsKeywordsPredicate("Kurz Banana Milkshake Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFoodRecord(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalFoodItems.BANANA_MILKSHAKE), model.getFilteredFoodRecord());
    }

    @Test
    public void execute_singleKeywordForCalorie_multipleFoodItemsFound() {
        String expectedMessage = String.format(MESSAGE_FOODS_LISTED_OVERVIEW, 2);
        CalorieContainsKeywordsPredicate predicate = prepareCalorieContainsKeywordsPredicate("300");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFoodRecord(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalFoodItems.CHOCOLATE_BAR, TypicalFoodItems.GRANOLA),
                model.getFilteredFoodRecord());
    }

    @Test
    public void execute_singleKeywordForProtein_multipleFoodItemsFound() {
        String expectedMessage = String.format(MESSAGE_FOODS_LISTED_OVERVIEW, 2);
        ProteinContainsKeywordsPredicate predicate = prepareProteinContainsKeywordsPredicate("20");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFoodRecord(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalFoodItems.ALMOND, TypicalFoodItems.DUCK_RICE),
                model.getFilteredFoodRecord());
    }

    @Test
    public void execute_singleKeywordForCarbohydrate_multipleFoodItemsFound() {
        String expectedMessage = String.format(MESSAGE_FOODS_LISTED_OVERVIEW, 2);
        CarbohydrateContainsKeywordsPredicate predicate = prepareCarbohydrateContainsKeywordsPredicate("20");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFoodRecord(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(
                Arrays.asList(TypicalFoodItems.ALMOND, TypicalFoodItems.FISH_AND_CHIPS),
                model.getFilteredFoodRecord());
    }

    @Test
    public void execute_singleKeywordForFat_foodItemFound() {
        String expectedMessage = String.format(MESSAGE_FOODS_LISTED_OVERVIEW, 1);
        FatContainsKeywordsPredicate predicate = prepareFatContainsKeywordsPredicate("20");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFoodRecord(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalFoodItems.DUCK_RICE),
                model.getFilteredFoodRecord());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNameContainsKeywordsPredicate(String userInput) {
        return new NameContainsKeywordsPredicate(new Name(userInput));
    }

    /**
     * Parses {@code userInput} into a {@code CalorieContainsKeywordsPredicate}.
     */
    private CalorieContainsKeywordsPredicate prepareCalorieContainsKeywordsPredicate(String userInput) {
        return new CalorieContainsKeywordsPredicate(new Calorie(userInput));
    }

    /**
     * Parses {@code userInput} into a {@code ProteinContainsKeywordsPredicate}.
     */
    private ProteinContainsKeywordsPredicate prepareProteinContainsKeywordsPredicate(String userInput) {
        return new ProteinContainsKeywordsPredicate(new Protein(userInput));
    }

    /**
     * Parses {@code userInput} into a {@code CarbohydrateContainsKeywordsPredicate}.
     */
    private CarbohydrateContainsKeywordsPredicate prepareCarbohydrateContainsKeywordsPredicate(String userInput) {
        return new CarbohydrateContainsKeywordsPredicate(new Carbohydrate(userInput));
    }

    /**
     * Parses {@code userInput} into a {@code FatContainsKeywordsPredicate}.
     */
    private FatContainsKeywordsPredicate prepareFatContainsKeywordsPredicate(String userInput) {
        return new FatContainsKeywordsPredicate(new Fat(userInput));
    }

    /**
     * Parses {@code userInput} into a {@code TagContainsKeywordsPredicate}.
     */
    private TagContainsKeywordsPredicate prepareTagContainsKeywordsPredicate(String userInput) {
        ArrayList<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag(userInput));
        return new TagContainsKeywordsPredicate(tagList);
    }
}
