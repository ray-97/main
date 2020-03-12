package f11_1.calgo.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import f11_1.calgo.commons.exceptions.IllegalValueException;
import f11_1.calgo.model.food.Calorie;
import f11_1.calgo.model.food.Carbohydrate;
import f11_1.calgo.model.food.Fat;
import f11_1.calgo.model.food.Protein;
import f11_1.calgo.model.food.Food;
import f11_1.calgo.model.food.Name;
import f11_1.calgo.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Food}.
 */
class JsonAdaptedFood {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Food's %s field is missing!";

    private final String name;
    private final String calorie;
    private final String protein;
    private final String carbohydrate;
    private final String fat;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    // Constructing JsonAdaptedFood objects

    /**
     * Constructs a {@code JsonAdaptedFood} with the given food details.
     */
    @JsonCreator
    public JsonAdaptedFood(@JsonProperty("name") String name,
                           @JsonProperty("calorie") String calorie,
                           @JsonProperty("protein (g)") String protein,
                           @JsonProperty("carbohydrate (g)") String carbohydrate,
                           @JsonProperty("fat (g)") String fat,
                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.calorie = calorie;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Food} into this class for Jackson use.
     */
    public JsonAdaptedFood(Food source) {
        name = source.getName().fullName;
        calorie = source.getCalorie().value;
        protein = source.getProtein().value;
        carbohydrate = source.getCarbohydrate().value;
        fat = source.getFat().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    // Converting from JsonAdaptedFood objects to Model-friendly objects

    /**
     * Converts this Jackson-friendly adapted food object into the model's {@code Food} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted food.
     */
    public Food toModelType() throws IllegalValueException {
        final List<Tag> foodTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            foodTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (calorie == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Calorie.class.getSimpleName()));
        }
        if (!Calorie.isValidCalorie(calorie)) {
            throw new IllegalValueException(Calorie.MESSAGE_CONSTRAINTS);
        }
        final Calorie modelCalorie = new Calorie(calorie);

        if (protein == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Protein.class.getSimpleName()));
        }
        if (!Protein.isValidProtein(protein)) {
            throw new IllegalValueException(Protein.MESSAGE_CONSTRAINTS);
        }
        final Protein modelProtein = new Protein(protein);

        if (carbohydrate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Carbohydrate.class.getSimpleName()));
        }
        if (!Carbohydrate.isValidCarbohydrate(carbohydrate)) {
            throw new IllegalValueException(Carbohydrate.MESSAGE_CONSTRAINTS);
        }
        final Carbohydrate modelCarbohydrate = new Carbohydrate(carbohydrate);

        if (fat == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Fat.class.getSimpleName()));
        }
        if (!Fat.isValidFat(fat)) {
            throw new IllegalValueException(Fat.MESSAGE_CONSTRAINTS);
        }
        final Fat modelFat = new Fat(fat);

        final Set<Tag> modelTags = new HashSet<>(foodTags);
        return new Food(modelName, modelCalorie, modelProtein, modelCarbohydrate, modelFat, modelTags);
    }

}
