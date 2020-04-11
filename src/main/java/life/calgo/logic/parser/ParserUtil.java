package life.calgo.logic.parser;

import static java.util.Objects.requireNonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import life.calgo.commons.core.index.Index;
import life.calgo.commons.util.StringUtil;
import life.calgo.logic.commands.GoalCommand;
import life.calgo.logic.parser.exceptions.ParseException;
import life.calgo.model.day.DailyGoal;
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

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static final String MESSAGE_INVALID_DATE = String.format(
            "Invalid date entered. Give an actual date and follow the format of %s" , DATE_PATTERN);
    public static final String MESSAGE_INVALID_PORTION = "Portion is either a number or left empty.";
    public static final String MESSAGE_NON_POSITIVE_PORTION = "Portion should be a positive number.";
    public static final String MESSAGE_INVALID_INDEX = "Index should be a positive number.";
    public static final String MESSAGE_INVALID_POSITION = "Position should be a positive integer!";
    public static final String MESSAGE_INVALID_RATING = "Rating should a an integer between 0 to 10.";
    public static final String MESSAGE_PORTION_LENGTH = "Length of portion should be at most 5 characters.";

    private static final int VALIDATION_LENGTH = 5;
    private static final int INT_INVALID_RATING = -1;
    private static final int INT_MINIMUM_NATURAL_NUMBER = 0;
    private static final int INT_MAXIMUM_RATING = 10;
    private static final int NUTRITIONAL_VALUE_MAXIMUM_DIGITS = 5; // human diets do not exceed 5 digits in calories
    // Protein, Carbohydrate, Fat will hence also never exceed 5 digits as each gram of these gives >1 calorie

    /**
     * Acts as helper method to check if input length is valid.
     *
     * @param input String representing input.
     * @param message Message to display if check fails.
     * @throws ParseException If input length exceeds validation length.
     */
    public static void inputLengthValidation(String input, String message) throws ParseException {
        if (input.length() > VALIDATION_LENGTH) {
            throw new ParseException(message);
        }
    }

    /**
     * Returns true if given String can be parsed as a number.
     *
     * @param strNum String argument to be parsed as a number.
     * @return True if the input can be parsed as a number.
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
     * Returns true if given String can be parsed as an Integer.
     *
     * @param strNum String argument to be parsed as a Integer.
     * @return True if the input can be parsed as a Integer.
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
     * Parses {@code goal} into an integer and returns it.
     *
     * @throws ParseException If the specified value is invalid (not an integer that is >= MINIMUM_ACCEPTABLE_CALORIES
     * and <= MAXIMUM_ACCEPTABLE CALORIES.
     */
    public static int parseGoal(String goal) throws ParseException {
        if (!(isInteger(goal) && goal.length() <= 5 && Integer.parseInt(goal) >= DailyGoal.MINIMUM_ACCEPTABLE_CALORIES
                && Integer.parseInt(goal) <= DailyGoal.MAXIMUM_ACCEPTABLE_CALORIES)) {
            throw new ParseException(String.format(GoalCommand.MESSAGE_FAILURE, DailyGoal.MINIMUM_ACCEPTABLE_CALORIES,
                    DailyGoal.MAXIMUM_ACCEPTABLE_CALORIES));
        } else {
            return Integer.parseInt(goal);
        }
    }


    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException If the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses given String representation of position into an OptionalInt.
     * Position refers to index the food object has in food record display.
     *
     * @param position String representation of position.
     * @return OptionalInt representation of position.
     */
    public static int parsePosition(String position) throws ParseException {
        requireNonNull(position);
        String trimmedPosition = position.trim();
        if (!isInteger(trimmedPosition)) {
            throw new ParseException(MESSAGE_INVALID_POSITION);
        }
        return Integer.parseInt(trimmedPosition);
    }

    /**
     * Instantiate LocalDate object from date represented in String.
     *
     * @param date Date in String representation.
     * @return LocalDate Object with date equivalent to that expressed in argument.
     * @throws ParseException If given String date is in invalid format.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (trimmedDate.equals("")) {
            return LocalDate.now();
        }
        try {
            // extra check for tricky dates like 2020-02-31
            DateFormat df = new SimpleDateFormat(DATE_PATTERN);
            df.setLenient(false);
            df.parse(date);
            return LocalDate.parse(trimmedDate, FORMATTER);
        } catch (Exception e) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
    }

    /**
     * Parses String portion as a double.
     *
     * @param portion String representation of portion argument.
     * @return Double representation of portion argument.
     * @throws ParseException If given argument cannot be parsed as a number.
     */
    public static double parsePortion(String portion) throws ParseException {
        requireNonNull(portion);
        String trimmedPortion = portion.trim();
        inputLengthValidation(trimmedPortion, MESSAGE_PORTION_LENGTH);
        boolean isInvalidPortion = !isNumeric(trimmedPortion) && trimmedPortion.length() > 0;
        if (isInvalidPortion) {
            throw new ParseException(MESSAGE_INVALID_PORTION);
        }
        double value = isNumeric(trimmedPortion) ? Double.parseDouble(trimmedPortion) : 1;
        if (value <= INT_MINIMUM_NATURAL_NUMBER) {
            throw new ParseException(MESSAGE_NON_POSITIVE_PORTION);
        }
        return value;
    }

    /**
     * Parses a String rating as an int.
     *
     * @param rating String representation of rating argument.
     * @return Double representation of rating argument.
     * @throws ParseException If given argument cannot be parsed as an int.
     */
    public static int parseRating(String rating) throws ParseException {
        requireNonNull(rating);
        String trimmedRating = rating.trim();
        boolean isWithinRange = false;
        int parsedInt = INT_INVALID_RATING;
        if (isInteger(trimmedRating)) {
            parsedInt = Integer.parseInt(trimmedRating);
            isWithinRange = parsedInt >= INT_MINIMUM_NATURAL_NUMBER && parsedInt <= INT_MAXIMUM_RATING;
        }
        if (!isWithinRange) {
            throw new ParseException(MESSAGE_INVALID_RATING);
        }
        return parsedInt;
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException If the given {@code Name} is invalid.
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
     * Parses a {@code String calorie} into a {@code Calorie}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException If the given {@code calorie} is invalid.
     */
    public static Calorie parseCalorie(String calorie) throws ParseException {

        requireNonNull(calorie);
        String trimmedCalorie = calorie.trim();

        boolean isInvalidCalorie = !Calorie.isValidCalorie(trimmedCalorie);
        boolean hasUnacceptableStringLength = !ParserUtil.hasAcceptableLengthNutritionalValue(trimmedCalorie);
        if (isInvalidCalorie || hasUnacceptableStringLength) {
            throw new ParseException(Calorie.MESSAGE_CONSTRAINTS);
        }

        String processedCalorieValueString = ParserUtil.removeLeadingZerosFromIntegerString(trimmedCalorie);
        return new Calorie(processedCalorieValueString);

    }

    /**
     * Parses a {@code String protein} into an {@code Protein}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException If the given {@code protein} is invalid.
     */
    public static Protein parseProtein(String protein) throws ParseException {
        requireNonNull(protein);
        String trimmedProtein = protein.trim();

        boolean isInvalidProtein = !Protein.isValidProtein(trimmedProtein);
        boolean hasUnacceptableStringLength = !ParserUtil.hasAcceptableLengthNutritionalValue(trimmedProtein);
        if (isInvalidProtein || hasUnacceptableStringLength) {
            throw new ParseException(Protein.MESSAGE_CONSTRAINTS);
        }

        String processedProteinValueString = ParserUtil.removeLeadingZerosFromIntegerString(trimmedProtein);
        return new Protein(processedProteinValueString);
    }

    /**
     * Parses a {@code String carbohydrate} into an {@code Carbohydrate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException If the given {@code carbohydrate} is invalid.
     */
    public static Carbohydrate parseCarbohydrate(String carbohydrate) throws ParseException {
        requireNonNull(carbohydrate);
        String trimmedCarbohydrate = carbohydrate.trim();

        boolean isInvalidCarbohydrate = !Carbohydrate.isValidCarbohydrate(trimmedCarbohydrate);
        boolean hasUnacceptableStringLength = !ParserUtil.hasAcceptableLengthNutritionalValue(trimmedCarbohydrate);
        if (isInvalidCarbohydrate || hasUnacceptableStringLength) {
            throw new ParseException(Carbohydrate.MESSAGE_CONSTRAINTS);
        }

        String processedCarbohydrateValueString = ParserUtil.removeLeadingZerosFromIntegerString(trimmedCarbohydrate);
        return new Carbohydrate(processedCarbohydrateValueString);
    }

    /**
     * Parses a {@code String fat} into a {@code Fat}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException If the given {@code fat} is invalid.
     */
    public static Fat parseFat(String fat) throws ParseException {
        requireNonNull(fat);
        String trimmedFat = fat.trim();

        boolean isInvalidFat = !Fat.isValidFat(trimmedFat);
        boolean hasUnacceptableStringLength = !ParserUtil.hasAcceptableLengthNutritionalValue(trimmedFat);
        if (isInvalidFat || hasUnacceptableStringLength) {
            throw new ParseException(Fat.MESSAGE_CONSTRAINTS);
        }

        String processedFatValueString = ParserUtil.removeLeadingZerosFromIntegerString(trimmedFat);
        return new Fat(processedFatValueString);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException If the given {@code tag} is invalid.
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
     *
     * @param tags The Collection of Strings to convert into Tags.
     * @return The Set of Tags created.
     * @throws ParseException Should any issues occur during the conversion.
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
     * @param argumentMultimap The Argument Multimap we search each Prefix through.
     * @param prefixes Each Prefix we need to search for matches.
     * @return Whether every Prefix appears in the Argument Multimap.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Removes leading zeros from a source String which represents an Integer.
     *
     * @param source The source String which represents an Integer and can only contain Integer values.
     * @return The processed String which has leading zeros removed.
     */
    private static String removeLeadingZerosFromIntegerString(String source) {
        int processedValue = Integer.parseInt(source);
        String processedValueString = String.valueOf(processedValue);
        return processedValueString;
    }

    /**
     * Checks whether the given String is within the acceptable length for a nutritional value.
     *
     * @param value the String representing the nutritional value we want to check.
     * @return whether the given String representing a nutritional value can possibly be valid.
     */
    private static boolean hasAcceptableLengthNutritionalValue(String value) {
        return (value.length() <= NUTRITIONAL_VALUE_MAXIMUM_DIGITS);
    }
}
