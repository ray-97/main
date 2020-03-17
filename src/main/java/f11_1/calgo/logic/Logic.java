package f11_1.calgo.logic;

import java.nio.file.Path;
import java.time.LocalDate;

import f11_1.calgo.model.Model;
import f11_1.calgo.model.day.Day;
import f11_1.calgo.model.food.ConsumedFood;
import javafx.collections.ObservableList;
import f11_1.calgo.commons.core.GuiSettings;
import f11_1.calgo.logic.commands.CommandResult;
import f11_1.calgo.logic.commands.exceptions.CommandException;
import f11_1.calgo.logic.parser.exceptions.ParseException;
import f11_1.calgo.model.ReadOnlyFoodRecord;
import f11_1.calgo.model.food.Food;
import javafx.collections.ObservableMap;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the FoodRecord.
     *
     * @see Model#getFoodRecord()
     */
    ReadOnlyFoodRecord getFoodRecord();

    /** Returns an unmodifiable view of the filtered list of foods */
    ObservableList<Food> getFilteredFoodRecord();

    ObservableList<ConsumedFood> getFilteredDailyList();

    /**
     * Returns the user prefs' food record file path.
     */
    Path getFoodRecordFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
