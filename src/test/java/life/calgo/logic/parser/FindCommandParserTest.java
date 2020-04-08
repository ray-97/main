package life.calgo.logic.parser;

import static life.calgo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import life.calgo.logic.commands.FindCommand;
import life.calgo.model.food.Calorie;
import life.calgo.model.food.Carbohydrate;
import life.calgo.model.food.Fat;
import life.calgo.model.food.Name;
import life.calgo.model.food.Protein;
import life.calgo.model.food.predicates.CalorieContainsKeywordsPredicate;
import life.calgo.model.food.predicates.CarbohydrateContainsKeywordsPredicate;
import life.calgo.model.food.predicates.FatContainsKeywordsPredicate;
import life.calgo.model.food.predicates.NameContainsKeywordsPredicate;
import life.calgo.model.food.predicates.ProteinContainsKeywordsPredicate;
import life.calgo.model.food.predicates.TagContainsKeywordsPredicate;
import life.calgo.model.tag.Tag;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {

        // spaces
        CommandParserTestUtil.assertParseFailure(
                parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // only find keyword
        CommandParserTestUtil.assertParseFailure(parser, "find",
                FindCommandParser.MESSAGE_EXCESS_FIND_FILTERS);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {

        // By Name

        FindCommand expectedFindCommandByName =
                new FindCommand(new NameContainsKeywordsPredicate(new Name("Strawberry Jam")));
        // no leading and trailing whitespaces
        CommandParserTestUtil.assertParseSuccess(parser, "find n/Strawberry Jam", expectedFindCommandByName);
        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, "find n/  Strawberry   Jam  ",
                expectedFindCommandByName);

        // By Calorie

        FindCommand expectedFindCommandByCalorie =
                new FindCommand(new CalorieContainsKeywordsPredicate(new Calorie("111")));
        // no leading and trailing whitespaces
        CommandParserTestUtil.assertParseSuccess(parser, "find cal/111", expectedFindCommandByCalorie);
        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, "find cal/  111  ", expectedFindCommandByCalorie);

        // By Protein

        FindCommand expectedFindCommandByProtein =
                new FindCommand(new ProteinContainsKeywordsPredicate(new Protein("123")));
        // no leading and trailing whitespaces
        CommandParserTestUtil.assertParseSuccess(parser, "find p/123", expectedFindCommandByProtein);
        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, "find p/  123  ", expectedFindCommandByProtein);

        // By Carbohydrate

        FindCommand expectedFindCommandByCarbohydrate =
                new FindCommand(new CarbohydrateContainsKeywordsPredicate(new Carbohydrate("99")));
        // no leading and trailing whitespaces
        CommandParserTestUtil.assertParseSuccess(parser, "find c/99", expectedFindCommandByCarbohydrate);
        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, "find c/  99  ", expectedFindCommandByCarbohydrate);

        // By Fat

        FindCommand expectedFindCommandByFat =
                new FindCommand(new FatContainsKeywordsPredicate(new Fat("69")));
        // no leading and trailing whitespaces
        CommandParserTestUtil.assertParseSuccess(parser, "find f/69", expectedFindCommandByFat);
        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, "find f/  69 ", expectedFindCommandByFat);

        // By Tag
        ArrayList<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("Sweet"));
        FindCommand expectedFindCommandByTag =
                new FindCommand(new TagContainsKeywordsPredicate(tagList));
        // no leading and trailing whitespaces
        CommandParserTestUtil.assertParseSuccess(parser, "find t/Sweet", expectedFindCommandByTag);
        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, "find t/  Sweet  ", expectedFindCommandByTag);

    }

    @Test
    public void parse_invalidArguments_returnsException() {

        // more than 1 Prefix of same type
        CommandParserTestUtil.assertParseFailure(parser, "find n/cake n/1",
                FindCommandParser.MESSAGE_EXCESS_FIND_FILTERS);
        // 2 of different Prefix
        CommandParserTestUtil.assertParseFailure(parser, "find n/cake f/1",
                FindCommandParser.MESSAGE_EXCESS_FIND_FILTERS);
        // 3 of different Prefix in different order
        CommandParserTestUtil.assertParseFailure(parser, "find n/cake c/1 cal/100",
                FindCommandParser.MESSAGE_EXCESS_FIND_FILTERS);
        // Multiple Prefix of multiple types
        CommandParserTestUtil.assertParseFailure(parser, "find n/cake n/chicken c/1 cal/100 cal/50",
                FindCommandParser.MESSAGE_EXCESS_FIND_FILTERS);
    }

}
