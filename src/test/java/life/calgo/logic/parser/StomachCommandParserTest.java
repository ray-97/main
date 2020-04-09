package life.calgo.logic.parser;

import static life.calgo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static life.calgo.logic.parser.ParserUtil.MESSAGE_INVALID_DATE;
import static life.calgo.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import life.calgo.logic.commands.StomachCommand;
import life.calgo.logic.parser.exceptions.ParseException;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.testutil.TypicalDailyFoodLog;

public class StomachCommandParserTest {

    private LocalDate date = LocalDate.now();
    private StomachCommandParser parser = new StomachCommandParser();
    private DailyFoodLog dailyLog = TypicalDailyFoodLog.getAppleOnlyLog();

    @Test
    public void parse_validArgs_returnsStomachCommand() {
        String userInput = "stomach";
        CommandParserTestUtil.assertParseSuccess(parser, userInput, new StomachCommand(date));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedMessage = MESSAGE_INVALID_COMMAND_FORMAT;
        String invalidUserInput = "stomach 1";
        CommandParserTestUtil.assertParseFailure(
                parser, invalidUserInput, String.format(expectedMessage, StomachCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDateFormat_parseExceptionThrown() {
        String expectedMessage = MESSAGE_INVALID_DATE;
        String invalidUserInput = "stomach d/2020/09/08";

        assertThrows(ParseException.class,
                expectedMessage, () -> parser.parse(invalidUserInput));
    }

    @Test
    public void parse_nonExistentDate_parseExceptionThrown() {
        String expectedMessage = MESSAGE_INVALID_DATE;
        String invalidUserInput = "stomach d/2020-02-31";
        assertThrows(ParseException.class,
                expectedMessage, () -> parser.parse(invalidUserInput));
    }

}
