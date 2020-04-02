package life.calgo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * An abstract class containing functionality that ReportGenerator and ExportGenerator share
 */
public abstract class DocumentGenerator {
    protected PrintWriter printWriter;
    protected File file;
    protected final Logger logger;
    public static final int WIDTH_OF_DOCUMENT = 120;


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
     * Writes an empty line
     */
    public void printEmptyLine() {
        printWriter.println("");
    }

    /**
     * Centralises text
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
     * Writes the meta-information of the document
     */
    public abstract void printHeader();

    /**
     * Writes the concluding remarks in the document
     */
    public abstract void printFooter();
}
