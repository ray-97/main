package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROTEIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Calorie;
import seedu.address.model.person.Carbohydrate;
import seedu.address.model.person.Fat;
import seedu.address.model.person.Protein;
import seedu.address.model.person.Food;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_CALORIES + "PHONE] "
            + "[" + PREFIX_FAT + "EMAIL] "
            + "[" + PREFIX_PROTEIN + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CALORIES + "91234567 "
            + PREFIX_FAT + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Food> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Food foodToEdit = lastShownList.get(index.getZeroBased());
        Food editedFood = createEditedPerson(foodToEdit, editPersonDescriptor);

        if (!foodToEdit.isSameFood(editedFood) && model.hasPerson(editedFood)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(foodToEdit, editedFood);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedFood));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Food createEditedPerson(Food foodToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert foodToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(foodToEdit.getName());
        Calorie updatedCalorie = editPersonDescriptor.getCalorie().orElse(foodToEdit.getCalorie());
        Fat updatedFat = editPersonDescriptor.getFat().orElse(foodToEdit.getFat());
        Protein updatedProtein = editPersonDescriptor.getProtein().orElse(foodToEdit.getProtein());
        Carbohydrate updatedCarbohydrate = editPersonDescriptor.getCarbohydrate().orElse(foodToEdit.getCarbohydrate());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(foodToEdit.getTags());

        return new Food(updatedName, updatedCalorie, updatedProtein, updatedCarbohydrate, updatedFat, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Calorie calorie;
        private Protein protein;
        private Carbohydrate carbohydrate;
        private Fat fat;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setCalorie(toCopy.calorie);
            setProtein(toCopy.protein);
            setCarbohydrate(toCopy.carbohydrate);
            setFat(toCopy.fat);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, calorie, fat, protein, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setCalorie(Calorie calorie) {
            this.calorie = calorie;
        }

        public Optional<Calorie> getCalorie() {
            return Optional.ofNullable(calorie);
        }

        public void setFat(Fat fat) {
            this.fat = fat;
        }

        public Optional<Fat> getFat() {
            return Optional.ofNullable(fat);
        }

        public void setProtein(Protein protein) {
            this.protein = protein;
        }

        public Optional<Protein> getProtein() {
            return Optional.ofNullable(protein);
        }

        public void setCarbohydrate(Carbohydrate carbohydrate) {
            this.carbohydrate = carbohydrate;
        }

        public Optional<Carbohydrate> getCarbohydrate() {
            return Optional.ofNullable(carbohydrate);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getCalorie().equals(e.getCalorie())
                    && getProtein().equals(e.getProtein())
                    && getCarbohydrate().equals(e.getCarbohydrate())
                    && getFat().equals(e.getFat())
                    && getTags().equals(e.getTags());
        }
    }
}
