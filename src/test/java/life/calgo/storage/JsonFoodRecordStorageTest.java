package life.calgo.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.io.TempDir;

import life.calgo.commons.exceptions.DataConversionException;
import life.calgo.model.FoodRecord;
import life.calgo.model.ReadOnlyFoodRecord;

import life.calgo.testutil.Assert;
import life.calgo.testutil.TypicalFoodItems;



public class JsonFoodRecordStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonFoodRecordStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readFoodRecord_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readFoodRecord(null));
    }

    private java.util.Optional<ReadOnlyFoodRecord> readFoodRecord(String filePath) throws Exception {
        return new JsonFoodRecordStorage(Paths.get(filePath)).readFoodRecord(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readFoodRecord("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataConversionException.class, () -> readFoodRecord("notJsonFormatFoodRecord.json"));
    }

    @Test
    public void readFoodRecord_invalidFoodFoodRecord_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readFoodRecord("invalidFoodItemFoodRecord.json"));
    }

    @Test
    public void readFoodRecord_invalidAndValidFoodFoodRecord_throwDataConversionException() {
        Assert.assertThrows(
                DataConversionException.class, () -> readFoodRecord("invalidAndValidFoodItemFoodRecord.json"));
    }

    @Test
    public void readAndSaveFoodRecord_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempFoodRecord.json");
        FoodRecord original = TypicalFoodItems.getTypicalFoodRecord();
        JsonFoodRecordStorage jsonFoodRecordStorage = new JsonFoodRecordStorage(filePath);

        // Save in new file and read back
        jsonFoodRecordStorage.saveFoodRecord(original, filePath);
        ReadOnlyFoodRecord readBack = jsonFoodRecordStorage.readFoodRecord(filePath).get();
        assertEquals(original, new FoodRecord(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addFood(TypicalFoodItems.ZINGER_BURGER);
        original.removeFood(TypicalFoodItems.DUCK_RICE);
        jsonFoodRecordStorage.saveFoodRecord(original, filePath);
        readBack = jsonFoodRecordStorage.readFoodRecord(filePath).get();
        assertEquals(original, new FoodRecord(readBack));

        // Save and read without specifying file path
        original.addFood(TypicalFoodItems.YELLOW_SAUCE);
        jsonFoodRecordStorage.saveFoodRecord(original); // file path not specified
        readBack = jsonFoodRecordStorage.readFoodRecord().get(); // file path not specified
        assertEquals(original, new FoodRecord(readBack));

    }

    @Test
    public void saveFoodRecord_nullFoodRecord_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveFoodRecord(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveFoodRecord(ReadOnlyFoodRecord foodRecord, String filePath) {
        try {
            new JsonFoodRecordStorage(Paths.get(filePath))
                    .saveFoodRecord(foodRecord, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveFoodRecord_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveFoodRecord(new FoodRecord(), null));
    }
}
