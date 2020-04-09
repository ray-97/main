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
     * Returns goal data obtained from the storage file as a {@link ReadOnlyGoal}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyGoal> readGoal() throws DataConversionException, IOException;

    /**
     * Similar to {@link #readGoal()}, but now reads from a specified file path.
     *
     * @param filePath the file path of the source file.
     * @see #getGoalFilePath()
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
     * Similar to {@link #saveGoal(ReadOnlyGoal)}, but now saves to a specified file path.
     *
     * @param filePath the path of the storage file we want to save at.
     * @see #saveGoal(ReadOnlyGoal)
     */
    void saveGoal(ReadOnlyGoal goal, Path filePath) throws IOException;

}
