package life.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import life.calgo.commons.core.Messages;
import life.calgo.model.Model;
import life.calgo.model.food.Food;

/**
 * Finds and lists all Food in FoodRecord with Name/Tag containing any of, or nutritional value matching,
 * the argument keyword.
 * Keyword matching is case insensitive. Users can search for multiple keywords only via Name with a single n/ prefix.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all entries containing any of "
            + "the specified keywords in the Name or one of their Tags, or match the specified nutritional value. "
            + "More help in the User Guide.\nChoose only 1 of the following parameters: [n/NAME] [cal/CALORIE] "
            + "[p/PROTEIN] [c/CARBOHYDRATE] [f/FAT] [t/TAG]. NAME and TAG are case-insensitive. \n"
            + "Example: '"
            + COMMAND_WORD + " cal/150' to search by Calorie, or '"
            + COMMAND_WORD + " n/Pear' to search by Name";

    private final Predicate<Food> predicate;

    /**
     * Constructor for FindCommand, which seeks to find Food entries that satisfy a given Predicate.
     *
     * @param predicate the Predicate which the FindCommand searches Food objects to satisfy as true.
     */
    public FindCommand(Predicate<Food> predicate) {
        this.predicate = predicate;
    }

    /**
     * Executes the find command, searching through the Model to find Food objects satisfying the predicate.
     *
     * @param model {@code Model} which the command should operate on.
     * @return the CommandResult which shows the appropriate response for the user for this command.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredFoodRecord(predicate); // only show entries that satisfy a predicate to be true.
        return new CommandResult(
                String.format(Messages.MESSAGE_FOODS_LISTED_OVERVIEW, model.getFilteredFoodRecord().size()));
    }

    /**
     * Checks if the FindCommand is the same as the other specified, based off the Predicate.
     *
     * @param other the other FindCommand to compare with.
     * @return whether the FindCommand is the same as the other specified.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FindCommand
                && predicate.equals(((FindCommand) other).predicate));
    }
}
