package f11_1.calgo.logic.commands;


import static f11_1.calgo.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import f11_1.calgo.commons.util.CollectionUtil;
import f11_1.calgo.logic.commands.exceptions.CommandException;
import f11_1.calgo.model.Model;
import f11_1.calgo.model.day.Day;
import f11_1.calgo.model.food.Food;

public class VomitCommand extends Command {

    public static final String COMMAND_WORD = "vomit";

    public static final String MESSAGE_VOMIT_FOOD_SUCCESS = "Vomit: %1$s";

    public static final String MESSAGE_SUCCESS = "%d portion of %s was consumed on %s";

    private final Day dayVomitted;
    private final Food foodVomited;

    public VomitCommand(Day dayVomitted, Food foodVomited) {
        requireAllNonNull(dayVomitted, foodVomited);
        this.dayVomitted = dayVomitted;
        this.foodVomited = foodVomited;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasDay(dayVomitted)) {
            model.addDay(dayVomitted);
        }
        model.addConsumption(dayVomitted);
        // needs some way to get portion.
        return new CommandResult(String.format(MESSAGE_SUCCESS, dayVomitted.getPortion(foodVomited),
                foodVomited, dayVomitted.getLocalDate()));
    }
}
