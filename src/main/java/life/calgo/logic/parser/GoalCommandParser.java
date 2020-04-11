package life.calgo.logic.parser;

import life.calgo.commons.core.Messages;
import life.calgo.logic.commands.GoalCommand;
import life.calgo.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GoalCommand object.
 */
public class GoalCommandParser implements Parser<GoalCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the GoalCommand
     * and returns a GoalCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format or acceptable values.
     */
    public GoalCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] splitArgs = args.split("\\s+");
        if (trimmedArgs.isEmpty() || splitArgs.length != 2) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, GoalCommand.MESSAGE_USAGE)
            );
        }

        String goalArg = splitArgs[1];
        int targetCalories = ParserUtil.parseGoal(goalArg);
        return new GoalCommand(targetCalories);
    }
}

