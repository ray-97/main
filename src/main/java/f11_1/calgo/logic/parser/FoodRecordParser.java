package f11_1.calgo.logic.parser;

import static f11_1.calgo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static f11_1.calgo.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import f11_1.calgo.logic.commands.NomCommand;
import f11_1.calgo.logic.commands.ReportCommand;
import f11_1.calgo.logic.commands.StomachCommand;
import f11_1.calgo.logic.commands.VomitCommand;

import f11_1.calgo.logic.commands.AddCommand;
import f11_1.calgo.logic.commands.ClearCommand;
import f11_1.calgo.logic.commands.Command;
import f11_1.calgo.logic.commands.DeleteCommand;
import f11_1.calgo.logic.commands.EditCommand;
import f11_1.calgo.logic.commands.ExitCommand;
import f11_1.calgo.logic.commands.FindCommand;
import f11_1.calgo.logic.commands.HelpCommand;
import f11_1.calgo.logic.commands.ListCommand;
import f11_1.calgo.logic.parser.exceptions.ParseException;
import f11_1.calgo.model.Model;

/**
 * Parses user input.
 */
public class FoodRecordParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, Model model) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case StomachCommand.COMMAND_WORD:
            return new StomachCommand();

        case NomCommand.COMMAND_WORD:
            return new NomCommandParser(model).parse(arguments);

        case VomitCommand.COMMAND_WORD:
            return new VomitCommandParser(model).parse(arguments);

        case ReportCommand.COMMAND_WORD:
            return new ReportCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
