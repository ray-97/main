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

    @Override
    public Optional<ReadOnlyConsumptionRecord> readConsumptionRecord()
            throws DataConversionException, IOException {
        return readConsumptionRecord(filePath);
    }

    @Override
    public Optional<ReadOnlyConsumptionRecord> readConsumptionRecord(Path filePath)
            throws DataConversionException, IOException {
        requireNonNull(filePath);

        Optional<JsonSerializableConsumptionRecord> jsonConsumptionRecord = JsonUtil.readJsonFile(
                filePath, JsonSerializableConsumptionRecord.class);
        if (!jsonConsumptionRecord.isPresent()) {
            return Optional.empty();
        }

        try {
            System.out.println("checkpoint0");
            return Optional.of(jsonConsumptionRecord.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    public void saveConsumptionRecord(ReadOnlyConsumptionRecord consumptionRecord) throws IOException {
        saveConsumptionRecord(consumptionRecord, filePath);
    }

    public void saveConsumptionRecord(ReadOnlyConsumptionRecord consumptionRecord, Path filePath) throws IOException {
        requireNonNull(consumptionRecord);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableConsumptionRecord(consumptionRecord), filePath);
    }

}
