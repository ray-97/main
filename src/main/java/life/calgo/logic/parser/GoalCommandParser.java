package life.calgo.logic.parser;

import life.calgo.commons.core.Messages;
import life.calgo.logic.commands.GoalCommand;
import life.calgo.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GoalCommand object
 */
public class GoalCommandParser implements Parser<GoalCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the GoalCommand
     * and returns a GoalCommand object for execution
     * @throws ParseException if the user input does not conform the expected format or is less than or equal to 0
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
        if (!ParserUtil.isInteger(goalArg)) {
            throw new ParseException(String.format(GoalCommand.MESSAGE_FAILURE, GoalCommand.MINIMUM_ACCEPTABLE_CALORIES,
                    GoalCommand.MAXIMUM_ACCEPTABLE_CALORIES));
        }
        // at this breakpoint, we know goalArg is an integer
        int targetCalories = Integer.parseInt(goalArg);

        return new GoalCommand(targetCalories);
    }
}

