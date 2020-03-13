package f11_1.calgo.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import f11_1.calgo.commons.core.GuiSettings;
import f11_1.calgo.commons.core.LogsCenter;
import f11_1.calgo.logic.commands.Command;
import f11_1.calgo.logic.commands.CommandResult;
import f11_1.calgo.logic.commands.exceptions.CommandException;
import f11_1.calgo.logic.parser.FoodRecordParser;
import f11_1.calgo.logic.parser.exceptions.ParseException;
import f11_1.calgo.model.Model;
import f11_1.calgo.model.ReadOnlyFoodRecord;
import f11_1.calgo.model.food.Food;
import f11_1.calgo.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final FoodRecordParser foodRecordParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        foodRecordParser = new FoodRecordParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = foodRecordParser.parseCommand(commandText, model);
        commandResult = command.execute(model);

        try {
            storage.saveFoodRecord(model.getFoodRecord());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
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
