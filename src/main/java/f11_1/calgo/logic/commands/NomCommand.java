package f11_1.calgo.logic.commands;

import static f11_1.calgo.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import f11_1.calgo.commons.util.CollectionUtil;
import f11_1.calgo.logic.commands.exceptions.CommandException;
import f11_1.calgo.model.Model;
import f11_1.calgo.model.day.Day;
import f11_1.calgo.model.food.Food;

public class NomCommand extends Command {

    public static final String MESSAGE_SUCCESS = "%d portion of %s was consumed on %s";
    public static final String COMMAND_WORD = "nom";

    private final Day dayConsumed;
    private final Food foodConsumed;

    public NomCommand(Day dayConsumed, Food foodConsumed) {
        requireAllNonNull(dayConsumed, foodConsumed);
        this.dayConsumed = dayConsumed;
        this.foodConsumed = foodConsumed;
    }
    // Flow from GUI: mainwindow -> executeCommand handling help and exit <- logic.execute(commandResult),
    // and also interacts with AB storage(which gets AB from model) <- ABparser results commandResult
    // <- respective parser with feedback in commandResult -> passes Day into model -> model gives Day to save in AB model

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasDay(dayConsumed)) {
            model.addDay(dayConsumed);
        }
        model.addConsumption(dayConsumed);
        // needs some way to get portion.
        return new CommandResult(String.format(MESSAGE_SUCCESS, dayConsumed.getPortion(foodConsumed),
                foodConsumed, dayConsumed.getLocalDate()));
    }

}
