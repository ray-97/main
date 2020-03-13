package f11_1.calgo.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import f11_1.calgo.commons.exceptions.DataConversionException;
import f11_1.calgo.model.FoodRecord;
import f11_1.calgo.model.ReadOnlyFoodRecord;

/**
 * Represents a storage for {@link FoodRecord}.
 */
public interface FoodRecordStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getFoodRecordFilePath();

    /**
     * Returns FoodRecord data as a {@link ReadOnlyFoodRecord}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyFoodRecord> readFoodRecord() throws DataConversionException, IOException;

    /**
     * @see #getFoodRecordFilePath()
     */
    Optional<ReadOnlyFoodRecord> readFoodRecord(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyFoodRecord} to the storage.
     * @param foodRecord cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFoodRecord(ReadOnlyFoodRecord foodRecord) throws IOException;

    /**
     * @see #saveFoodRecord(ReadOnlyFoodRecord)
     */
    void saveFoodRecord(ReadOnlyFoodRecord addressBook, Path filePath) throws IOException;

}
