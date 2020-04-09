package life.calgo.storage;

import com.fasterxml.jackson.annotation.JsonCreator;

import life.calgo.commons.exceptions.IllegalValueException;
import life.calgo.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedTag {

    private final String tagName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given String {@code tagName}.
     *
     * @param tagName The String that represents a Tag to form the JsonAdaptedTag with.
     */
    @JsonCreator
    public JsonAdaptedTag(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     *
     * @param source The source Tag object we wish to convert from.
     */
    public JsonAdaptedTag(Tag source) {
        tagName = source.tagName;
    }

    /**
     * Dummy constructor for JsonAdaptedTag class.
     */
    public JsonAdaptedTag() {
        tagName = "";
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException If there were any data constraints violated in the adapted tag.
     */
    public Tag toModelType() throws IllegalValueException {
        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(tagName);
    }

}
