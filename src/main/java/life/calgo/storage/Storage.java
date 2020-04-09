package life.calgo.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import life.calgo.commons.exceptions.DataConversionException;
import life.calgo.model.ReadOnlyConsumptionRecord;
import life.calgo.model.ReadOnlyFoodRecord;
import life.calgo.model.ReadOnlyGoal;
import life.calgo.model.ReadOnlyUserPrefs;
import life.calgo.model.UserPrefs;

/**
 * API of the Storage component.
 */
public interface Storage extends UserPrefsStorage, FoodRecordStorage, ConsumptionRecordStorage, GoalStorage {

    // UserPrefs-related methods

    /**
     * Gets the json user preferences file path.
     *
     * @return The Path for the UserPrefs json storage file.
     */
    @Override
    public Path getUserPrefsFilePath();

    /**
     * Returns a UserPrefs object wrapped in an Optional after reading the json user preferences file.
     *
     * @return An empty Optional if no such file exists, or the derived UserPrefs object wrapped in an Optional.
     * @throws DataConversionException If the file format is not as expected.
     * @throws IOException If there was any problem when reading from the file.
     */
    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    /**
     * Saves the ReadOnlyUserPrefs in a json file.
     *
     * @param userPrefs The user preferences to save, which cannot be null.
     * @throws IOException If there was any problem when writing to the file.
     */
    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    // FoodRecord-related methods

    /**
     * Gets the json FoodRecord file path.
     *
     * @return The Path for the FoodRecord json storage file.
     */
    @Override
    Path getFoodRecordFilePath();

    /**
     * Returns a ReadOnlyFoodRecord object wrapped in an Optional after reading the json FoodRecord file.
     *
     * @return An empty Optional if no such file exists, or the derived ReadOnlyFoodRecord object in an Optional.
     * @throws DataConversionException If the file format is not as expected.
     * @throws IOException If there was any problem when reading from the file.
     */
    @Override
    Optional<ReadOnlyFoodRecord> readFoodRecord() throws DataConversionException, IOException;

    /**
     * Saves the ReadOnlyFoodRecord in a json file.
     *
     * @param foodRecord The ReadOnlyFoodRecord to be saved which cannot be null.
     * @throws IOException If there was any problem when writing to the file.
     */
    @Override
    void saveFoodRecord(ReadOnlyFoodRecord foodRecord) throws IOException;

    // ConsumptionRecord-related methods

    /**
     * Gets the json ConsumptionRecord file path.
     *
     * @return The Path for the ConsumptionRecord json storage file.
     */
    @Override
    public Path getConsumptionRecordFilePath();

    /**
     * Returns a ReadOnlyConsumptionRecord object wrapped in an Optional after reading the json ConsumptionRecord file.
     *
     * @return An empty Optional if no such file exists, or the derived ReadOnlyConsumptionRecord inside an Optional.
     * @throws DataConversionException If the file format is not as expected.
     * @throws IOException If there was any problem when reading from the file.
     */
    @Override
    public Optional<ReadOnlyConsumptionRecord> readConsumptionRecord() throws DataConversionException, IOException;

    /**
     * Saves the ReadOnlyConsumptionRecord in a json file.
     *
     * @param consumptionRecord The ReadOnlyConsumptionRecord to be saved which cannot be null.
     * @throws IOException Tf there was any problem when writing to the file.
     */
    @Override
    public void saveConsumptionRecord(ReadOnlyConsumptionRecord consumptionRecord) throws IOException;

    // Goal-related methods

    /**
     * Gets the json goal file path.
     *
     * @return The Path for the goal json storage file.
     */
    @Override
    public Path getGoalFilePath();

    /**
     * Returns a ReadOnlyGoal object wrapped in an Optional after reading the json goal file.
     *
     * @return An empty Optional if no such file exists, or the derived ReadOnlyGoal inside an Optional.
     * @throws DataConversionException If the file format is not as expected.
     * @throws IOException If there was any problem when reading from the file.
     */
    @Override
    public Optional<ReadOnlyGoal> readGoal() throws DataConversionException, IOException;

    /**
     * Saves the ReadOnlyGoal in a json file.
     *
     * @param goal The ReadOnlyGoal to be saved which cannot be null.
     * @throws IOException If there was any problem when writing to the file.
     */
    @Override
    public void saveGoal(ReadOnlyGoal goal) throws IOException;


}
