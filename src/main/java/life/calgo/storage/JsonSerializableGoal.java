package life.calgo.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import life.calgo.commons.exceptions.IllegalValueException;
import life.calgo.model.ReadOnlyGoal;
import life.calgo.model.day.DailyGoal;

/**
 * An Immutable Goal that is serializable to JSON format.
 */
@JsonRootName(value = "goal")
class JsonSerializableGoal {

    private JsonAdaptedGoal goal;

    /**
     * Constructs a {@code JsonSerializableGoal} with the given persons.
     */
    @JsonCreator
    public JsonSerializableGoal(@JsonProperty("goal") JsonAdaptedGoal goal) {
        this.goal = goal;
    }

    /**
     * Converts a given {@code ReadOnlyGoal} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableGoal}.
     */
    public JsonSerializableGoal(ReadOnlyGoal source) {
        this.goal = new JsonAdaptedGoal(source.getGoal());
    }

    /**
     * Converts this goal into the model's {@code DailyGoal} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public DailyGoal toModelType() throws IllegalValueException {
        return goal.toModelType();
    }
}
