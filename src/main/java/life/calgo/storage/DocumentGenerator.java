package life.calgo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import life.calgo.model.food.Name;

/**
 * An abstract class representing functionality for ReportGenerator and ExportGenerator.
 */
public abstract class DocumentGenerator {
    public static final int DOCUMENT_WIDTH = 120;

    protected PrintWriter printWriter;
    protected File file;
    protected final Logger logger;

    public DocumentGenerator(String pathName, Logger logger) {

        this.file = new File(pathName);
        this.logger = logger;

        try {

            file.getParentFile().mkdirs();
            file.createNewFile();
            this.printWriter = new PrintWriter(file);

        } catch (FileNotFoundException e) {

            // happens when there is an error in opening or creating the file.
            logger.warning("Not able to generate document because file was unable to be created.");

        } catch (Exception e) {

            // other issues, usually due to the user's system.
            logger.warning("Check your system security settings and enable rights to create a new file.");

        }
    }

    // Printing Methods

    /**
     * Writes the context/meta-information of the document.
     */
    protected abstract void printHeader();

    /**
     * Writes the body of the document.
     */
    protected abstract void printBody();

    /**
     * Writes the concluding remarks in the document.
     */
    protected abstract void printFooter();

    /**
     * Writes a line for neatness in formatting.
     */
    protected void printSeparator() {
        printWriter.println("--------------------------------------------------------------------------------"
                + "---------------------------------------");
    }

    /**
     * Writes an empty line.
     */
    protected void printEmptyLine() {
        printWriter.println("");
    }

    // String Manipulation Methods

    /**
     * Obtains the wrapped String representation of the given Name suited for the specified width.
     *
     * @param name the Name of the Food which we want to wrap.
     * @param width the maximum allowed width of the Name segment.
     * @return the wrapped String representation of the Name for the given width.
     */
    protected String generateWrappedNameString(Name name, int width) {

        String result = "";

        String workablePart = getNameString(name);
        while (!hasAcceptableLength(workablePart, width)) {
            result += getNextSegment(workablePart, width); // in a new line each time to follow visual format
            workablePart = getNextWorkablePart(workablePart, width);
        }
        assert (hasAcceptableLength(workablePart, width)) : "The supposedly truncated String is still too long.";
        result += workablePart; // definitely within acceptable length at this point

        return result;

    }

    /**
     * Centralises the specified String.
     *
     * @param text The String to be centralised.
     * @param width The width of the line whereby String should be centralised.
     * @return The processed String that has been centralised.
     */
    protected String centraliseText(String text, int width) {

        int lengthOfText = text.length();
        int numWhitespace = (width - lengthOfText) / 2;
        String prefixedText = addNLeadingWhitespace(text, numWhitespace);
        return addNTrailingWhitespace(prefixedText, numWhitespace);
    }

    /**
     * Adds a prefix of a given number of whitespaces to a given string.
     *
     * @param text The given string.
     * @param n The number of whitespaces to add before the given string.
     * @return The string with leading whitespaces.
     */
    protected String addNLeadingWhitespace(String text, int n) {
        return " ".repeat(n) + text;
    }

    /**
     * Adds a suffix of a given number of whitespaces to a given string.
     *
     * @param text The given string.
     * @param n The number of whitespaces to add after the given string.
     * @return The string with trailing whitespaces.
     */
    protected String addNTrailingWhitespace(String text, int n) {
        return text + " ".repeat(n);
    }

    // Utility Methods

    /**
     * Removes the first (width) number of characters and returns the remaining String to continue working with.
     *
     * @param workablePart the original String to work with.
     * @param width the number of characters to remove.
     * @return the remaining String after characters are removed.
     */
    protected String getNextWorkablePart(String workablePart, int width) {
        return workablePart.substring(width);
    }

    /**
     * Obtains the truncated part (in a new line) from the middle of a String considered too long for the formatting.
     *
     * @param workablePart the String we extract the part from.
     * @param width the length of the extracted part.
     * @return the truncated part we wish to extract.
     */
    protected String getNextSegment(String workablePart, int width) {
        return workablePart.substring(0, width) + "\n";
    }

    protected String getNameString(Name name) {
        return name.toString();
    }

    /**
     * Checks whether the current Name contains a String within the acceptable length for the visual format.
     *
     * @param part the current String to check, which can represent a substring of another String.
     * @param length the acceptable length.
     * @return whether the given String is within acceptable limits.
     */
    protected boolean hasAcceptableLength(String part, int length) {
        return (part.length() <= length);
    }

}
