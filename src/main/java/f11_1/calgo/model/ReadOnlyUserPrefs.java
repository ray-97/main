package f11_1.calgo.model;

import java.nio.file.Path;

import f11_1.calgo.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getFoodRecordFilePath();

}
