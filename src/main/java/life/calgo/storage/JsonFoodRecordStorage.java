package life.calgo.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import life.calgo.commons.core.LogsCenter;
import life.calgo.commons.exceptions.DataConversionException;
import life.calgo.commons.exceptions.IllegalValueException;
import life.calgo.commons.util.FileUtil;
import life.calgo.commons.util.JsonUtil;
import life.calgo.model.ReadOnlyFoodRecord;

/**
 * A class to access/write FoodRecord data stored as a json file on the hard disk.
 * JsonSerializableFoodRecord is used as a middle-man between the Model and the disk.
 */
public class JsonFoodRecordStorage implements FoodRecordStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonFoodRecordStorage.class);

    private Path filePath;

    public JsonFoodRecordStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getFoodRecordFilePath() {
        return filePath;
    }

    /**
     * Returns a ReadOnlyFoodRecord obtained from a file storage specified by the current file path.
     *
     * @return the derived ReadOnlyFoodRecord obtained from the relevant file storage.
     * @throws DataConversionException if the data in the file is not in the expected format.
     */
    @Override
    public Optional<ReadOnlyFoodRecord> readFoodRecord() throws DataConversionException {
        return readFoodRecord(filePath);
    }

    /**
     * Similar to {@link #readFoodRecord()}, but now reads from an explicitly specified file path.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyFoodRecord> readFoodRecord(Path filePath) throws DataConversionException {

        requireNonNull(filePath);

        Optional<JsonSerializableFoodRecord> jsonFoodRecord = JsonUtil.readJsonFile(
                filePath, JsonSerializableFoodRecord.class);

        if (!jsonFoodRecord.isPresent()) {
            return Optional.empty();
        }

        try {

            return Optional.of(jsonFoodRecord.get().toModelType());

        } catch (IllegalValueException ive) {

            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);

        }
    }

    /**
     * Saves the current FoodRecord, represented by a ReadOnlyFoodRecord, into a json storage file.
     *
     * @param foodRecord the ReadOnlyFoodRecord to be saved which cannot be null.
     * @throws IOException if there was any problem when writing to the file.
     */
    @Override
    public void saveFoodRecord(ReadOnlyFoodRecord foodRecord) throws IOException {
        saveFoodRecord(foodRecord, filePath);
    }

    /**
     * Similar to {@link #saveFoodRecord(ReadOnlyFoodRecord)}, but saves to a specified file at the specified path.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveFoodRecord(ReadOnlyFoodRecord foodRecord, Path filePath) throws IOException {
        requireNonNull(foodRecord);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableFoodRecord(foodRecord), filePath);
    }
}
