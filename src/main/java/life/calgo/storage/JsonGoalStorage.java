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
import life.calgo.model.ReadOnlyGoal;

/**
 * A class to access Daily Goal data stored as a json file on the hard disk.
 */
public class JsonGoalStorage implements GoalStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonGoalStorage.class);

    private Path filePath;

    public JsonGoalStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getGoalFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyGoal> readGoal() throws DataConversionException {
        return readGoal(filePath);
    }

    /**
     * Similar to {@link #readGoal()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyGoal> readGoal(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableGoal> jsonGoal = JsonUtil.readJsonFile(
                filePath, JsonSerializableGoal.class);
        if (!jsonGoal.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonGoal.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveGoal(ReadOnlyGoal goal) throws IOException {
        saveGoal(goal, filePath);
    }

    /**
     * Similar to {@link #saveGoal(ReadOnlyGoal)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveGoal(ReadOnlyGoal goal, Path filePath) throws IOException {
        requireNonNull(goal);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableGoal(goal), filePath);
    }
}
