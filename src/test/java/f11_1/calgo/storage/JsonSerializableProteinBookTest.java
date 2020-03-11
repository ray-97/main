package f11_1.calgo.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static f11_1.calgo.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import f11_1.calgo.commons.exceptions.IllegalValueException;
import f11_1.calgo.commons.util.JsonUtil;
import f11_1.calgo.model.FoodRecord;
import f11_1.calgo.testutil.TypicalPersons;

public class JsonSerializableProteinBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableFoodRecord dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableFoodRecord.class).get();
        FoodRecord foodRecordFromFile = dataFromFile.toModelType();
        FoodRecord typicalPersonsFoodRecord = TypicalPersons.getTypicalAddressBook();
        assertEquals(foodRecordFromFile, typicalPersonsFoodRecord);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFoodRecord dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableFoodRecord.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableFoodRecord dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableFoodRecord.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableFoodRecord.MESSAGE_DUPLICATE_FOOD,
                dataFromFile::toModelType);
    }

}
