package life.calgo.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import life.calgo.commons.core.LogsCenter;
import life.calgo.commons.exceptions.DataConversionException;
import life.calgo.model.ReadOnlyConsumptionRecord;
import life.calgo.model.ReadOnlyFoodRecord;
import life.calgo.model.ReadOnlyGoal;
import life.calgo.model.ReadOnlyUserPrefs;
import life.calgo.model.UserPrefs;

/**
 * Manages storage of FoodRecord data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private FoodRecordStorage foodRecordStorage;
    private ConsumptionRecordStorage consumptionRecordStorage;
    private UserPrefsStorage userPrefsStorage;
    private GoalStorage goalStorage;

    public StorageManager(FoodRecordStorage foodRecordStorage, ConsumptionRecordStorage consumptionRecordStorage,
                          UserPrefsStorage userPrefsStorage, GoalStorage goalStorage) {
        super();
        this.foodRecordStorage = foodRecordStorage;
        this.consumptionRecordStorage = consumptionRecordStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.goalStorage = goalStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ FoodRecord methods ==============================

    @Override
    public Path getFoodRecordFilePath() {
        return foodRecordStorage.getFoodRecordFilePath();
    }

    @Override
    public Optional<ReadOnlyFoodRecord> readFoodRecord() throws DataConversionException, IOException {
        return readFoodRecord(foodRecordStorage.getFoodRecordFilePath());
    }

    @Override
    public Optional<ReadOnlyFoodRecord> readFoodRecord(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return foodRecordStorage.readFoodRecord(filePath);
    }

    @Override
    public void saveFoodRecord(ReadOnlyFoodRecord foodRecord) throws IOException {
        saveFoodRecord(foodRecord, foodRecordStorage.getFoodRecordFilePath());
    }

    @Override
    public void saveFoodRecord(ReadOnlyFoodRecord foodRecord, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        foodRecordStorage.saveFoodRecord(foodRecord, filePath);
    }

    // ============= ConsumptionRecord methods =======================================

    @Override
    public Path getConsumptionRecordFilePath() {
        return consumptionRecordStorage.getConsumptionRecordFilePath();
    }

    @Override
    public Optional<ReadOnlyConsumptionRecord> readConsumptionRecord() throws DataConversionException, IOException {
        return readConsumptionRecord(consumptionRecordStorage.getConsumptionRecordFilePath());
    }

    @Override
    public Optional<ReadOnlyConsumptionRecord> readConsumptionRecord(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read from file: " + filePath);
        return consumptionRecordStorage.readConsumptionRecord(filePath);
    }

    @Override
    public void saveConsumptionRecord(ReadOnlyConsumptionRecord consumptionRecord) throws IOException {
        saveConsumptionRecord(consumptionRecord, consumptionRecordStorage.getConsumptionRecordFilePath());
    }

    @Override
    public void saveConsumptionRecord(ReadOnlyConsumptionRecord consumptionRecord, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        consumptionRecordStorage.saveConsumptionRecord(consumptionRecord, filePath);
    }

    // ============= Goal methods =======================================

    @Override
    public Path getGoalFilePath() {
        return goalStorage.getGoalFilePath();
    }

    @Override
    public Optional<ReadOnlyGoal> readGoal() throws DataConversionException, IOException {
        return readGoal(goalStorage.getGoalFilePath());
    }

    @Override
    public Optional<ReadOnlyGoal> readGoal(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read from file: " + filePath);
        return goalStorage.readGoal(filePath);
    }

    @Override
    public void saveGoal(ReadOnlyGoal goal) throws IOException {
        saveGoal(goal, goalStorage.getGoalFilePath());
    }

    @Override
    public void saveGoal(ReadOnlyGoal goal, Path filePath) throws IOException {
        logger.fine("Attempting to write data to file: " + filePath);
        goalStorage.saveGoal(goal, filePath);
    }

}
