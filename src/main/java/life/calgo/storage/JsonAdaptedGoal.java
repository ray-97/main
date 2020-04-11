package life.calgo.storage;

import com.fasterxml.jackson.annotation.JsonCreator;

import life.calgo.commons.exceptions.IllegalValueException;
import life.calgo.logic.commands.GoalCommand;
import life.calgo.model.day.DailyGoal;

/**
 * Jackson-friendly version of {@link DailyGoal}.
 */
class JsonAdaptedGoal {

    private final int dailyGoal;

    /**
     * Constructs a {@code JsonAdaptedGoal} with the given {@code dailyGoal}.
     *
     * @param dailyGoal The goal to be converted.
     */
    @JsonCreator
    public JsonAdaptedGoal(int dailyGoal) {
        this.dailyGoal = dailyGoal;
    }

    /**
     * Dummy constructor for JsonAdaptedGoal class.
     */
    public JsonAdaptedGoal() {
        this.dailyGoal = DailyGoal.DUMMY_VALUE;
    }

    /**
     * Converts this Jackson-friendly adapted dailyGoal object into the model's {@code DailyGoal} object.
     *
     * @throws IllegalValueException If there were any data constraints violated in the adapted goal.
     */
    public DailyGoal toModelType() throws IllegalValueException {
        if (!DailyGoal.isValidGoal(dailyGoal)) {
            throw new IllegalValueException(GoalCommand.MESSAGE_FAILURE);
        }
        return new DailyGoal(dailyGoal);
    }
}
