package f11_1.calgo.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static f11_1.calgo.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import f11_1.calgo.testutil.Assert;
import f11_1.calgo.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import f11_1.calgo.commons.exceptions.DataConversionException;
import f11_1.calgo.model.FoodRecord;
import f11_1.calgo.model.ReadOnlyFoodRecord;

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
        Assert.assertThrows(DataConversionException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readAddressBook("invalidPersonAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readAddressBook("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        FoodRecord original = TypicalPersons.getTypicalAddressBook();
        JsonFoodRecordStorage jsonAddressBookStorage = new JsonFoodRecordStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveFoodRecord(original, filePath);
        ReadOnlyFoodRecord readBack = jsonAddressBookStorage.readFoodRecord(filePath).get();
        assertEquals(original, new FoodRecord(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addFood(TypicalPersons.HOON);
        original.removeFood(TypicalPersons.ALICE);
        jsonAddressBookStorage.saveFoodRecord(original, filePath);
        readBack = jsonAddressBookStorage.readFoodRecord(filePath).get();
        assertEquals(original, new FoodRecord(readBack));

        // Save and read without specifying file path
        original.addFood(TypicalPersons.IDA);
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
