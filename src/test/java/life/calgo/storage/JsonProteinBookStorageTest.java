package life.calgo.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static life.calgo.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import life.calgo.testutil.Assert;
import life.calgo.testutil.TypicalFoodItems;
import life.calgo.testutil.TypicalFoodItems;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import life.calgo.commons.exceptions.DataConversionException;
import life.calgo.model.FoodRecord;
import life.calgo.model.ReadOnlyFoodRecord;

public class JsonProteinBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyFoodRecord> readAddressBook(String filePath) throws Exception {
        return new JsonFoodRecordStorage(Paths.get(filePath)).readFoodRecord(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataConversionException.class, () -> readAddressBook("notJsonFormatFoodRecord.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readAddressBook("invalidFoodItemFoodRecord.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readAddressBook("invalidAndValidFoodItemFoodRecord.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        FoodRecord original = TypicalFoodItems.getTypicalFoodRecord();
        JsonFoodRecordStorage jsonAddressBookStorage = new JsonFoodRecordStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveFoodRecord(original, filePath);
        ReadOnlyFoodRecord readBack = jsonAddressBookStorage.readFoodRecord(filePath).get();
        assertEquals(original, new FoodRecord(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addFood(TypicalFoodItems.FISH_AND_CHIPS);
        original.removeFood(TypicalFoodItems.DUCK_RICE);
        jsonAddressBookStorage.saveFoodRecord(original, filePath);
        readBack = jsonAddressBookStorage.readFoodRecord(filePath).get();
        assertEquals(original, new FoodRecord(readBack));

        // Save and read without specifying file path
        original.addFood(TypicalFoodItems.FISH_AND_CHIPS);
        jsonAddressBookStorage.saveFoodRecord(original); // file path not specified
        readBack = jsonAddressBookStorage.readFoodRecord().get(); // file path not specified
        assertEquals(original, new FoodRecord(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyFoodRecord addressBook, String filePath) {
        try {
            new JsonFoodRecordStorage(Paths.get(filePath))
                    .saveFoodRecord(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveAddressBook(new FoodRecord(), null));
    }
}
