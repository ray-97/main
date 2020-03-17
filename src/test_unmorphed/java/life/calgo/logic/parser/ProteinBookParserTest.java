//package f11_1.calgo.logic.parser;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static f11_1.calgo.testutil.Assert.assertThrows;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import f11_1.calgo.commons.core.Messages;
//import f11_1.calgo.logic.commands.ClearCommand;
//import f11_1.calgo.logic.commands.DeleteCommand;
//import f11_1.calgo.logic.commands.ExitCommand;
//import f11_1.calgo.logic.commands.FindCommand;
//import f11_1.calgo.logic.commands.HelpCommand;
//import f11_1.calgo.logic.commands.ListCommand;
//import f11_1.calgo.logic.parser.exceptions.ParseException;
//import f11_1.calgo.model.food.Food;
//import f11_1.calgo.model.food.NameContainsKeywordsPredicate;
//import f11_1.calgo.testutil.Assert;
//import f11_1.calgo.testutil.EditPersonDescriptorBuilder;
//import f11_1.calgo.testutil.FoodBuilder;
//import f11_1.calgo.testutil.PersonUtil;
//import f11_1.calgo.testutil.TypicalIndexes;
//import org.junit.jupiter.api.Test;
//
//public class ProteinBookParserTest {
//
//    private final FoodRecordParser parser = new FoodRecordParser();
//
//    @Test
//    public void parseCommand_add() throws Exception {
//        Food food = new FoodBuilder().build();
//        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(food));
//        assertEquals(new AddCommand(food), command);
//    }
//
//    @Test
//    public void parseCommand_clear() throws Exception {
//        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
//        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
//    }
//
//    @Test
//    public void parseCommand_delete() throws Exception {
//        DeleteCommand command = (DeleteCommand) parser.parseCommand(
//                DeleteCommand.COMMAND_WORD + " " + TypicalIndexes.INDEX_FIRST_PERSON.getOneBased());
//        assertEquals(new DeleteCommand(TypicalIndexes.INDEX_FIRST_PERSON), command);
//    }
//
//    @Test
//    public void parseCommand_edit() throws Exception {
//        Food food = new FoodBuilder().build();
//        EditCommand.EditFoodDescriptor descriptor = new EditPersonDescriptorBuilder(food).build();
//        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
//                + TypicalIndexes.INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
//        assertEquals(new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON, descriptor), command);
//    }
//
//    @Test
//    public void parseCommand_exit() throws Exception {
//        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
//        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
//    }
//
//    @Test
//    public void parseCommand_find() throws Exception {
//        List<String> keywords = Arrays.asList("foo", "bar", "baz");
//        FindCommand command = (FindCommand) parser.parseCommand(
//                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
//        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
//    }
//
//    @Test
//    public void parseCommand_help() throws Exception {
//        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
//        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
//    }
//
//    @Test
//    public void parseCommand_list() throws Exception {
//        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
//        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
//    }
//
//    @Test
//    public void parseCommand_unrecognisedInput_throwsParseException() {
//        Assert.assertThrows(ParseException.class, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
//            -> parser.parseCommand(""));
//    }
//
//    @Test
//    public void parseCommand_unknownCommand_throwsParseException() {
//        Assert.assertThrows(ParseException.class, Messages.MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
//    }
//}
