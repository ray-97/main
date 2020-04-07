package life.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static life.calgo.logic.commands.CommandTestUtil.assertCommandFailure;
import static life.calgo.testutil.TypicalFoodItems.getTypicalFoodRecord;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import life.calgo.commons.core.GuiSettings;
import life.calgo.model.ConsumptionRecord;
import life.calgo.model.FoodRecord;
import life.calgo.model.Model;
import life.calgo.model.ModelManager;
import life.calgo.model.ReadOnlyConsumptionRecord;
import life.calgo.model.ReadOnlyFoodRecord;
import life.calgo.model.ReadOnlyUserPrefs;
import life.calgo.model.UserPrefs;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.day.DailyGoal;
import life.calgo.model.food.DisplayFood;
import life.calgo.model.food.Food;
import life.calgo.model.food.Name;
import life.calgo.testutil.Assert;
import life.calgo.testutil.FoodBuilder;
import life.calgo.testutil.TypicalFoodItems;

public class NomCommandTest {

    private Model model = new ModelManager(
            TypicalFoodItems.getTypicalFoodRecord(), new ConsumptionRecord(),
            new UserPrefs(), new DailyGoal());
    private Model expectedModel = new ModelManager(
            TypicalFoodItems.getTypicalFoodRecord(), new ConsumptionRecord(),
            new UserPrefs(), new DailyGoal());

    @Test
    public void constructor_nullFood_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new NomCommand(null, null));
    }
}