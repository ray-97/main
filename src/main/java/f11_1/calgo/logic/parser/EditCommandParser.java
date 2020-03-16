package f11_1.calgo.logic.parser;

import static java.util.Objects.requireNonNull;
import static f11_1.calgo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_CARBOHYDRATE;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_FAT;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_PROTEIN;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_NAME;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_CALORIES;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import f11_1.calgo.commons.core.index.Index;
import f11_1.calgo.logic.commands.EditCommand;
import f11_1.calgo.logic.parser.exceptions.ParseException;
import f11_1.calgo.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CALORIES,
                        PREFIX_PROTEIN, PREFIX_CARBOHYDRATE, PREFIX_FAT, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditCommand.EditFoodDescriptor editFoodDescriptor = new EditCommand.EditFoodDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editFoodDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_CALORIES).isPresent()) {
            editFoodDescriptor.setCalorie(ParserUtil.parseCalorie(argMultimap.getValue(PREFIX_CALORIES).get()));
        }
        if (argMultimap.getValue(PREFIX_PROTEIN).isPresent()) {
            editFoodDescriptor.setProtein(ParserUtil.parseProtein(argMultimap.getValue(PREFIX_PROTEIN).get()));
        }
        if (argMultimap.getValue(PREFIX_CARBOHYDRATE).isPresent()) {
            editFoodDescriptor.setCarbohydrate(
                    ParserUtil.parseCarbohydrate(argMultimap.getValue(PREFIX_CARBOHYDRATE).get())
            );
        }
        if (argMultimap.getValue(PREFIX_FAT).isPresent()) {
            editFoodDescriptor.setFat(ParserUtil.parseFat(argMultimap.getValue(PREFIX_FAT).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editFoodDescriptor::setTags);

        if (!editFoodDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editFoodDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
