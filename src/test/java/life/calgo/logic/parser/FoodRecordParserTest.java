package life.calgo.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import life.calgo.commons.core.Messages;
import life.calgo.logic.commands.ClearCommand;
import life.calgo.logic.commands.DeleteCommand;
import life.calgo.logic.commands.ExitCommand;
import life.calgo.logic.commands.FindCommand;
import life.calgo.logic.commands.HelpCommand;
import life.calgo.logic.commands.ListCommand;
import life.calgo.logic.commands.UpdateCommand;
import life.calgo.logic.parser.exceptions.ParseException;
import life.calgo.model.Model;
import life.calgo.model.ModelManager;
import life.calgo.model.food.Food;
import life.calgo.model.food.NameContainsKeywordsPredicate;
import life.calgo.testutil.Assert;
import life.calgo.testutil.FoodBuilder;
import life.calgo.testutil.FoodUtil;

public class FoodRecordParserTest {

    private final FoodRecordParser parser = new FoodRecordParser();
    private final Model model = new ModelManager();

    @Test
    public void parseCommand_update() throws Exception {
        Food food = new FoodBuilder().build();
        UpdateCommand command = (UpdateCommand) parser.parseCommand(FoodUtil.getUpdateCommand(food), model);
        assertEquals(new UpdateCommand(food), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, model) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3", model) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        String foodName = "Apple";
        Food foodToDelete = new FoodBuilder().withName(foodName)
                .withCalorie("0")
                .withCarbohydrate("0")
                .withProtein("0")
                .withCarbohydrate("0")
                .withFat("0")
                .build();
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " n/" + foodName, model);
        assertEquals(new DeleteCommand(foodToDelete), command);
    }


    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, model) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", model) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")), model);
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, model) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3", model) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, model) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3", model) instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        Assert.assertThrows(ParseException.class, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand("", model));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        Assert.assertThrows(
                ParseException.class, Messages.MESSAGE_UNKNOWN_COMMAND, () ->
                        parser.parseCommand("unknownCommand", model));
    }
}
