package life.calgo.storage;

import static life.calgo.storage.JsonAdaptedFood.MISSING_FIELD_MESSAGE_FORMAT;
import static life.calgo.testutil.Assert.assertThrows;
import static life.calgo.testutil.TypicalFoodItems.BANANA_MILKSHAKE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import life.calgo.commons.exceptions.IllegalValueException;
import life.calgo.model.food.Calorie;
import life.calgo.model.food.Carbohydrate;
import life.calgo.model.food.Fat;
import life.calgo.model.food.Name;
import life.calgo.model.food.Protein;

public class JsonAdaptedFoodTest {
    private static final String INVALID_NAME = "@APPLE!";
    private static final String INVALID_CALORIE = "+651234";
    private static final String INVALID_CARBOHYDRATE = " ";
    private static final String INVALID_PROTEIN = "ABC!";
    private static final String INVALID_FAT = "IAMNOTFAT?!";
    private static final String INVALID_TAG = "# u a dum";

    private static final String VALID_NAME = BANANA_MILKSHAKE.getName().toString();
    private static final String VALID_CALORIE = BANANA_MILKSHAKE.getCalorie().toString();
    private static final String VALID_PROTEIN = BANANA_MILKSHAKE.getProtein().toString();
    private static final String VALID_CARBOHYDRATE = BANANA_MILKSHAKE.getFat().toString();
    private static final String VALID_FAT = BANANA_MILKSHAKE.getFat().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BANANA_MILKSHAKE.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validFoodDetails_returnsFood() throws Exception {
        JsonAdaptedFood food = new JsonAdaptedFood(BANANA_MILKSHAKE);
        assertEquals(BANANA_MILKSHAKE, food.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedFood food = new JsonAdaptedFood(INVALID_NAME, VALID_CALORIE, VALID_PROTEIN,
                        VALID_CARBOHYDRATE, VALID_FAT, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedFood food = new JsonAdaptedFood(null, VALID_CALORIE, VALID_PROTEIN,
                VALID_CARBOHYDRATE, VALID_FAT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidCalorie_throwsIllegalValueException() {
        JsonAdaptedFood food = new JsonAdaptedFood(VALID_NAME, INVALID_CALORIE, VALID_PROTEIN,
                        VALID_CARBOHYDRATE, VALID_FAT, VALID_TAGS);
        String expectedMessage = Calorie.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_nullCalorie_throwsIllegalValueException() {
        JsonAdaptedFood food = new JsonAdaptedFood(VALID_NAME, null, VALID_PROTEIN,
                VALID_CARBOHYDRATE, VALID_FAT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Calorie.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidCarbohydrate_throwsIllegalValueException() {
        JsonAdaptedFood food = new JsonAdaptedFood(VALID_NAME, VALID_CALORIE, VALID_PROTEIN,
                        INVALID_CARBOHYDRATE, VALID_FAT, VALID_TAGS);
        String expectedMessage = Carbohydrate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_nullCarbohydrate_throwsIllegalValueException() {
        JsonAdaptedFood food = new JsonAdaptedFood(VALID_NAME, VALID_CALORIE, VALID_PROTEIN,
                null, VALID_FAT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Carbohydrate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidProtein_throwsIllegalValueException() {
        JsonAdaptedFood food = new JsonAdaptedFood(VALID_NAME, VALID_CALORIE, INVALID_PROTEIN,
                        VALID_CARBOHYDRATE, VALID_FAT, VALID_TAGS);
        String expectedMessage = Protein.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_nullProtein_throwsIllegalValueException() {
        JsonAdaptedFood food = new JsonAdaptedFood(VALID_NAME, VALID_CALORIE, null,
                VALID_CARBOHYDRATE, VALID_FAT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Protein.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidFat_throwsIllegalValueException() {
        JsonAdaptedFood food = new JsonAdaptedFood(VALID_NAME, VALID_CALORIE, VALID_PROTEIN,
                        VALID_CARBOHYDRATE, INVALID_FAT, VALID_TAGS);
        String expectedMessage = Fat.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_nullFat_throwsIllegalValueException() {
        JsonAdaptedFood food = new JsonAdaptedFood(VALID_NAME, VALID_CALORIE, VALID_PROTEIN,
                VALID_CARBOHYDRATE, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Fat.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedFood food = new JsonAdaptedFood(VALID_NAME, VALID_CALORIE, VALID_PROTEIN,
                VALID_CARBOHYDRATE, VALID_FAT, invalidTags);
        assertThrows(IllegalValueException.class, food::toModelType);
    }

}
