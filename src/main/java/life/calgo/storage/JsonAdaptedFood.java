package life.calgo.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import life.calgo.commons.exceptions.IllegalValueException;
import life.calgo.model.food.Calorie;
import life.calgo.model.food.Carbohydrate;
import life.calgo.model.food.Fat;
import life.calgo.model.food.Food;
import life.calgo.model.food.Name;
import life.calgo.model.food.Protein;
import life.calgo.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Food}.
 */
public class JsonAdaptedFood {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Food's %s field is missing!";

    private final String name;
    private final String calorie;
    private final String protein;
    private final String carbohydrate;
    private final String fat;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    // Constructing JsonAdaptedFood objects

    /**
     * Constructs a {@code JsonAdaptedFood} with the given Food details.
     *
     * @param name Name of the Food.
     * @param calorie Calorie of the Food.
     * @param protein Protein of the Food.
     * @param carbohydrate Carbohydrate of the Food.
     * @param fat Fat of the Food.
     * @param tagged Tags associated with the Food.
     */
    @JsonCreator
    public JsonAdaptedFood(@JsonProperty("name") String name,
                           @JsonProperty("calorie") String calorie,
                           @JsonProperty("protein") String protein,
                           @JsonProperty("carbohydrate") String carbohydrate,
                           @JsonProperty("fat") String fat,
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
     *
     * @param source the source Food to be converted into a Jackson format.
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
     * Converts this Jackson-friendly Adapted Food object into the model's {@code Food} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted food.
     */
    public Food toModelType() throws IllegalValueException {

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
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Carbohydrate.class.getSimpleName()));
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

        final List<Tag> foodTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            foodTags.add(tag.toModelType());
        }
        final Set<Tag> modelTags = new HashSet<>(foodTags);

        return new Food(modelName, modelCalorie, modelProtein, modelCarbohydrate, modelFat, modelTags);
    }

    /**
     * Provides a String representation of the JsonAdaptedFood's Name.
     *
     * @return the String representation of the JsonAdaptedFood's Name.
     */
    public String toString() {
        return name;
    }
}
