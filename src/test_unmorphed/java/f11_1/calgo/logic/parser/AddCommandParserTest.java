package f11_1.calgo.logic.parser;

import static f11_1.calgo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static f11_1.calgo.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static f11_1.calgo.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static f11_1.calgo.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static f11_1.calgo.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static f11_1.calgo.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static f11_1.calgo.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static f11_1.calgo.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static f11_1.calgo.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static f11_1.calgo.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static f11_1.calgo.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static f11_1.calgo.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static f11_1.calgo.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static f11_1.calgo.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static f11_1.calgo.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static f11_1.calgo.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static f11_1.calgo.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static f11_1.calgo.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static f11_1.calgo.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static f11_1.calgo.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static f11_1.calgo.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static f11_1.calgo.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static f11_1.calgo.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static f11_1.calgo.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import f11_1.calgo.testutil.FoodBuilder;
import f11_1.calgo.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;

import f11_1.calgo.model.food.Calorie;
import f11_1.calgo.model.food.Fat;
import f11_1.calgo.model.food.Protein;
import f11_1.calgo.model.food.Food;
import f11_1.calgo.model.food.Name;
import f11_1.calgo.model.tag.Tag;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Food expectedFood = new FoodBuilder(TypicalPersons.BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedFood));

        // multiple names - last name accepted
        CommandParserTestUtil.assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedFood));

        // multiple phones - last phone accepted
        CommandParserTestUtil.assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedFood));

        // multiple emails - last email accepted
        CommandParserTestUtil.assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedFood));

        // multiple addresses - last address accepted
        CommandParserTestUtil.assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedFood));

        // multiple tags - all accepted
        Food expectedFoodMultipleTags = new FoodBuilder(TypicalPersons.BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        CommandParserTestUtil.assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedFoodMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Food expectedFood = new FoodBuilder(TypicalPersons.AMY).withTags().build();
        CommandParserTestUtil.assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedFood));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Calorie.MESSAGE_CONSTRAINTS);

        // invalid email
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Fat.MESSAGE_CONSTRAINTS);

        // invalid address
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Protein.MESSAGE_CONSTRAINTS);

        // invalid tag
        CommandParserTestUtil.assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
