package life.calgo.logic.commands;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import life.calgo.model.Model;

import java.util.Set;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    private String keyword;

    private final ObservableMap<String, String> internalMap = FXCollections.observableHashMap();
    private final ObservableMap<String, String> internalUnmodifiableMap =
            FXCollections.unmodifiableObservableMap(internalMap);

    private Set<String> internalSet;
    //private final ObservableSet<String> internalUnmodifiableSet =
            //FXCollections.unmodifiableObservableSet(internalSet);

    public static final String HELP_CLEAR_MESSAGE = "clear:\n"
            + "Clears all entries from the Food Record.\n"
            + "Format: clear\n\n";
    public static final String HELP_DELETE_MESSAGE = "delete:\n"
            + "Deletes the specified Food Item from the Food Record.\n"
            + "Format: delete n/NAME\n\n";
    public static final String HELP_EXIT_MESSAGE = "exit:\n"
            + "Exits the program.\n"
            + "Format: exit\n\n";
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
            + "Format: goal GOAL\n\n";
    public static final String HELP_LIST_MESSAGE = "list:\n"
            + "Shows a list of all Food items in the Food Records,\n"
            + "with their respective nutritional values of calories, proteins, carbohydrates, and fats.\n"
            + "Format: list\n\n";
    public static final String HELP_NOM_MESSAGE = "nom:\n"
            + "Adds a food item into the log which keeps track of what the user has eaten on that day.\n"
            + "Format: nom [n/NAME] [d/DATE] [portion/PORTION]\n\n";
    public static final String HELP_REPORT_MESSAGE = "report:\n"
            + "Given a date, the command generates a document (in pdf format) of relevant insights about\n"
            + "the user’s food consumption pattern of the same date.\n"
            + "report dd-mm-yyyy\n\n";
    public static final String HELP_STOMACH_MESSAGE = "stomach: \n"
            + "Displays a list of food items along side how many portions have been consumed on that day.\n"
            + "For a more detailed statistical report with nutritional values of food consumed, see report command.\n"
            + "Format: stomach [d/DATE]\n\n";
    public static final String HELP_UPDATE_MESSAGE = "update:\n"
            + "Updates a Food Item in the Food Record, or creates a new Food Item if one isn't already present.\n"
            + "Format: update n/NAME cal/CALORIES p/PROTEINS c/CARBS f/FATS\n\n";
    public static final String HELP_VOMIT_MESSAGE = "vomit:\n"
            + "Deletes a food item that a user has previously added to the log tracking consumption on that day.\n"
            + "Format: vomit [num/INDEX_OF_FOOD] [d/DATE] [portion/PORTION]\n\n";

    public HelpCommand(){
        // dummy
    }

    public HelpCommand(String keyword) {
        this.keyword = keyword.trim();

        addMessagesToMap();
        internalSet = setKeySet();
    }

    private void addMessagesToMap() {
        internalMap.put("clear", HELP_CLEAR_MESSAGE);
        internalMap.put("delete", HELP_DELETE_MESSAGE);
        internalMap.put("exit", HELP_EXIT_MESSAGE);
        internalMap.put("export", HELP_EXPORT_MESSAGE);
        internalMap.put("find", HELP_FIND_MESSAGE);
        internalMap.put("goal", HELP_GOAL_MESSAGE);
        internalMap.put("list", HELP_LIST_MESSAGE);
        internalMap.put("nom", HELP_NOM_MESSAGE);
        internalMap.put("report", HELP_REPORT_MESSAGE);
        internalMap.put("stomach", HELP_STOMACH_MESSAGE);
        internalMap.put("update", HELP_UPDATE_MESSAGE);
        internalMap.put("vomit", HELP_VOMIT_MESSAGE);
    }

    private Set<String> setKeySet() {
        return internalMap.keySet();
    }

    private String printIfSubstring(String keyword) {
        String result = "";

        for (String commandName : internalSet) {
            if (!commandName.contains(keyword)) {
                continue;
            }

            result = result + internalMap.get(commandName);
        }

        return result;
    }

    @Override
    public CommandResult execute(Model model) {
        String commandGuide = printIfSubstring(keyword);
        return new CommandResult(commandGuide,
                true, false);
    }
}
