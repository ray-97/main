package life.calgo.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import life.calgo.logic.Logic;
import life.calgo.logic.commands.CommandResult;
import life.calgo.logic.commands.exceptions.CommandException;
import life.calgo.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";


    private final CommandExecutor commandExecutor;
    private final CommandListener commandListener;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor, CommandListener commandListener) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.commandListener = commandListener;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains("n/")
                    && (newValue.startsWith("update") || newValue.startsWith("delete")
                        || newValue.startsWith("nom"))) {
                String foodName = newValue.split(" ", 2)[1];
                commandListener.getSuggestions(foodName);
            }
            setStyleToDefault();
        });
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }


    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandListener {
        /**
         * Listens into the command and filters the FoodListPanel accordingly.
         *
         * @see Logic#getSimilarFood(String)
         */
        void getSuggestions(String foodName);
    }

}
