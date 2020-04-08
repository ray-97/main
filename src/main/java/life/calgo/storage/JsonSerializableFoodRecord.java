package life.calgo.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import life.calgo.commons.exceptions.IllegalValueException;
import life.calgo.model.FoodRecord;
import life.calgo.model.ReadOnlyFoodRecord;
import life.calgo.model.food.Food;

/**
 * An Immutable FoodRecord that is serializable to JSON format.
 * This contains a number of JsonAdaptedFood objects.
 */
@JsonRootName(value = "foodrecord")
class JsonSerializableFoodRecord {

    public static final String MESSAGE_DUPLICATE_FOOD = "Food Record contains duplicate Food(s).";

    private final List<JsonAdaptedFood> foods = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFoodRecord} with the given persons.
     */
    @JsonCreator
    public JsonSerializableFoodRecord(@JsonProperty("foods") List<JsonAdaptedFood> foods) {
        this.foods.addAll(foods);
    }

    /**
     * Converts a given {@code ReadOnlyFoodRecord} into this class for Jackson use.
     *
     * @param source the source FoodRecord. Note that future changes to this will not affect
     *               the created {@code JsonSerializableFoodRecord}.
     */
    public JsonSerializableFoodRecord(ReadOnlyFoodRecord source) {
        foods.addAll(source.getFoodList().stream().map(JsonAdaptedFood::new).collect(Collectors.toList()));
    }

    /**
     * Converts this JsonSerializableFoodRecord into the Model's {@code FoodRecord} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FoodRecord toModelType() throws IllegalValueException {
        FoodRecord foodRecord = new FoodRecord();
        for (JsonAdaptedFood jsonAdaptedFood : foods) {
            Food food = jsonAdaptedFood.toModelType();
            if (foodRecord.hasFood(food)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FOOD);
            }
            foodRecord.addFood(food);
        }
        return foodRecord;
    }

}
