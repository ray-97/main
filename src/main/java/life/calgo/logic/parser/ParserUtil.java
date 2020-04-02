package life.calgo.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import life.calgo.commons.core.index.Index;
import life.calgo.commons.util.StringUtil;
import life.calgo.logic.parser.exceptions.ParseException;
import life.calgo.model.food.Calorie;
import life.calgo.model.food.Carbohydrate;
import life.calgo.model.food.Fat;
import life.calgo.model.food.Name;
import life.calgo.model.food.Protein;
import life.calgo.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Position should be a positive number.";

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final String MESSAGE_INVALID_DATE = String.format(
            "Invalid date entered. Give an actual date and follow the format of %s" , DATE_PATTERN);
    private static final String MESSAGE_INVALID_PORTION = "Portion is either a number or left empty.";
    private static final String MESSAGE_INVALID_POSITION = "Position should be an integer!";
    private static final String MESSAGE_INVALID_RATING = "Rating should a an integer between 0 to 10.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Instantiate LocalDate object from date represented in String
     * @param date date in String representation
     * @return LocalDate object with date equivalent to that expressed in argument
     * @throws ParseException if given String date is in invalid format
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (trimmedDate.equals("")) {
            return LocalDate.now();
        }
        try {
            return LocalDate.parse(trimmedDate, formatter);
        } catch (Exception e) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
    }

    /**
     * Returns true if given String can be parsed as a number
     * @param strNum a String argument to be parsed as a number
     * @return true if the input can be parsed as a number
     */
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if given String can be parsed as an Integer
     * @param strNum a String argument to be parsed as a Integer
     * @return true if the input can be parsed as a Integer
     */
    public static boolean isInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Parses String portion as a double
     * @param portion a String representation of portion argument
     * @return double representation of portion argument
     * @throws ParseException if given argument cannot be parsed as a number
     */
    public static double parsePortion(String portion) throws ParseException {
        requireNonNull(portion);
        String trimmedPortion = portion.trim();
        boolean isInvalidPortion = !isNumeric(trimmedPortion) && trimmedPortion.length() > 0;
        if (isInvalidPortion) {
            throw new ParseException(MESSAGE_INVALID_PORTION);
        }
        return isNumeric(trimmedPortion) ? Double.parseDouble(trimmedPortion) : 1;
    }

    /**
     * Parses a String rating as an int.
     * @param rating a String representation of rating argument
     * @return double representation of rating argument
     * @throws ParseException if given argument cannot be parsed as an int
     */
    public static int parseRating(String rating) throws ParseException {
        requireNonNull(rating);
        String trimmedRating = rating.trim();
        boolean withinRange = true;
        int parsedInt = -1;
        if (isInteger(trimmedRating)) {
            parsedInt = Integer.parseInt(trimmedRating);
            withinRange = parsedInt >= 0 && parsedInt <= 10;
        }
        if (!withinRange) {
            throw new ParseException(MESSAGE_INVALID_RATING);
        }
        return parsedInt;
    }

    /**
     * Parses given String representation of position into an OptionalInt
     * Position refers to index the food object has in food record display
     * @param position String representation of position
     * @return OptionalInt representation of position
     */
    public static int parsePosition(String position) throws ParseException {
        requireNonNull(position);
        String trimmedPosition = position.trim();
        // We do not check range of position here because only foods in DailyFoodLog knows.
        if (!isInteger(trimmedPosition)) {
            throw new ParseException(MESSAGE_INVALID_POSITION);
        }
        return Integer.parseInt(trimmedPosition);
    }

    /**
     * Parses a {@code String calorie} into a {@code Calorie}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code calorie} is invalid.
     */
    public static Calorie parseCalorie(String calorie) throws ParseException {
        requireNonNull(calorie);
        String trimmedCalorie = calorie.trim();
        if (!Calorie.isValidCalorie(trimmedCalorie)) {
            throw new ParseException(Calorie.MESSAGE_CONSTRAINTS);
        }
        return new Calorie(trimmedCalorie);
    }

    /**
     * Parses a {@code String protein} into an {@code Protein}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code protein} is invalid.
     */
    public static Protein parseProtein(String protein) throws ParseException {
        requireNonNull(protein);
        String trimmedProtein = protein.trim();
        if (!Protein.isValidProtein(trimmedProtein)) {
            throw new ParseException(Protein.MESSAGE_CONSTRAINTS);
        }
        return new Protein(trimmedProtein);
    }

    /**
     * Parses a {@code String carbohydrate} into an {@code Carbohydrate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code carbohydrate} is invalid.
     */
    public static Carbohydrate parseCarbohydrate(String carbohydrate) throws ParseException {
        requireNonNull(carbohydrate);
        String trimmedCarbohydrate = carbohydrate.trim();
        if (!Fat.isValidFat(trimmedCarbohydrate)) {
            throw new ParseException(Carbohydrate.MESSAGE_CONSTRAINTS);
        }
        return new Carbohydrate(trimmedCarbohydrate);
    }

    /**
     * Parses a {@code String fat} into a {@code Fat}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code fat} is invalid.
     */
    public static Fat parseFat(String fat) throws ParseException {
        requireNonNull(fat);
        String trimmedFat = fat.trim();
        if (!Fat.isValidFat(trimmedFat)) {
            throw new ParseException(Fat.MESSAGE_CONSTRAINTS);
        }
        return new Fat(trimmedFat);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Returns whether all Prefixes appear in the Argument Multimap.
     *
     * @param argumentMultimap the Argument Multimap we search each Prefix through.
     * @param prefixes Each Prefix we need to search for matches.
     * @return whether every Prefix appears in the Argument Multimap.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
