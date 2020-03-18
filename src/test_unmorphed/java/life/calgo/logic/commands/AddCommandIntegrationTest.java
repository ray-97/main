package f11_1.calgo.logic.commands;

import static f11_1.calgo.logic.commands.CommandTestUtil.assertCommandFailure;
import static f11_1.calgo.logic.commands.CommandTestUtil.assertCommandSuccess;

import f11_1.calgo.testutil.FoodBuilder;
import f11_1.calgo.testutil.TypicalPersons;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import f11_1.calgo.model.Model;
import f11_1.calgo.model.ModelManager;
import f11_1.calgo.model.UserPrefs;
import f11_1.calgo.model.food.Food;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Food validFood = new FoodBuilder().build();

        Model expectedModel = new ModelManager(model.getFoodRecord(), new UserPrefs());
        expectedModel.addFood(validFood);

        assertCommandSuccess(new AddCommand(validFood), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validFood), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Food foodInList = model.getFoodRecord().getFoodList().get(0);
        assertCommandFailure(new AddCommand(foodInList), model, AddCommand.MESSAGE_DUPLICATE_FOOD);
    }

}
