package life.calgo.logic.parser;

import static life.calgo.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import life.calgo.logic.parser.exceptions.ParseException;
import life.calgo.model.food.Calorie;
import life.calgo.model.food.Carbohydrate;
import life.calgo.model.food.Fat;
import life.calgo.model.food.Name;
import life.calgo.model.food.Protein;
import life.calgo.model.tag.Tag;
import life.calgo.testutil.Assert;
import life.calgo.testutil.TypicalIndexes;

public class ParserUtilTest {
    private static final String INVALID_NAME = "@APPLE!";
    private static final String INVALID_CALORIE = "+651234";
    private static final String INVALID_CARBOHYDRATE = " ";
    private static final String INVALID_PROTEIN = "ABC!";
    private static final String INVALID_FAT = "IAMNOTFAT?!";
    private static final String INVALID_TAG = "# u a dum";

    private static final String VALID_NAME = "Apple";
    private static final String VALID_CALORIE = "100";
    private static final String VALID_PROTEIN = "10";
    private static final String VALID_CARBOHYDRATE = "5";
    private static final String VALID_FAT = "2";
    private static final String VALID_TAG_1 = "hard";
    private static final String VALID_TAG_2 = "healthy";



    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(TypicalIndexes.INDEX_FIRST_FOOD, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(TypicalIndexes.INDEX_FIRST_FOOD, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseCalorie_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseCalorie((String) null));
    }

    @Test
    public void parseCalorie_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseCalorie(INVALID_CALORIE));
    }

    @Test
    public void parseCalorie_validValueWithoutWhitespace_returnsCalorie() throws Exception {
        Calorie expectedCalorie = new Calorie(VALID_CALORIE);
        assertEquals(expectedCalorie, ParserUtil.parseCalorie(VALID_CALORIE));
    }

    @Test
    public void parseCalorie_validValueWithWhitespace_returnsTrimmedCalorie() throws Exception {
        String calorieWithWhitespace = WHITESPACE + VALID_CALORIE + WHITESPACE;
        Calorie expectedCalorie = new Calorie(VALID_CALORIE);
        assertEquals(expectedCalorie, ParserUtil.parseCalorie(calorieWithWhitespace));
    }

    @Test
    public void parseProtein_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseProtein((String) null));
    }

    @Test
    public void parseProtein_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseProtein(INVALID_PROTEIN));
    }

    @Test
    public void parseProtein_validValueWithoutWhitespace_returnsProtein() throws Exception {
        Protein expectedProtein = new Protein(VALID_PROTEIN);
        assertEquals(expectedProtein, ParserUtil.parseProtein(VALID_PROTEIN));
    }


    @Test
    public void parseProtein_validValueWithWhitespace_returnsTrimmedProtein() throws Exception {
        String proteinWithWhitespace = WHITESPACE + VALID_PROTEIN + WHITESPACE;
        Protein expectedProtein = new Protein(VALID_PROTEIN);
        assertEquals(expectedProtein, ParserUtil.parseProtein(proteinWithWhitespace));
    }

    @Test
    public void parseCarbohydrate_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseCarbohydrate((String) null));
    }

    @Test
    public void parseCarbohydrate_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseCarbohydrate(INVALID_CARBOHYDRATE));
    }

    @Test
    public void parseCarbohydrate_validValueWithoutWhitespace_returnsCarbohydrate() throws Exception {
        Carbohydrate expectedCarbohydrate = new Carbohydrate(VALID_CARBOHYDRATE);
        assertEquals(expectedCarbohydrate, ParserUtil.parseCarbohydrate(VALID_CARBOHYDRATE));
    }


    @Test
    public void parseCarbohydrate_validValueWithWhitespace_returnsTrimmedCarbohydrate() throws Exception {
        String carbohydrateWithWhitespace = WHITESPACE + VALID_CARBOHYDRATE + WHITESPACE;
        Carbohydrate expectedCarbohydrate = new Carbohydrate(VALID_CARBOHYDRATE);
        assertEquals(expectedCarbohydrate, ParserUtil.parseCarbohydrate(carbohydrateWithWhitespace));
    }


    @Test
    public void parseFat_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseFat((String) null));
    }

    @Test
    public void parseFat_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseFat(INVALID_FAT));
    }

    @Test
    public void parseFat_validValueWithoutWhitespace_returnsFat() throws Exception {
        Fat expectedFat = new Fat(VALID_FAT);
        assertEquals(expectedFat, ParserUtil.parseFat(VALID_FAT));
    }

    @Test
    public void parseFat_validValueWithWhitespace_returnsTrimmedFat() throws Exception {
        String fatWithWhitespace = WHITESPACE + VALID_FAT + WHITESPACE;
        Fat expectedFat = new Fat(VALID_FAT);
        assertEquals(expectedFat, ParserUtil.parseFat(fatWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
