package life.calgo.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import life.calgo.commons.core.GuiSettings;
import life.calgo.commons.core.LogsCenter;
import life.calgo.logic.commands.Command;
import life.calgo.logic.commands.CommandResult;
import life.calgo.logic.commands.exceptions.CommandException;
import life.calgo.logic.parser.CalgoParser;
import life.calgo.logic.parser.exceptions.ParseException;
import life.calgo.model.Model;
import life.calgo.model.ReadOnlyFoodRecord;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.day.DailyGoal;
import life.calgo.model.food.DisplayFood;
import life.calgo.model.food.Food;
import life.calgo.model.food.predicates.FoodRecordContainsFoodNamePredicate;
import life.calgo.storage.Storage;

/**
 * The main LogicManager of the App.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CalgoParser calgoParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        calgoParser = new CalgoParser();
    }

    // General execute method for all commands

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = calgoParser.doesParserRequireModel(commandText)
                ? calgoParser.parseCommand(commandText, model)
                : calgoParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveFoodRecord(model.getFoodRecord());
            storage.saveConsumptionRecord(model.getConsumptionRecord());
            storage.saveGoal(model.getDailyGoal());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    // Getter Methods for Day package classes in Model

    @Override
    public List<Food> getSimilarFood(String foodName) {
        List<Food> filteredFood;
        filteredFood = model.getFoodRecord()
                .getFoodList().filtered(new FoodRecordContainsFoodNamePredicate(foodName));
        return filteredFood;
    }

    @Override
    public ArrayList<DailyFoodLog> getPastWeekLogs() {
        return model.getPastWeekLogs();
    }

    @Override
    public LocalDate getDate() {
        return model.getDate();
    }

    @Override
    public ReadOnlyFoodRecord getFoodRecord() {
        return model.getFoodRecord();
    }

    @Override
    public ObservableList<Food> getFilteredFoodRecord() {
        return model.getFilteredFoodRecord();
    }

    @Override
    public ObservableList<DisplayFood> getFilteredDailyList() {
        return model.getCurrentFilteredDailyList();
    }

    // Goal related Methods

    @Override
    public DailyGoal getDailyGoal() {
        return model.getDailyGoal();
    }

    @Override
    public double getRemainingCalories() {
        return model.getRemainingCalories();
    }


    // User Pref Methods

    @Override
    public Path getFoodRecordFilePath() {
        return model.getFoodRecordFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
