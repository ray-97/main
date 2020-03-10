package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

public class StomachCommand extends Command {

    public static final String COMMAND_WORD = "stomach";

    public static final String MESSAGE_SUCCESS = "Listed all food consumed on %s";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // so how do we output to GUI?
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
