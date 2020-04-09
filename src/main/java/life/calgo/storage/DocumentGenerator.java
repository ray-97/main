package life.calgo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import life.calgo.model.food.Name;

/**
 * An abstract class representing functionality for ReportGenerator and ExportGenerator.
 */
public abstract class DocumentGenerator {
    public static final int WIDTH_OF_DOCUMENT = 120;

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
    public abstract void printHeader();

    /**
     * Writes the body of the document.
     */
    public abstract void printBody();

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

    /**
     * Writes the concluding remarks in the document.
     */
    public abstract void printFooter();

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









    // Remove if Unused thanks!

    /**
     * @param sb A client StringBuilder object.
     * @param columns An ArrayList of String Arrays (columns) that contain respective lines of data.
     * @param currLine The current line number that is being iterated through.
     * @param smallColumnWidth An Integer for the number of characters a small column should be.(For Numerical columns).
     * @param largeColumnWidth An Integer for the number of characters a large column should be. (For Name columns).
     */
    private void iterateColumn(StringBuilder sb, ArrayList<String[]> columns, int currLine, int smallColumnWidth,
                               int largeColumnWidth) {
        int numArrays = columns.size();
        for (int column = 0; column < numArrays; column++) {
            String[] currArray = columns.get(column);
            int columnWidth = smallColumnWidth;
            // first column is Food Name column
            if (column == 0) {
                columnWidth = largeColumnWidth;
            }

            // only first line of that column is centralised.
            // if column contains data that is spread across multiple lines, the other lines are not centralised.
            if (currLine == 0) {
                sb.append(centraliseText(currArray[currLine], columnWidth + 1));
            } else if (currLine < currArray.length) {
                String currString = currArray[currLine];
                sb.append(addNTrailingWhitespace(currString,
                        columnWidth - currString.length() + 1));
            } else {
                sb.append(addNTrailingWhitespace(" ", columnWidth + 1));
            }

            if (column == numArrays - 1) {
                sb.append("\n");
            }
        }
    }
    /**
     * Combines columns to form a table. Goes line by line.
     *
     * @param strings An ArrayList of Strings, where each element is a column.
     * @param smallColumnWidth An Integer for the number of characters a small column should be.(For Numerical columns).
     * @param largeColumnWidth An Integer for the number of characters a large column should be. (For Name columns).
     * @return A stitched String with all columns combined together.
     */
    public String combineColumns(ArrayList<String> strings, int smallColumnWidth, int largeColumnWidth) {
        StringBuilder result = new StringBuilder();
        ArrayList<String[]> splitArrays = splitNewLines(strings);
        int maxNumLines = getMaxLines(splitArrays);

        for (int currLine = 0; currLine < maxNumLines; currLine++) {
            iterateColumn(result, splitArrays, currLine, smallColumnWidth, largeColumnWidth);
        }

        return result.toString();
    }

    /**
     * Wraps a String s into lines of n characters.
     *
     * @param s the String to be wrapped about.
     * @param n the number of characters allowed in a line.
     * @return the processed String after wrapping.
     */
    public String stringWrap(String s, int n) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i != 0 && i % (n - 1) == 0) {
                result.append("\n");
            }
            result.append(s.charAt(i));
        }

        return result.toString();
    }

    /**
     * Centralises the specified String.
     *
     * @param text The String to be centralised.
     * @param width The width of the line whereby String should be centralised.
     * @return The processed String that has been centralised.
     */
    public String centraliseText(String text, int width) {

        int lengthOfText = text.length();
        int numWhitespace = (width - lengthOfText) / 2;
        String prefixedText = addNLeadingWhitespace(text, numWhitespace);
        return addNTrailingWhitespace(prefixedText, numWhitespace);
    }

    /**
     * A method that converts each String into a String array containing substrings that are split by "\n".
     * @param strings An ArrayList of strings.
     * @return An ArrayList of String arrays, which contain resulting substrings after split by "\n".
     */
    private ArrayList<String[]> splitNewLines(ArrayList<String> strings) {
        ArrayList<String[]> result = new ArrayList<>();
        for (String string : strings) {
            String[] curr = string.split("\n");
            result.add(curr);
        }
        return result;
    }

    /**
     * Takes in a list of String arrays {@code splitStrings} and returns the maximum length of the String
     * array elements.
     */
    private int getMaxLines(ArrayList<String[]> splitStrings) {
        int maxLines = 0;
        for (String[] stringArray : splitStrings) {
            maxLines = Math.max(maxLines, stringArray.length);
        }
        return maxLines;
    }

    public String addNLeadingWhitespace(String text, int n) {
        return " ".repeat(n) + text;
    }

    public String addNTrailingWhitespace(String text, int n) {
        return text + " ".repeat(n);
    }











}
