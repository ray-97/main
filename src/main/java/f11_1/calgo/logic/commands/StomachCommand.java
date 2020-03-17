package f11_1.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import f11_1.calgo.logic.parser.CliSyntax;
import f11_1.calgo.model.Model;


public class StomachCommand extends Command {

    public static final String COMMAND_WORD = "stomach";

    public static final String MESSAGE_SUCCESS = "Display all food consumed"; // on %s
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Display food items consumed on selected date. "
            + "Parameters: "
            + CliSyntax.PREFIX_DATE + "DATE"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_DATE + "2020-14-03 ";

    private LocalDate date;

    public StomachCommand(LocalDate date) {
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateCurrentFilteredDailyList(Model.PREDICATE_SHOW_ALL_CONSUMED_FOODS,
                date);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}