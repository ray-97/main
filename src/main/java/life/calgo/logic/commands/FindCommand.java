package life.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import life.calgo.commons.core.Messages;
import life.calgo.model.Model;
import life.calgo.model.food.Food;
import life.calgo.model.food.NameContainsKeywordsPredicate;
import life.calgo.model.food.ProteinContainsKeywordsPredicate;
import life.calgo.model.food.TagContainsKeywordsPredicate;

/**
 * Finds and lists all food in food record whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all entries containing any of "
            + "the specified keywords in the Name or one of their Tags, or match the  specified nutritional value.\n"
            + "Choose 1 of the following parameters: [n/NAME] [cal/CALORIES] [p/PROTEIN] [c/CARBOHYDRATES] [f/FATS] "
            + "[t/TAG]. NAME and TAG are case-insensitive. \n"
            + "Example: " + COMMAND_WORD + " cal/150";

    private final Predicate<Food> predicate;

    public FindCommand(Predicate<Food> predicate) {
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
        return other == this
                || (other instanceof FindCommand
                && predicate.equals(((FindCommand) other).predicate));
    }
}
