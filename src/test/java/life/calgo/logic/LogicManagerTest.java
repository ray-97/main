package life.calgo.logic;

import static life.calgo.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static life.calgo.logic.commands.CommandTestUtil.CALORIE_DESC_APPLE;
import static life.calgo.logic.commands.CommandTestUtil.CARBOHYDRATE_DESC_APPLE;
import static life.calgo.logic.commands.CommandTestUtil.FAT_DESC_APPLE;
import static life.calgo.logic.commands.CommandTestUtil.NAME_DESC_APPLE;
import static life.calgo.logic.commands.CommandTestUtil.PROTEIN_DESC_APPLE;

import static life.calgo.testutil.Assert.assertThrows;
import static life.calgo.testutil.TypicalFoodItems.APPLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import life.calgo.logic.commands.CommandResult;
import life.calgo.logic.commands.ListCommand;
import life.calgo.logic.commands.UpdateCommand;
import life.calgo.logic.commands.exceptions.CommandException;
import life.calgo.logic.parser.exceptions.ParseException;
import life.calgo.model.ConsumptionRecord;
import life.calgo.model.Model;
import life.calgo.model.ModelManager;
import life.calgo.model.ReadOnlyFoodRecord;
import life.calgo.model.UserPrefs;
import life.calgo.model.day.DailyGoal;
import life.calgo.model.food.Food;
import life.calgo.storage.JsonConsumptionRecordStorage;
import life.calgo.storage.JsonFoodRecordStorage;
import life.calgo.storage.JsonGoalStorage;
import life.calgo.storage.JsonUserPrefsStorage;
import life.calgo.storage.StorageManager;
import life.calgo.testutil.FoodBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonFoodRecordStorage foodRecordStorage =
                new JsonFoodRecordStorage(temporaryFolder.resolve("foodrecord.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonConsumptionRecordStorage consumptionRecordStorage =
                new JsonConsumptionRecordStorage(temporaryFolder.resolve("consumptionrecord.json"));
        JsonGoalStorage goalStorage = new JsonGoalStorage(temporaryFolder.resolve("invalidGoal.json"));

        StorageManager storage = new StorageManager(foodRecordStorage, consumptionRecordStorage,
                userPrefsStorage, goalStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }


    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonFoodRecordStorage addressBookStorage =
                new JsonFoodRecordIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        JsonConsumptionRecordStorage consumptionRecordStorage =
                new JsonConsumptionRecordStorage(temporaryFolder.resolve("ioExceptionConsumptionRecord.json"));
        JsonGoalStorage goalStorage = new JsonGoalStorage(temporaryFolder.resolve("ioExceptionGoal.json"));
        StorageManager storage = new StorageManager(addressBookStorage, consumptionRecordStorage,
                userPrefsStorage, goalStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String updateCommand = UpdateCommand.COMMAND_WORD + NAME_DESC_APPLE + CALORIE_DESC_APPLE + PROTEIN_DESC_APPLE
                + CARBOHYDRATE_DESC_APPLE + FAT_DESC_APPLE;
        Food expectedFood = new FoodBuilder(APPLE).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addFood(expectedFood);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(updateCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredFoodRecord().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getFoodRecord(), new ConsumptionRecord(),
                new UserPrefs(), new DailyGoal());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonFoodRecordIoExceptionThrowingStub extends JsonFoodRecordStorage {
        private JsonFoodRecordIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveFoodRecord(ReadOnlyFoodRecord addressBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
