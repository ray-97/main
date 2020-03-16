package f11_1.calgo.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import f11_1.calgo.commons.core.index.Index;
import f11_1.calgo.commons.util.StringUtil;
import f11_1.calgo.logic.parser.exceptions.ParseException;
import f11_1.calgo.model.food.Calorie;
import f11_1.calgo.model.food.Carbohydrate;
import f11_1.calgo.model.food.Protein;
import f11_1.calgo.model.food.Fat;
import f11_1.calgo.model.food.Name;
import f11_1.calgo.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

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
}
