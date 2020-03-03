package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.day.Day;
import seedu.address.model.food.Food;

public class ConsumeCommand extends Command {

    private final Food toConsume;
    private final Day dayConsumed;

    public ConsumeCommand(Food food, Day day) {
        requireNonNull(food);
        toConsume = food;
        dayConsumed = day;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasDay(dayConsumed)) {
            model.addDay(dayConsumed);
        }
            // add food to consume on that day
            // for dayAfterConsuming, see editedperson
            model.addConsumption(dayConsumed, dayAfterConsuming);
            // return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        }
    }

}
