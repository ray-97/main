package life.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.LinkedHashMap;
import java.util.Set;

import life.calgo.logic.commands.exceptions.CommandException;
import life.calgo.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    public static final String NO_COMMAND = "Sorry, but no commands contain this keyword. "
            + "Thus, a guide for all commands will be shown.\n\n";

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
            + "Saves an editable text file (FoodRecords.txt) in data/exports folder.\n"
            + "This helps you to take printouts of your records whenever you can't be around your laptop.\n"
            + "The FoodRecords.txt file contains all Food item records including their nutritional content.\n"
            + "Format: export\n\n";
    public static final String HELP_FIND_MESSAGE = "find:\n"
            + "Finds all Food items whose names contain any of the keyword(s)\n"
            + "Alternatively, Food items can also be found by entering a specific value of its attributes,\n"
            + "i.e. Calories, Proteins, Carbohydrates, or Fats.\n"
            + "Format: find [n/NAME] [cal/CALORIES] [p/PROTEINS] [c/CARBS] [f/FATS] (choose only 1 parameter)\n"
            + "Alt format: find KEYWORD [MORE_KEYWORDS]\n\n";
    public static final String HELP_GOAL_MESSAGE = "goal:\n"
            + "Sets a numerical goal for the desired number of calories to be consumed in a day.\n"
            + "Subsequently, Calgo will provide you with insights on how to reach your goal.\n"
            + "Side Note: These helpful insights are given through the report feature.\n"
            + "Format: goal GOAL\n\n";
    public static final String HELP_HELP_MESSAGE = "help:\n"
            + "Shows a list of all commands and their usage and format.\n"
            + "Alternatively, search for a specific group of commands using a keyword.\n"
            + "Format: help [COMMAND_WORD]\n\n";
    public static final String HELP_LIST_MESSAGE = "list:\n"
            + "Shows a list of all Food items in the Food Records,\n"
            + "with their respective nutritional values of calories, proteins, carbohydrates, and fats.\n"
            + "Format: list\n\n";
    public static final String HELP_NOM_MESSAGE = "nom:\n"
            + "Adds a food item into the log which keeps track of what the user has eaten on that day.\n"
            + "Format: nom n/NAME [d/DATE] [portion/PORTION]\n\n";
    public static final String HELP_REPORT_MESSAGE = "report:\n"
            + "Given a date, the command generates a text document that contains the following:\n"
            + "\t - Your goal information"
            + "\t - Your calorie consumption on the given date"
            + "\t - Motivation and tips on how to better reach your goal.\n"
            + "Format: report d/yyyy-MM-dd\n\n";
    public static final String HELP_STOMACH_MESSAGE = "stomach: \n"
            + "Displays a list of food items along side how many portions have been consumed on that day.\n"
            + "For a more detailed statistical report with nutritional values of food consumed, see report command.\n"
            + "Format: stomach [d/DATE]\n\n";
    public static final String HELP_UPDATE_MESSAGE = "update:\n"
            + "Updates a Food Item in the Food Record, or creates a new Food Item if one isn't already present.\n"
            + "Format: update n/NAME cal/CALORIES p/PROTEINS c/CARBS f/FATS\n\n";
    public static final String HELP_VOMIT_MESSAGE = "vomit:\n"
            + "Deletes a food item that a user has previously added to the log tracking consumption on that day.\n"
            + "Format: vomit num/INDEX_OF_FOOD [d/DATE] [portion/PORTION]\n\n";

    public static final String DEFAULT_HELP_MESSAGE = HELP_CLEAR_MESSAGE
            + HELP_DELETE_MESSAGE
            + HELP_EXIT_MESSAGE
            + HELP_EXPORT_MESSAGE
            + HELP_FIND_MESSAGE
            + HELP_GOAL_MESSAGE
            + HELP_HELP_MESSAGE
            + HELP_LIST_MESSAGE
            + HELP_NOM_MESSAGE
            + HELP_REPORT_MESSAGE
            + HELP_UPDATE_MESSAGE
            + HELP_VOMIT_MESSAGE;

    private static final LinkedHashMap<String, String> internalMap = new LinkedHashMap<>();
    private static Set<String> internalSet;

    private static String filteredGuide;

    private String keyword;

    public HelpCommand() {
        //dummy for test
    }

    public HelpCommand(String keyword) {
        this.keyword = keyword.trim();

        addMessagesToMap();
        internalSet = setKeySet();
    }

    public static String getFilteredGuide() {
        return filteredGuide;
    }

    /**
     * Add all help messages to the internal hashmap.
     */
    private void addMessagesToMap() {
        internalMap.put("clear", HELP_CLEAR_MESSAGE);
        internalMap.put("delete", HELP_DELETE_MESSAGE);
        internalMap.put("exit", HELP_EXIT_MESSAGE);
        internalMap.put("export", HELP_EXPORT_MESSAGE);
        internalMap.put("find", HELP_FIND_MESSAGE);
        internalMap.put("goal", HELP_GOAL_MESSAGE);
        internalMap.put("help", HELP_HELP_MESSAGE);
        internalMap.put("list", HELP_LIST_MESSAGE);
        internalMap.put("nom", HELP_NOM_MESSAGE);
        internalMap.put("report", HELP_REPORT_MESSAGE);
        internalMap.put("stomach", HELP_STOMACH_MESSAGE);
        internalMap.put("update", HELP_UPDATE_MESSAGE);
        internalMap.put("vomit", HELP_VOMIT_MESSAGE);
    }

    /**
     * Sets a given set to the keyset of the internalMap.
     * @return Set of the keys.
     */
    private Set<String> setKeySet() {
        return internalMap.keySet();
    }

    /**
     * Obtains a String for the help guide of all commands with the keyword as a substring.
     * @param keyword
     * @return String containing all found commands.
     */
    private String printIfSubstring(String keyword) {
        requireNonNull(internalSet);
        String result = "";

        for (String commandName : internalSet) {
            if (!commandName.contains(keyword)) {
                continue;
            }

            result = result + internalMap.get(commandName);
        }

        return result;
    }

    private void setFilteredGuide() {
        if (keyword == null) {
            filteredGuide = DEFAULT_HELP_MESSAGE;
        } else {
            filteredGuide = printIfSubstring(keyword);
        }

        if (filteredGuide.isEmpty()) {
            filteredGuide = NO_COMMAND + DEFAULT_HELP_MESSAGE;
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        setFilteredGuide();

        return new CommandResult(SHOWING_HELP_MESSAGE,
                true, false);
    }
}
