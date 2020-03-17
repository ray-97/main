package life.calgo.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import life.calgo.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String HELP_UPDATE_MESSAGE = "update:\n"
            + "Updates a Food Item in the Food Record, or creates a new Food Item if one isn't already present.\n"
            + "Format: update n/NAME cal/CALORIES [p/PROTEINS] [c/CARBS] [f/FATS]\n\n";
    public static final String HELP_DELETE_MESSAGE = "delete:\n"
            + "Deletes the specified Food Item from the Food Record.\n"
            + "Format: delete n/NAME\n\n";
    public static final String HELP_LIST_MESSAGE = "list:\n"
            + "Shows a list of all Food items in the Food Records,\n"
            + "with their respective nutritional values of calories, proteins, carbohydrates, and fats.\n"
            + "Format: list\n\n";
    public static final String HELP_STOMACH_MESSAGE = "stomach: \n"
            + "Displays a list of food items along side how many portions have been consumed on that day.\n"
            + "For a more detailed statistical report with nutritional values of food consumed, see report command.\n"
            + "Format: stomach [d/DATE]\n\n";
    public static final String HELP_NOM_MESSAGE = "nom:\n"
            + "Adds a food item into the log which keeps track of what the user has eaten on that day.\n"
            + "Format: nom [n/NAME] [d/DATE] [portion/PORTION]\n\n";
    public static final String HELP_VOMIT_MESSAGE = "vomit:\n"
            + "Deletes a food item that a user has previously added to the log tracking consumption on that day.\n"
            + "Format: vomit [num/INDEX_OF_FOOD] [d/DATE] [portion/PORTION]\n\n";
    public static final String HELP_EXPORT_MESSAGE = "export:\n"
            + "Saves a human-readable text file (FoodRecords.txt) in the target folder\n"
            + "(default: same folder as the Calgo application), containing all Food item records including\n"
            + "All Calgo data is also automatically saved after each command.\n"
            + "Format: export [location/LOCATION]\n\n";
    public static final String HELP_FIND_MESSAGE = "find:\n"
            + "Finds all Food items whose names contain any of the keyword(s)\n"
            + "Alternatively, Food items can also be found by entering a specific value of its attributes,\n"
            + "i.e. Calories, Proteins, Carbohydrates, or Fats.\n"
            + "Format: find [cal/CALORIES] [p/PROTEINS] [c/CARBS] [f/FATS]\n"
            + "Alt format: find KEYWORD [MORE_KEYWORDS]\n\n";
    public static final String HELP_GOAL_MESSAGE = "goal:\n"
            + "Sets a numerical goal for the desired number of calories to be consumed in a day.\n"
            + "This goal will be used to provide helpful insights for users in the Report.\n"
            + "Format: goal [g/GOAL]\n\n";
    public static final String HELP_REPORT_MESSAGE = "report:\n"
            + "Given a date, the command generates a document (in pdf format) of relevant insights about\n"
            + "the user’s food consumption pattern of the same date.\n"
            + "report dd-mm-yyyy\n\n";
    public static final String HELP_CLEAR_MESSAGE = "clear:\n"
            + "Clears all entries from the Food Record.\n"
            + "Format: clear\n\n";
    public static final String HELP_EXIT_MESSAGE = "exit:\n"
            + "Exits the program.\n"
            + "Format: exit\n\n";


    public static final String USERGUIDE_URL = "https://se-education.org/addressbook-level3/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;
    public static final String COMMAND_MESSAGE = HELP_UPDATE_MESSAGE
            + HELP_DELETE_MESSAGE
            + HELP_LIST_MESSAGE
            + HELP_STOMACH_MESSAGE
            + HELP_NOM_MESSAGE
            + HELP_VOMIT_MESSAGE
            + HELP_EXPORT_MESSAGE
            + HELP_FIND_MESSAGE
            + HELP_GOAL_MESSAGE
            + HELP_REPORT_MESSAGE
            + HELP_CLEAR_MESSAGE
            + HELP_EXIT_MESSAGE;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private TextArea commandMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        commandMessage.setText(COMMAND_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
