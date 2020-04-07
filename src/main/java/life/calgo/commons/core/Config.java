package life.calgo.commons.core;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app.
 * Config values customizable through config file.
 */
public class Config {

    /**
     * Path for the configuration json file.
     */
    public static final Path DEFAULT_CONFIG_FILE = Paths.get("config.json");

    /**
     * Path for the preferences json file.
     */
    private Path userPrefsFilePath = Paths.get("preferences.json");
    private Level logLevel = Level.INFO;

    /**
     * Obtains the logging level for console messages.
     *
     * @return the logging level for console messages.
     */
    public Level getLogLevel() {
        return logLevel;
    }

    /**
     * Sets the specified logging level for console messages.
     *
     * @param logLevel the specified logging level for console messages.
     */
    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * Obtains the user preference file path previously set.
     *
     * @return the user preference file path previously set.
     */
    public Path getUserPrefsFilePath() {
        return userPrefsFilePath;
    }

    /**
     * Sets the user preference file path to the current specified.
     *
     * @param userPrefsFilePath user preference file path to the current specified.
     */
    public void setUserPrefsFilePath(Path userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    /**
     * Checks if the Config is the same as the other.
     *
     * @param other the other Config to compare the current one with.
     * @return if the Config is the same as the other.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Config)) { //this handles null as well.
            return false;
        }

        Config o = (Config) other;

        return Objects.equals(logLevel, o.logLevel)
                && Objects.equals(userPrefsFilePath, o.userPrefsFilePath);
    }

    /**
     * Returns the hashcode of the user preference file.
     *
     * @return the hashcode of the user preference file.
     */
    @Override
    public int hashCode() {
        return Objects.hash(logLevel, userPrefsFilePath);
    }

    /**
     * Gives a String representation of the Config, showing current logging level and preference file location.
     *
     * @return a String representation of the Config, showing current logging level and preference file location.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Current log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        return sb.toString();
    }

}
