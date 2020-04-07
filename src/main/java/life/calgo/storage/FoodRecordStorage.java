package life.calgo.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import life.calgo.commons.exceptions.DataConversionException;
import life.calgo.model.FoodRecord;
import life.calgo.model.ReadOnlyFoodRecord;

/**
 * Represents a storage for {@link FoodRecord}.
 */
public interface FoodRecordStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getFoodRecordFilePath();

    /**
     * Returns FoodRecord data obtained from the storage file as a {@link ReadOnlyFoodRecord}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyFoodRecord> readFoodRecord() throws DataConversionException, IOException;

    /**
     * Returns a ReadOnlyFoodRecord generated from a storage file specified by the filePath.
     *
     * @param filePath the file path of the source file.
     * @return the resultant ReadOnlyFoodRecord obtained from the source file.
     * @throws DataConversionException if the data in the file is not in the expected format.
     * @throws IOException if there was any problem when reading from the file.
     * @see #getFoodRecordFilePath()
     */
    Optional<ReadOnlyFoodRecord> readFoodRecord(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyFoodRecord} to the storage.
     *
     * @param foodRecord the ReadOnlyFoodRecord to be saved which cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFoodRecord(ReadOnlyFoodRecord foodRecord) throws IOException;

    /**
     * Saves the given {@link ReadOnlyFoodRecord} to the storage at a specified Path.
     *
     * @param foodRecord the ReadOnlyFoodRecord to be saved.
     * @param filePath the path of the storage file we want to save at.
     * @throws IOException if there was any problem writing to the file.
     * @see #saveFoodRecord(ReadOnlyFoodRecord)
     */
    void saveFoodRecord(ReadOnlyFoodRecord foodRecord, Path filePath) throws IOException;

}
