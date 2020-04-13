package life.calgo.ui;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import life.calgo.commons.core.GuiSettings;
import life.calgo.commons.core.LogsCenter;
import life.calgo.logic.Logic;
import life.calgo.logic.commands.CommandResult;
import life.calgo.logic.commands.HelpCommand;
import life.calgo.logic.commands.exceptions.CommandException;
import life.calgo.logic.parser.exceptions.ParseException;
import life.calgo.model.day.DailyGoal;
import life.calgo.model.food.Food;
import life.calgo.model.food.Name;

/**
 * The Main Window. Provides the basic application layout containing.
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final String GREETING_MESSAGE_NO_GOAL = "Welcome to Calgo! Since this is your first time, "
            + "do remember to set a daily calorie goal using the goal command. Type 'help' to learn more "
            + "about our commands!";
    private static final String GREETING_MESSAGE = "Welcome back to Calgo! We're all ready to help you meet your "
            + "daily caloric goals.";
    private static final String POSITIVE_CALORIES_MESSAGE = "%s calories left for the day";
    private static final String NEGATIVE_CALORIES_MESSAGE = "Exceeded %s calories for the day";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    private ContextMenu contextMenu;

    // Independent Ui parts residing in this Ui container
    private FoodListPanel foodListPanel;
    private DailyListPanel dailyListPanel;
    private ResultDisplay resultDisplay;
    private GoalDisplay goalDisplay;
    private RemainingCaloriesDisplay remainingCaloriesDisplay;
    private HelpWindow helpWindow;
    private GraphPanel graphPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane foodListPanelPlaceholder;

    @FXML
    private StackPane dailyListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane goalDisplayPlaceholder;

    @FXML
    private StackPane caloriesDisplayPlaceholder;

    @FXML
    private StackPane graphPanelPlaceholder;

    @FXML
    private Label dailyListDate;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        foodListPanel = new FoodListPanel(logic.getFilteredFoodRecord());
        foodListPanelPlaceholder.getChildren().add(foodListPanel.getRoot());

        dailyListPanel = new DailyListPanel(logic.getFilteredDailyList());
        dailyListPanelPlaceholder.getChildren().add(dailyListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        goalDisplay = new GoalDisplay();
        goalDisplayPlaceholder.getChildren().add(goalDisplay.getRoot());

        remainingCaloriesDisplay = new RemainingCaloriesDisplay();
        caloriesDisplayPlaceholder.getChildren().add(remainingCaloriesDisplay.getRoot());

        dailyListDate.setText("Food Consumed On: " + getDate());

        if (logic.getDailyGoal().getGoal().equals(DailyGoal.DUMMY_VALUE)) {
            resultDisplay.setFeedbackToUser(GREETING_MESSAGE_NO_GOAL);
        } else {
            resultDisplay.setFeedbackToUser(GREETING_MESSAGE);
        }

        fillGoal();

        fillRemainingCalories();

        graphPanel = GraphPanel.getGraphPanelInstance();
        graphPanelPlaceholder.getChildren().add(graphPanel.getGraph(logic));

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getFoodRecordFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, this::getSuggestions);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

    }

    /**
     * Fills Goal stack pane with daily goal data.
     */
    private void fillGoal() {
        goalDisplay.setGoalOfUser(logic.getDailyGoal().toString());
    }

    /**
     * Fills remaining calories pane with number of remaining calories for the day.
     */
    public void fillRemainingCalories() {
        double remainingCalories = logic.getRemainingCalories();
        if (remainingCalories < 0.0) {
            remainingCaloriesDisplay.setCaloriesOfUser(String.format(NEGATIVE_CALORIES_MESSAGE,
                    (int) (remainingCalories * -1)));
        } else {
            remainingCaloriesDisplay.setCaloriesOfUser(String.format(POSITIVE_CALORIES_MESSAGE,
                    (int) remainingCalories));
        }
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Handles the MainWindow in event of the Help command being used.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelpHelper(String commandGuide) {
        // Check if HelpWindow content is required content
        helpWindow.setGuide(HelpCommand.getFilteredGuide());

        handleHelp();
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Gets current date of daily list.
     */
    @FXML
    public String getDate() {
        return logic.getDate().toString();
    }

    public FoodListPanel getFoodListPanel() {
        return foodListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            // update GUI components that display Model information

            // Food Record
            foodListPanel = new FoodListPanel(logic.getFilteredFoodRecord());
            // Goal Information
            fillGoal();
            fillRemainingCalories();

            if (commandResult.isShowHelp()) {
                handleHelpHelper(commandResult.getFeedbackToUser());
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        } finally {
            // Daily List date display
            dailyListDate.setText("Food Consumed On: " + getDate());
            // Graph
            graphPanelPlaceholder.getChildren().removeAll(graphPanel.getPreviousGraph());
            graphPanelPlaceholder.getChildren().add(graphPanel.getGraph(logic));
        }
    }

    /**
     * Presents similar food suggestions to user depending on their input.
     *
     */
    private void getSuggestions(String text) {
        String foodName = text.substring(text.indexOf("n/") + 2);
        if (!foodName.isEmpty()) {
            List<Food> similarFood = logic.getSimilarFood(foodName);
            String s = similarFood.stream()
                            .map(Food::getName)
                            .map(Name::toString)
                            .sorted(Comparator.naturalOrder())
                            .collect(Collectors.joining("\n"));
            if (!similarFood.isEmpty()) {
                resultDisplay.setFeedbackToUser("Here are some Food items with similar names in your Food Record: \n"
                        + s);
            } else {
                resultDisplay.setFeedbackToUser("It seems like there is no similar Food item in your Food Record");
            }
        } else {
            resultDisplay.setFeedbackToUser("");
        }
    }
}
