package life.calgo.storage;

import com.fasterxml.jackson.annotation.JsonCreator;

import life.calgo.commons.exceptions.IllegalValueException;
import life.calgo.model.day.DailyGoal;

/**
 * Jackson-friendly version of {@link DailyGoal}.
 */
class JsonAdaptedGoal {

    private final int dailyGoal;

    /**
     * Constructs a {@code JsonAdaptedGoal} with the given {@code dailyGoal}.
     */
    @JsonCreator
    public JsonAdaptedGoal(int dailyGoal) {
        this.dailyGoal = dailyGoal;
    }

    /**
     * Dummy constructor for JsonAdaptedTag class.
     */
    public JsonAdaptedGoal() {
        this.dailyGoal = 0;
    }

    /**
     * Converts this Jackson-friendly adapted dailyGoal object into the model's {@code DailyGoal} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public DailyGoal toModelType() throws IllegalValueException {
        if (!DailyGoal.isValidGoal(dailyGoal)) {
            throw new IllegalValueException(DailyGoal.MESSAGE_CONSTRAINTS);
        }
        return new DailyGoal(dailyGoal);
    }
}
