package life.calgo.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static life.calgo.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import life.calgo.commons.exceptions.IllegalValueException;
import life.calgo.commons.util.JsonUtil;
import life.calgo.model.FoodRecord;
import life.calgo.testutil.TypicalPersons;

public class JsonSerializableProteinBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableFoodRecordTest");
    private static final Path TYPICAL_FOODITEMS_FILE = TEST_DATA_FOLDER.resolve("typicalFoodItemsFoodRecord.json");
    private static final Path INVALID_FOOD_FILE = TEST_DATA_FOLDER.resolve("invalidFoodItemFoodRecord.json");
    private static final Path DUPLICATE_FOOD_FILE = TEST_DATA_FOLDER.resolve("duplicateFoodItemFoodRecord.json");

    @Test
    public void toModelType_typicalFoodItemsFile_success() throws Exception {
        JsonSerializableFoodRecord dataFromFile = JsonUtil.readJsonFile(TYPICAL_FOODITEMS_FILE,
                JsonSerializableFoodRecord.class).get();
        FoodRecord foodRecordFromFile = dataFromFile.toModelType();
        FoodRecord typicalPersonsFoodRecord = TypicalPersons.getTypicalAddressBook();
        assertEquals(foodRecordFromFile, typicalPersonsFoodRecord);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFoodRecord dataFromFile = JsonUtil.readJsonFile(INVALID_FOOD_FILE,
                JsonSerializableFoodRecord.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableFoodRecord dataFromFile = JsonUtil.readJsonFile(DUPLICATE_FOOD_FILE,
                JsonSerializableFoodRecord.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableFoodRecord.MESSAGE_DUPLICATE_FOOD,
                dataFromFile::toModelType);
    }

}
