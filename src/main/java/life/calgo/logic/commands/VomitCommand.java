package life.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import life.calgo.logic.commands.exceptions.CommandException;
import life.calgo.model.Model;
import life.calgo.model.day.Day;
import life.calgo.model.food.Food;
import life.calgo.commons.util.CollectionUtil;

public class VomitCommand extends Command {

    public static final String COMMAND_WORD = "vomit";

    public static final String MESSAGE_SUCCESS = "Successfully throw up %1$s"; // %d portion of %s was consumed on %s

    private final Day dayVomited;
    private final Food foodVomited;

    public VomitCommand(Day dayVomitted, Food foodVomited) {
        CollectionUtil.requireAllNonNull(dayVomitted, foodVomited);
        this.dayVomited = dayVomitted;
        this.foodVomited = foodVomited;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasDay(dayVomited)) {
            model.addDay(dayVomited);
        }
        model.addConsumption(dayVomited);
        model.updateCurrentFilteredDailyList(Model.PREDICATE_SHOW_ALL_CONSUMED_FOODS,
                dayVomited.getLocalDate());
        return new CommandResult(String.format(MESSAGE_SUCCESS, foodVomited));
                // dayVomited.getPortion(foodVomited), foodVomited, dayVomited.getLocalDate()));
    }
}
