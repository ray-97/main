package life.calgo.logic.parser;

import static life.calgo.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static life.calgo.logic.parser.NomCommandParser.MESSAGE_NONEXISTENT_FOOD;
import static life.calgo.logic.parser.ParserUtil.MESSAGE_INVALID_DATE;
import static life.calgo.logic.parser.ParserUtil.MESSAGE_INVALID_RATING;
import static life.calgo.logic.parser.ParserUtil.MESSAGE_NON_POSITIVE_PORTION;
import static life.calgo.logic.parser.ParserUtil.MESSAGE_PORTION_LENGTH;
import static life.calgo.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import life.calgo.commons.core.Messages;
import life.calgo.logic.commands.NomCommand;
import life.calgo.logic.parser.exceptions.ParseException;
import life.calgo.model.ModelManager;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.food.Name;
import life.calgo.testutil.TypicalDailyFoodLog;
import life.calgo.testutil.TypicalFoodItems;

public class NomCommandParserTest {

    private ModelManager modelManager;
    private NomCommandParser nomCommandParser = new NomCommandParser(new ModelManager());

    @BeforeEach
    public void setUp() {
        modelManager = new ModelManager();
        nomCommandParser = new NomCommandParser(modelManager);
        modelManager.addFood(TypicalFoodItems.APPLE);
    }

    @Test
    public void parse_missingNamePrefix_parseExceptionThrown() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, NomCommand.MESSAGE_USAGE);
        assertThrows(ParseException.class, expectedMessage, () -> nomCommandParser.parse("nom"));
    }

    @Test
    public void parse_missingNameValue_parseExceptionThrown() {
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(ParseException.class, expectedMessage, () -> nomCommandParser.parse("nom n/"));
    }

    @Test
    public void parse_foodNotInFoodRecord_parseExceptionThrown() {
        String expectedMessage = MESSAGE_NONEXISTENT_FOOD;
        assertThrows(ParseException.class, expectedMessage, () -> nomCommandParser.parse("nom n/SHaRk fIn SoUp"));
    }

    @Test
    public void parse_invalidDateFormat_parseExceptionThrown() {
        String expectedMessage = MESSAGE_INVALID_DATE;
        assertThrows(
                ParseException.class, expectedMessage, () -> nomCommandParser.parse("nom n/Apple d/2020/09/08"));
    }

    @Test
    public void parse_nonExistentDate_parseExceptionThrown() {
        String expectedMessage = MESSAGE_INVALID_DATE;
        assertThrows(
                ParseException.class, expectedMessage, () -> nomCommandParser.parse("nom n/Apple d/2020-02-31"));
    }

    @Test
    public void parse_negativePortion_parseExceptionThrown() {
        String expectedMessage = MESSAGE_NON_POSITIVE_PORTION;
        assertThrows(
                ParseException.class, expectedMessage, () -> nomCommandParser.parse("nom n/Apple portion/-1"));
    }

    @Test
    public void parse_overSizedPortion_parseExceptionThrown() {
        String expectedMessage = MESSAGE_PORTION_LENGTH;
        assertThrows(ParseException.class,
                expectedMessage, () -> nomCommandParser.parse("nom n/Apple portion/99999999999"));
    }

    @Test
    public void parse_negativeRating_parseExceptionThrown() {
        String expectedMessage = MESSAGE_INVALID_RATING;
        assertThrows(ParseException.class, expectedMessage, () -> nomCommandParser.parse("nom n/Apple r/-1"));
    }

    @Test
    public void parse_doubleForRating_parseExceptionThrown() {
        String expectedMessage = MESSAGE_INVALID_RATING;
        assertThrows(ParseException.class, expectedMessage, () -> nomCommandParser.parse("nom n/Apple r/1.5"));
    }

    @Test
    public void parse_ratingOutOfRange_parseExceptionThrown() {
        String expectedMessage = MESSAGE_INVALID_RATING;
        assertThrows(ParseException.class, expectedMessage, () -> nomCommandParser.parse("nom n/Apple r/11"));
    }

    @Test
    public void parse_validFoodSentenceCase_success() {
        String userInput = "nom n/Apple";
        DailyFoodLog appleOnlyLog = TypicalDailyFoodLog.getAppleOnlyLog();
        assertParseSuccess(nomCommandParser, userInput, new NomCommand(appleOnlyLog, TypicalFoodItems.APPLE));
    }

    @Test
    public void parse_validFoodLowerCase_success() {
        String userInput = "nom n/apple";
        DailyFoodLog appleOnlyLog = TypicalDailyFoodLog.getAppleOnlyLog();
        assertParseSuccess(nomCommandParser, userInput, new NomCommand(appleOnlyLog, TypicalFoodItems.APPLE));
    }

    @Test
    public void parse_validFoodUpperCase_success() {
        String userInput = "nom n/APPLE";
        DailyFoodLog appleOnlyLog = TypicalDailyFoodLog.getAppleOnlyLog();
        assertParseSuccess(nomCommandParser, userInput, new NomCommand(appleOnlyLog, TypicalFoodItems.APPLE));
    }

    @Test
    public void parse_validDate_success() {
        String userInput = "nom n/Apple d/";
        DailyFoodLog appleOnlyLog = TypicalDailyFoodLog.getAppleOnlyLog();
        assertParseSuccess(nomCommandParser, userInput, new NomCommand(appleOnlyLog, TypicalFoodItems.APPLE));
    }

    @Test
    public void parse_validPortion_success() {
        String userInput = "nom n/Apple portion/1";
        DailyFoodLog appleOnlyLog = TypicalDailyFoodLog.getAppleOnlyLog();
        assertParseSuccess(nomCommandParser, userInput, new NomCommand(appleOnlyLog, TypicalFoodItems.APPLE));
    }
}
