package life.calgo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * An abstract class containing functionality that ReportGenerator and ExportGenerate share
 */
public abstract class DocumentGenerator {
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
            logger.warning("Check your system security settings and enable rights to create a new file.");
        }
    }

    /**
     * Writes a line for neatness in formatting.
     */
    public void printSeparator() {
        printWriter.println("--------------------------------------------------------------------------------"
                + "----------------------------------------");
    }

    /**
     * Writes an empty line
     */
    public void printEmptyLine() {
        printWriter.println("");
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
