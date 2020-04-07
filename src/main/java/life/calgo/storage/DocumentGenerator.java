package life.calgo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * An abstract class containing functionality for ReportGenerator and ExportGenerator.
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

            // happens when there is an error in opening or creating the file
            logger.warning("Not able to generate document because file was unable to be created.");

        } catch (Exception e) {

            // other issues, usually due to the user's system
            logger.warning("Check your system security settings and enable rights to create a new file.");

        }
    }

    /**
     * Writes a line for neatness in formatting.
     */
    public void printSeparator() {
        printWriter.println("--------------------------------------------------------------------------------"
                + "---------------------------------------");
    }

    /**
     * Wraps a String s into lines of n characters.
     *
     * @param s the String to be wrapped about.
     * @param n the number of characters allowed in a line.
     * @return the processed String after wrapping.
     */
    public String stringWrap(String s, int n) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            if (i != 0 && i % (n - 1) == 0) {
                result += "\n";
            }
            result += s.charAt(i);
        }
        int numExceeded = result.length() % n;
        if (numExceeded != 0) {
            int remainderTrailingWhiteSpace = n - numExceeded;
            for (int i = 0; i < remainderTrailingWhiteSpace; i++) {
                result += " ";
            }
        }
        return result;
    }

    /**
     * Writes an empty line.
     */
    public void printEmptyLine() {
        printWriter.println("");
    }

    /**
     * Centralises the specified String.
     *
     * @param text the String to be centralised.
     * @return the processed String that has been centralised.
     */
    public String centraliseText(String text) {

        int lengthOfText = text.length();
        int numWhitespace = (WIDTH_OF_DOCUMENT - lengthOfText) / 2;

        StringBuilder sb = new StringBuilder();
        while (numWhitespace >= 0) {
            sb.append(" ");
            numWhitespace--;
        }
        sb.append(text);

        return sb.toString();
    }

    /**
     * Writes the context/meta-information of the document
     */
    public abstract void printHeader();

    /**
     * Writes the concluding remarks in the document
     */
    public abstract void printFooter();
}
