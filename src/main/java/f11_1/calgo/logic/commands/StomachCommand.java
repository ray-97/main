package f11_1.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import f11_1.calgo.model.Model;


public class StomachCommand extends Command {

    public static final String COMMAND_WORD = "stomach";

    public static final String MESSAGE_SUCCESS = "Display all food consumed"; // on %s

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // so how do we output to GUI?
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
