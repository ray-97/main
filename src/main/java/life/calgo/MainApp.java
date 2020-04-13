package life.calgo;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import life.calgo.commons.core.Config;
import life.calgo.commons.core.LogsCenter;
import life.calgo.commons.core.Version;
import life.calgo.commons.exceptions.DataConversionException;
import life.calgo.commons.util.ConfigUtil;
import life.calgo.commons.util.StringUtil;
import life.calgo.logic.Logic;
import life.calgo.logic.LogicManager;

import life.calgo.model.ConsumptionRecord;
import life.calgo.model.FoodRecord;
import life.calgo.model.Model;
import life.calgo.model.ModelManager;
import life.calgo.model.ReadOnlyConsumptionRecord;
import life.calgo.model.ReadOnlyFoodRecord;
import life.calgo.model.ReadOnlyGoal;
import life.calgo.model.ReadOnlyUserPrefs;
import life.calgo.model.UserPrefs;
import life.calgo.model.day.DailyGoal;
import life.calgo.model.util.SampleDataUtil;

import life.calgo.storage.ConsumptionRecordStorage;
import life.calgo.storage.FoodRecordStorage;
import life.calgo.storage.GoalStorage;
import life.calgo.storage.JsonConsumptionRecordStorage;
import life.calgo.storage.JsonFoodRecordStorage;
import life.calgo.storage.JsonGoalStorage;
import life.calgo.storage.JsonUserPrefsStorage;
import life.calgo.storage.Storage;
import life.calgo.storage.StorageManager;
import life.calgo.storage.UserPrefsStorage;
import life.calgo.ui.Ui;
import life.calgo.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Calgo ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        FoodRecordStorage foodRecordStorage = new JsonFoodRecordStorage(userPrefs.getFoodRecordFilePath());
        ConsumptionRecordStorage consumptionRecordStorage = new JsonConsumptionRecordStorage(
                userPrefs.getConsumptionRecordFilePath());
        GoalStorage goalStorage = new JsonGoalStorage(userPrefs.getGoalFilePath());
        storage = new StorageManager(foodRecordStorage, consumptionRecordStorage, userPrefsStorage, goalStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s food record and {@code userPrefs}. <br>
     * The data from the sample food record will be used instead if {@code storage}'s food record is not found,
     * or an empty food record will be used instead if errors occur when reading {@code storage}'s food record.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyFoodRecord> foodRecordOptional;
        ReadOnlyFoodRecord initialData;
        Optional<ReadOnlyConsumptionRecord> consumptionRecordOptional;
        ReadOnlyConsumptionRecord consumptionData;
        Optional<ReadOnlyGoal> goalOptional;
        ReadOnlyGoal goal;

        try {
            foodRecordOptional = storage.readFoodRecord();
            if (!foodRecordOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample FoodRecord");
            }
            initialData = foodRecordOptional.orElseGet(SampleDataUtil::getSampleFoodRecord);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty FoodRecord");
            initialData = new FoodRecord();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty FoodRecord");
            initialData = new FoodRecord();
        }

        try {
            consumptionRecordOptional = storage.readConsumptionRecord();
            if (!consumptionRecordOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with no consumption data.");
            }
            consumptionData = consumptionRecordOptional.orElse(new ConsumptionRecord());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with no consumption data.");
            consumptionData = new ConsumptionRecord();
        } catch (IOException e) {
            logger.warning("Problem while reading from file. Will be starting with no consumption data.");
            consumptionData = new ConsumptionRecord();
        }

        try {
            goalOptional = storage.readGoal();
            if (!goalOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with no goal set.");
            }
            goal = goalOptional.orElse(new DailyGoal());
        } catch (IllegalArgumentException e) {
            logger.warning("Current value of goal is not admissible. Will be starting with no goal set.");
            goal = new DailyGoal();
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format or current goal is not an acceptable value."
                    + " Will be starting with no goal set.");
            goal = new DailyGoal();
        } catch (IOException e) {
            logger.warning("Problem while reading from the goal file. Will be starting with no goal set.");
            goal = new DailyGoal();
        }
        return new ModelManager(initialData, consumptionData, userPrefs, goal);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty FoodRecord");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting Calgo " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Calgo ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
