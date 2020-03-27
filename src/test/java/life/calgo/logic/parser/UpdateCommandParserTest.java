package life.calgo.logic.parser;

import static life.calgo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static life.calgo.logic.commands.CommandTestUtil.VALID_NAME_APPLE;
import static life.calgo.logic.commands.CommandTestUtil.VALID_NAME_BANANA;
import static life.calgo.logic.commands.CommandTestUtil.VALID_CALORIE_APPLE;
import static life.calgo.logic.commands.CommandTestUtil.VALID_CALORIE_BANANA;
import static life.calgo.logic.commands.CommandTestUtil.VALID_PROTEIN_APPLE;
import static life.calgo.logic.commands.CommandTestUtil.VALID_PROTEIN_BANANA;
import static life.calgo.logic.commands.CommandTestUtil.VALID_CARBOHYDRATE_APPLE;
import static life.calgo.logic.commands.CommandTestUtil.VALID_CARBOHYDRATE_BANANA;
import static life.calgo.logic.commands.CommandTestUtil.VALID_FAT_APPLE;
import static life.calgo.logic.commands.CommandTestUtil.VALID_FAT_BANANA;
import static life.calgo.logic.commands.CommandTestUtil.VALID_TAG_HARD;
import static life.calgo.logic.commands.CommandTestUtil.VALID_TAG_SOFT;

import static life.calgo.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static life.calgo.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static life.calgo.logic.commands.CommandTestUtil.NAME_DESC_APPLE;
import static life.calgo.logic.commands.CommandTestUtil.NAME_DESC_BANANA;
import static life.calgo.logic.commands.CommandTestUtil.CALORIE_DESC_APPLE;
import static life.calgo.logic.commands.CommandTestUtil.CALORIE_DESC_BANANA;
import static life.calgo.logic.commands.CommandTestUtil.CARBOHYDRATE_DESC_APPLE;
import static life.calgo.logic.commands.CommandTestUtil.CARBOHYDRATE_DESC_BANANA;
import static life.calgo.logic.commands.CommandTestUtil.PROTEIN_DESC_APPLE;
import static life.calgo.logic.commands.CommandTestUtil.PROTEIN_DESC_BANANA;
import static life.calgo.logic.commands.CommandTestUtil.TAG_DESC_HARD;
import static life.calgo.logic.commands.CommandTestUtil.TAG_DESC_SOFT;

import static life.calgo.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static life.calgo.logic.commands.CommandTestUtil.INVALID_CALORIE_DESC;
import static life.calgo.logic.commands.CommandTestUtil.INVALID_PROTEIN_DESC;
import static life.calgo.logic.commands.CommandTestUtil.INVALID_CARBOHYDRATE_DESC;
import static life.calgo.logic.commands.CommandTestUtil.INVALID_FAT_DESC;
import static life.calgo.logic.commands.CommandTestUtil.INVALID_TAG_DESC;


import life.calgo.logic.commands.UpdateCommand;
import life.calgo.testutil.FoodBuilder;
import life.calgo.testutil.TypicalFoodItems;
import life.calgo.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;

import life.calgo.model.food.Calorie;
import life.calgo.model.food.Fat;
import life.calgo.model.food.Protein;
import life.calgo.model.food.Food;
import life.calgo.model.food.Name;
import life.calgo.model.tag.Tag;

public class UpdateCommandParserTest {
    private UpdateCommandParser parser = new UpdateCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Food expectedFood = new FoodBuilder(TypicalFoodItems.BANANA_MILKSHAKE).withTags(VALID_TAG_SOFT).build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BANANA + CALORIE_DESC_BANANA + PROTEIN_DESC_BANANA
                + CARBOHYDRATE_DESC_BANANA + TAG_DESC_SOFT, new UpdateCommand(expectedFood));

        // multiple names - last name accepted
        CommandParserTestUtil.assertParseSuccess(parser, NAME_DESC_APPLE + NAME_DESC_BANANA + CALORIE_DESC_BANANA + PROTEIN_DESC_BANANA
                + CARBOHYDRATE_DESC_BANANA + TAG_DESC_SOFT, new UpdateCommand(expectedFood));

        // multiple phones - last phone accepted
        CommandParserTestUtil.assertParseSuccess(parser, NAME_DESC_BANANA + CALORIE_DESC_APPLE + CALORIE_DESC_BANANA + PROTEIN_DESC_BANANA
                + CARBOHYDRATE_DESC_BANANA + TAG_DESC_SOFT, new UpdateCommand(expectedFood));

        // multiple emails - last email accepted
        CommandParserTestUtil.assertParseSuccess(parser, NAME_DESC_BANANA + CALORIE_DESC_BANANA + PROTEIN_DESC_APPLE + PROTEIN_DESC_BANANA
                + CARBOHYDRATE_DESC_BANANA + TAG_DESC_SOFT, new UpdateCommand(expectedFood));

        // multiple addresses - last address accepted
        CommandParserTestUtil.assertParseSuccess(parser, NAME_DESC_BANANA + CALORIE_DESC_BANANA + PROTEIN_DESC_BANANA + CARBOHYDRATE_DESC_APPLE
                + CARBOHYDRATE_DESC_BANANA + TAG_DESC_SOFT, new UpdateCommand(expectedFood));

        // multiple tags - all accepted
        Food expectedFoodMultipleTags = new FoodBuilder(TypicalFoodItems.BANANA_MILKSHAKE).withTags(VALID_TAG_SOFT, VALID_TAG_HARD)
                .build();
        CommandParserTestUtil.assertParseSuccess(parser, NAME_DESC_BANANA + CALORIE_DESC_BANANA + PROTEIN_DESC_BANANA + CARBOHYDRATE_DESC_BANANA
                + TAG_DESC_HARD + TAG_DESC_SOFT, new UpdateCommand(expectedFoodMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Food expectedFood = new FoodBuilder(TypicalFoodItems.APPLE).withTags().build();
        CommandParserTestUtil.assertParseSuccess(parser, NAME_DESC_APPLE + CALORIE_DESC_APPLE + PROTEIN_DESC_APPLE + CARBOHYDRATE_DESC_APPLE,
                new UpdateCommand(expectedFood));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_BANANA + CALORIE_DESC_BANANA + PROTEIN_DESC_BANANA + CARBOHYDRATE_DESC_BANANA,
                expectedMessage);

        // missing phone prefix
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_BANANA + VALID_CALORIE_BANANA + PROTEIN_DESC_BANANA + CARBOHYDRATE_DESC_BANANA,
                expectedMessage);

        // missing email prefix
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_BANANA + CALORIE_DESC_BANANA + VALID_PROTEIN_BANANA + CARBOHYDRATE_DESC_BANANA,
                expectedMessage);

        // missing address prefix
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_BANANA + CALORIE_DESC_BANANA + PROTEIN_DESC_BANANA + VALID_CARBOHYDRATE_BANANA,
                expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_BANANA + VALID_CALORIE_BANANA + VALID_PROTEIN_BANANA + VALID_CARBOHYDRATE_BANANA,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, INVALID_NAME_DESC + CALORIE_DESC_BANANA + PROTEIN_DESC_BANANA + CARBOHYDRATE_DESC_BANANA
                + TAG_DESC_HARD + TAG_DESC_SOFT, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_BANANA + INVALID_CALORIE_DESC + PROTEIN_DESC_BANANA + CARBOHYDRATE_DESC_BANANA
                + TAG_DESC_HARD + TAG_DESC_SOFT, Calorie.MESSAGE_CONSTRAINTS);

        // invalid email
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_BANANA + CALORIE_DESC_BANANA + INVALID_PROTEIN_DESC + CARBOHYDRATE_DESC_BANANA
                + TAG_DESC_HARD + TAG_DESC_SOFT, Fat.MESSAGE_CONSTRAINTS);

        // invalid address
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_BANANA + CALORIE_DESC_BANANA + PROTEIN_DESC_BANANA + INVALID_CARBOHYDRATE_DESC
                + TAG_DESC_HARD + TAG_DESC_SOFT, Protein.MESSAGE_CONSTRAINTS);

        // invalid tag
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_BANANA + CALORIE_DESC_BANANA + PROTEIN_DESC_BANANA + CARBOHYDRATE_DESC_BANANA
                + INVALID_TAG_DESC + VALID_TAG_SOFT, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, INVALID_NAME_DESC + CALORIE_DESC_BANANA + PROTEIN_DESC_BANANA + INVALID_CARBOHYDRATE_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BANANA + CALORIE_DESC_BANANA + PROTEIN_DESC_BANANA
                + CARBOHYDRATE_DESC_BANANA + TAG_DESC_HARD + TAG_DESC_SOFT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
    }
}
