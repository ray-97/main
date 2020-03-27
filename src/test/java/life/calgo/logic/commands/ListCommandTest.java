package life.calgo.logic.commands;

import static life.calgo.logic.commands.CommandTestUtil.assertCommandSuccess;

import life.calgo.model.day.DailyGoal;
import life.calgo.testutil.TypicalFoodItems;
import life.calgo.testutil.TypicalIndexes;
import life.calgo.testutil.TypicalPersons;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import life.calgo.model.Model;
import life.calgo.model.ModelManager;
import life.calgo.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalFoodItems.getTypicalFoodRecord(), new UserPrefs(), new DailyGoal());
        expectedModel = new ModelManager(model.getFoodRecord(), new UserPrefs(), new DailyGoal());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        CommandTestUtil.showFoodAtIndex(model, TypicalIndexes.INDEX_FIRST_FOOD);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
