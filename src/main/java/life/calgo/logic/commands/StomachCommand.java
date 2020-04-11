package life.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import life.calgo.logic.commands.exceptions.CommandException;
import life.calgo.logic.parser.CliSyntax;
import life.calgo.model.Model;

/**
 * Displays all food consumed on a given day.
 */
public class StomachCommand extends Command {

    public static final String COMMAND_WORD = "stomach";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Display food items consumed on selected date. "
            + "Parameters: "
            + CliSyntax.PREFIX_DATE + "DATE "
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_DATE + "2020-14-03 ";

    private static final String MESSAGE_SUCCESS = "Display all food consumed"; // on %s

    private LocalDate date;

    public StomachCommand(LocalDate date) {
        requireNonNull(date);
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateDate(date);
        model.updateCurrentFilteredDailyList(Model.PREDICATE_SHOW_ALL_CONSUMED_FOODS,
                date);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StomachCommand
                && date.equals(((StomachCommand) other).date)); // instanceof handles nulls
    }
}
