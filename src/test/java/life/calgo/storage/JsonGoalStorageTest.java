package life.calgo.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import life.calgo.commons.exceptions.DataConversionException;
import life.calgo.model.ReadOnlyGoal;
import life.calgo.model.day.DailyGoal;
import life.calgo.testutil.Assert;

public class JsonGoalStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonGoalStorageTest");

    @TempDir
    public Path testFolder;


    // Tests for Reading

    // test if JsonGoalStorage reads from null file path.
    @Test
    public void readGoal_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readGoal(null));
    }

    // test if JsonGoalStorage reads from non existent file.
    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readGoal("NonExistentFile.json").isPresent());
    }

    // test if JsonGoalStorage reads from an incorrect json format file.
    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataConversionException.class, () -> readGoal("notJsonFormatGoal.json"));
    }

    // test if JsonGoalStorage reads from an invalid json file.
    @Test
    public void readGoal_invalidGoal_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readGoal("invalidGoal.json"));
    }

    @Test
    public void readAndSaveGoal_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempGoal.json");
        DailyGoal original = new DailyGoal(DailyGoal.MINIMUM_HEALTHY_CALORIES);
        JsonGoalStorage jsonGoalStorage = new JsonGoalStorage(filePath);

        // Save in new file and read back without any loss or change of data
        jsonGoalStorage.saveGoal(original, filePath);
        ReadOnlyGoal readBack = jsonGoalStorage.readGoal(filePath).get();
        assertEquals(original, new DailyGoal(readBack));

        // Modify data, overwrite exiting file, and read back
        original = original.updateDailyGoal(DailyGoal.MAXIMUM_ACCEPTABLE_CALORIES);
        jsonGoalStorage.saveGoal(original, filePath);
        readBack = jsonGoalStorage.readGoal(filePath).get();
        assertEquals(original, new DailyGoal(readBack));

        // Save and read without specifying file path
        original = original.updateDailyGoal(DailyGoal.MINIMUM_ACCEPTABLE_CALORIES);
        jsonGoalStorage.saveGoal(original); // file path not specified
        readBack = jsonGoalStorage.readGoal().get(); // file path not specified
        assertEquals(original, new DailyGoal(readBack));

    }

    // Tests for Saving

    // test if JsonGoalStorage will save a Null Daily Goal.
    @Test
    public void saveDailyGoal_nullDailyGoal_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveGoal(null, "AnyFile.json"));
    }

    // test if JsonGoalStorage will save a valid Goal at a null file path.
    @Test
    public void saveDailyGoal_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveGoal(new DailyGoal(), null));
    }

    // Utility Methods
    private java.util.Optional<ReadOnlyGoal> readGoal(String filePath) throws Exception {
        return new JsonGoalStorage(Paths.get(filePath)).readGoal(addToTestDataPathIfNotNull(filePath));
    }


    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    /**
     * Saves {@code dailyGoal} at the specified {@code filePath}
     */
    private void saveGoal(DailyGoal dailyGoal, String filePath) {
        try {
            new JsonGoalStorage(Paths.get(filePath))
                    .saveGoal(dailyGoal, addToTestDataPathIfNotNull(filePath));
        } catch (IOException e) {
            throw new AssertionError("There should not be an error writing to the goal file", e);
        }
    }

}
