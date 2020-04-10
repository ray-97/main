package life.calgo.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, and a non-full word match also returns true.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == true //not a full word match
     *       </pre>
     * @param sentence Cannot be null.
     * @param word Cannot be null, cannot be empty, must be a single word.
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        AppUtil.checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        AppUtil.checkArgument(preppedWord.split("\\s+").length == 1,
                "Word parameter should be a single word");

        // we want to compare the keyword with the words in this sentence
        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        // only return true if either they are the exact equivalent ignoring casing,
        // or the word is contained as a substring in any of the sentence's words
        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase)
                || containsSubstringWord(wordsInPreppedSentence, preppedWord);
    }

    /**
     * Returns true if the String array contains at least one String containing the substring, regardless of casing.
     *
     * @param words String array to search in.
     * @param substringWord Substring keyword to find if the Strings from the String array contain it.
     * @return Whether the String array contains at least one String containing the substring.
     */
    private static boolean containsSubstringWord(String[] words, String substringWord) {
        for (String word: words) {
            if (word.toLowerCase().contains(substringWord.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns if the Food has the stated nutritional value.
     * Nutritional value can be either Calorie, Protein, Carbohydrate, or Fat.
     *
     * @param origin The Food's nutritional value.
     * @param query The stated nutritional value of the same type.
     * @return Whether the Food has the stated nutritional value.
     */
    public static boolean containsNutritionalValueEqualTo(String origin, String query) {
        requireNonNull(origin);
        requireNonNull(query);
        String preppedQueryString = query.trim();
        AppUtil.checkArgument(!preppedQueryString.isEmpty(), "Query parameter cannot be empty");
        AppUtil.checkArgument(preppedQueryString.split("\\s+").length == 1,
                "Your nutritional value query should be a single value");

        int preppedQueryValue = Integer.parseInt(preppedQueryString);
        int preppedOriginValue = Integer.parseInt(origin);

        return (preppedOriginValue == preppedQueryValue);
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer.
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input.
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException If {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
