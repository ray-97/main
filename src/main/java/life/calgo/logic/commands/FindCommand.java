package life.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import life.calgo.commons.core.Messages;
import life.calgo.model.Model;
import life.calgo.model.food.NameContainsKeywordsPredicate;

/**
 * Finds and lists all food in food record whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all foods which names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " happy chicken mcchicken";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredFoodRecord(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_FOODS_LISTED_OVERVIEW, model.getFilteredFoodRecord().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
