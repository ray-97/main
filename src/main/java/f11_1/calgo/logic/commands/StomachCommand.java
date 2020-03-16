package f11_1.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import f11_1.calgo.model.Model;


public class StomachCommand extends Command {

    public static final String COMMAND_WORD = "stomach";

    public static final String MESSAGE_SUCCESS = "Display all food consumed"; // on %s

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