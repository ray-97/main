package life.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import life.calgo.logic.commands.exceptions.CommandException;
import life.calgo.model.Model;
import life.calgo.model.ReadOnlyFoodRecord;
import life.calgo.storage.ExportGenerator;

/**
 * Generates a FoodRecord.txt file in the documents folder showing Food Record entries.
 * Name, Nutritional Values and Tags are all shown, with Food items in alphabetical order.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_SUCCESS = "Successfully generated FoodRecord.txt in the data/exports folder.";

    public static final String MESSAGE_FAILURE = "Did not manage to generate FoodRecord.txt.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ReadOnlyFoodRecord foodRecord = model.getFoodRecord();
        ExportGenerator exportGenerator = new ExportGenerator(foodRecord);
        boolean isSuccessfullyExported = exportGenerator.generateExport();
        if (!isSuccessfullyExported) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
