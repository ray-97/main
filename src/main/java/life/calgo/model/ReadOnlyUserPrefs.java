package life.calgo.model;

import java.nio.file.Path;

import life.calgo.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getFoodRecordFilePath();

}
