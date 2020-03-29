package life.calgo.logic.parser;

import static life.calgo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import life.calgo.logic.commands.FindCommand;
import life.calgo.model.food.Name;
import life.calgo.model.food.predicates.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(
                parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(new Name("Strawberry Jam")));
        CommandParserTestUtil.assertParseSuccess(parser, "find n/Strawberry Jam", expectedFindCommand);

        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, "find n/  Strawberry   Jam  ", expectedFindCommand);
    }

}
