package life.calgo.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import life.calgo.commons.core.Messages;
import life.calgo.logic.commands.ClearCommand;
import life.calgo.logic.commands.Command;
import life.calgo.logic.commands.DeleteCommand;
import life.calgo.logic.commands.ExitCommand;
import life.calgo.logic.commands.ExportCommand;
import life.calgo.logic.commands.FindCommand;
import life.calgo.logic.commands.GoalCommand;
import life.calgo.logic.commands.HelpCommand;
import life.calgo.logic.commands.ListCommand;
import life.calgo.logic.commands.NomCommand;
import life.calgo.logic.commands.ReportCommand;
import life.calgo.logic.commands.StomachCommand;
import life.calgo.logic.commands.UpdateCommand;
import life.calgo.logic.commands.VomitCommand;
import life.calgo.logic.parser.exceptions.ParseException;
import life.calgo.model.Model;

/**
 * Parses user input.
 */
public class CalgoParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput Full user input String.
     * @return The command based on the user input.
     * @throws ParseException If the user input does not conform the expected format.
     */
    public Command parseCommand(String userInput) throws ParseException {
        Matcher matcher = matchUserInput(userInput);

        String commandWord = matcher.group("commandWord");
        String arguments = matcher.group("arguments");

        switch (commandWord) {

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case UpdateCommand.COMMAND_WORD:
            return new UpdateCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case StomachCommand.COMMAND_WORD:
            return new StomachCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommand();

        case ReportCommand.COMMAND_WORD:
            return new ReportCommandParser().parse(arguments);

        case GoalCommand.COMMAND_WORD:
            return new GoalCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput Full user input String.
     * @param model Model for parser to retrieve information from.
     * @return The command based on the user input.
     * @throws ParseException If the user input does not conform the expected format.
     */
    public Command parseCommand(String userInput, Model model) throws ParseException {
        Matcher matcher = matchUserInput(userInput);

        String commandWord = matcher.group("commandWord");
        String arguments = matcher.group("arguments");

        switch (commandWord) {

        case NomCommand.COMMAND_WORD:
            return new NomCommandParser(model).parse(arguments);

        case VomitCommand.COMMAND_WORD:
            return new VomitCommandParser(model).parse(arguments);

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Functions as a helper method to reduce code reuse.
     *
     * @param userInput String to match.
     * @return Matcher object that matches user input.
     * @throws ParseException If the user input does not conform the expected format.
     */
    private Matcher matchUserInput(String userInput) throws ParseException {
        Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        return matcher;
    }

    /**
     * A method to check which overloaded parse method to call depending on the type of command.
     *
     * @param userInput String representing command.
     * @return True if command requires use of model, false otherwise.
     * @throws ParseException If the user input does not conform the expected format.
     */
    public boolean doesParserRequireModel(String userInput) throws ParseException {
        Matcher matcher = matchUserInput(userInput);

        String commandWord = matcher.group("commandWord");
        boolean isNomOrVomit = commandWord.equals(NomCommand.COMMAND_WORD)
                || commandWord.equals(VomitCommand.COMMAND_WORD);
        return isNomOrVomit;
    }

}
