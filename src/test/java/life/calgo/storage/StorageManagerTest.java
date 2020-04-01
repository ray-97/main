package life.calgo.storage;

import static life.calgo.testutil.TypicalFoodItems.getTypicalFoodRecord;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import life.calgo.commons.core.GuiSettings;
import life.calgo.model.ConsumptionRecord;
import life.calgo.model.FoodRecord;
import life.calgo.model.ReadOnlyConsumptionRecord;
import life.calgo.model.ReadOnlyFoodRecord;
import life.calgo.model.UserPrefs;
import life.calgo.testutil.ConsumptionRecordBuilder;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonFoodRecordStorage foodRecordStorage = new JsonFoodRecordStorage(getTempFilePath("fc"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonConsumptionRecordStorage consumptionRecordStorage =
                new JsonConsumptionRecordStorage(getTempFilePath("cc"));
        JsonGoalStorage goalStorage = new JsonGoalStorage(getTempFilePath("goal"));
        storageManager = new StorageManager(foodRecordStorage, consumptionRecordStorage, userPrefsStorage, goalStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void foodRecordReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonAddressBookStorageTest} class.
         */
        FoodRecord original = getTypicalFoodRecord();
        storageManager.saveFoodRecord(original);
        ReadOnlyFoodRecord retrieved = storageManager.readFoodRecord().get();
        assertEquals(original, new FoodRecord(retrieved));
    }

    @Test
    public void consumptionRecordReadSave() throws Exception {
        ConsumptionRecord original = new ConsumptionRecordBuilder().build();
        storageManager.saveConsumptionRecord(original);
        ReadOnlyConsumptionRecord retrieved = storageManager.readConsumptionRecord().get();
        assertEquals(original, new ConsumptionRecord(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getFoodRecordFilePath());
    }

}
