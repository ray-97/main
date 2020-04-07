package life.calgo.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import life.calgo.commons.exceptions.DataConversionException;
import life.calgo.model.ReadOnlyFoodRecord;
import life.calgo.model.ReadOnlyGoal;
import life.calgo.model.day.DailyGoal;

/**
 * Represents a storage for {@link DailyGoal}.
 */
public interface GoalStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getGoalFilePath();

    /**
     * Returns goal data obtained from the storage file as a {@link ReadOnlyGoal}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyGoal> readGoal() throws DataConversionException, IOException;

    /**
     * Returns a ReadOnlyGoal generated from a storage file specified by the filePath.
     * @see #getGoalFilePath()
     *
     * @param filePath the file path of the source file.
     * @return the resultant ReadOnlyGoal obtained from the source file.
     * @throws DataConversionException if the data in the file is not in the expected format.
     * @throws IOException if there was any problem when reading from the file.
     */
    Optional<ReadOnlyGoal> readGoal(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyGoal} to the storage.
     *
     * @param goal the ReadOnlyGoal to be saved which cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveGoal(ReadOnlyGoal goal) throws IOException;

    /**
     * Saves the given {@link ReadOnlyGoal} to the storage at a specified Path.
     * @see #saveGoal(ReadOnlyGoal)
     *
     * @param goal the ReadOnlyGoal to be saved.
     * @param filePath the path of the storage file we want to save at.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveGoal(ReadOnlyGoal goal, Path filePath) throws IOException;

}
