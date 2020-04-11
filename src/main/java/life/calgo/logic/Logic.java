package life.calgo.logic;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;

import life.calgo.commons.core.GuiSettings;
import life.calgo.logic.commands.CommandResult;
import life.calgo.logic.commands.exceptions.CommandException;
import life.calgo.logic.parser.exceptions.ParseException;
import life.calgo.model.Model;
import life.calgo.model.ReadOnlyFoodRecord;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.day.DailyGoal;
import life.calgo.model.food.DisplayFood;
import life.calgo.model.food.Food;

/**
 * API of the Logic component.
 */
public interface Logic {

    //  General execute method for all commands

    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    //  Getter Methods for Day package classes in Model

    /**
     * Returns a list of food objects with similar name.
     *
     * @param foodName The name of the food entered by user so far.
     */
    List<Food> getSimilarFood(String foodName);

    /**
     * Returns an ArrayList of DailyFoodLog objects in the past week.
     */
    ArrayList<DailyFoodLog> getPastWeekLogs();

    /**
     * Returns the date of the daily list in Model.
     */
    LocalDate getDate();

    /**
     * Returns the FoodRecord.
     *
     * @see Model#getFoodRecord()
     */
    ReadOnlyFoodRecord getFoodRecord();

    /** Returns an unmodifiable view of the filtered list of foods. */
    ObservableList<Food> getFilteredFoodRecord();

    ObservableList<DisplayFood> getFilteredDailyList();


    //  Goal related Methods

    /**
     * Returns the daily caloric goal of the user.
     */
    DailyGoal getDailyGoal();

    /**
     * Returns remaining number of calories of the user for the goal.
     */
    double getRemainingCalories();


    //   User Pref Methods

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
