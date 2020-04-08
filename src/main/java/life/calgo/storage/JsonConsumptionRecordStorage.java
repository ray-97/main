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
import life.calgo.model.ReadOnlyConsumptionRecord;

/**
 * A class to access ConsumptionRecord data stored as a json file on the hard disk.
 */
public class JsonConsumptionRecordStorage implements ConsumptionRecordStorage {

    private static final Logger logger = LogsCenter.getLogger(ConsumptionRecordStorage.class);

    private Path filePath;

    public JsonConsumptionRecordStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getConsumptionRecordFilePath() {
        return filePath;
    }

    /**
     * Returns a ReadOnlyConsumptionRecord wrapped in an Optional after reading from a json file.
     *
     * @return A {@code ReadOnlyConsumptionRecord} object that was read from file path,
     * wrapped within an {@code Optional}.
     * @throws DataConversionException If the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyConsumptionRecord> readConsumptionRecord()
            throws DataConversionException {
        return readConsumptionRecord(filePath);
    }

    /**
     * Similar to {@link #readConsumptionRecord()}, but now reads from an specified file path.
     *
     * @param filePath Location of the data. Cannot be null.
     */
    @Override
    public Optional<ReadOnlyConsumptionRecord> readConsumptionRecord(Path filePath)
            throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableConsumptionRecord> jsonConsumptionRecord = JsonUtil.readJsonFile(
                filePath, JsonSerializableConsumptionRecord.class);
        if (jsonConsumptionRecord.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonConsumptionRecord.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    /**
     * Writes the ConsumptionRecord to the pre-specified file path.
     *
     * @param consumptionRecord The consumptionRecord to be saved, which cannot be null.
     * @throws IOException If there's any error when writing to the file.
     */
    public void saveConsumptionRecord(ReadOnlyConsumptionRecord consumptionRecord) throws IOException {
        saveConsumptionRecord(consumptionRecord, filePath);
    }

    /**

    /**
     * Similar to {@link #saveConsumptionRecord(ReadOnlyConsumptionRecord)}, but now saves to a specified file path.
     *
     * @param filePath Location of the data. Cannot be null.
     */
    public void saveConsumptionRecord(ReadOnlyConsumptionRecord consumptionRecord, Path filePath) throws IOException {
        requireNonNull(consumptionRecord);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableConsumptionRecord(consumptionRecord), filePath);
    }

}
