package life.calgo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

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

}
