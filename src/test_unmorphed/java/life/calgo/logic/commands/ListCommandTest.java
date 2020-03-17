package f11_1.calgo.logic.commands;

import static f11_1.calgo.logic.commands.CommandTestUtil.assertCommandSuccess;

import f11_1.calgo.testutil.TypicalIndexes;
import f11_1.calgo.testutil.TypicalPersons;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import f11_1.calgo.model.Model;
import f11_1.calgo.model.ModelManager;
import f11_1.calgo.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getFoodRecord(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        CommandTestUtil.showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
