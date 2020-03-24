package life.calgo.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import life.calgo.commons.exceptions.DataConversionException;
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
     * Returns goal data as a {@link ReadOnlyGoal}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyGoal> readGoal() throws DataConversionException, IOException;

    /**
     * @see #getGoalFilePath()
     */
    Optional<ReadOnlyGoal> readGoal(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyGoal} to the storage.
     * @param goal cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveGoal(ReadOnlyGoal goal) throws IOException;

    /**
     * @see #saveGoal(ReadOnlyGoal)
     */
    void saveGoal(ReadOnlyGoal goal, Path filePath) throws IOException;
}
